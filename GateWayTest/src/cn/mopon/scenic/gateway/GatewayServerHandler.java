/*     */ package cn.mopon.scenic.gateway;
/*     */ 
/*     */ import cn.mopon.scenic.gateway.remote.RemoteServer;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListeningExecutorService;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class GatewayServerHandler extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private static final int MIN_PAYLOAD_SIZE = 32;
/*     */   private static final int MAX_PAYLOAD_SIZE = 131072;
/*     */   private static final int DEFAULT_PAYLOAD_SIZE = 512;
/*     */   private static final int PREFIX_LENGTH = 4;
/*  36 */   private static final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);
/*     */   private ByteBuf buf;
/*     */ 
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg)
/*     */     throws Exception
/*     */   {
/*  46 */     if (this.buf == null) {
/*  47 */       this.buf = ctx.alloc().buffer(512);
/*  48 */       this.buf.order(ByteOrder.LITTLE_ENDIAN);
/*     */     }
/*     */ 
/*  51 */     ByteBuf m = (ByteBuf)msg;
/*     */ 
/*  53 */     this.buf.writeBytes(m);
/*  54 */     m.release();
/*     */ 
/*  57 */     if (this.buf.readableBytes() > 4)
/*     */     {
/*  60 */       this.buf.markReaderIndex();
/*     */ 
/*  63 */       int payloadSize = this.buf.readInt();
/*     */ 
/*  66 */       if ((payloadSize > 131072) || (payloadSize < 32))
/*     */       {
/*  68 */         logger.error("Invalid payload size: ", Integer.valueOf(payloadSize));
/*  69 */         ctx.close();
/*  70 */         return;
/*     */       }
/*     */ 
/*  74 */       if (this.buf.readableBytes() < payloadSize) {
/*  75 */         this.buf.resetReaderIndex();
/*  76 */         return;
/*     */       }
/*     */ 
/*  80 */       ByteBuf req = this.buf.readBytes(this.buf.readableBytes());
/*     */ 
/*  83 */       processRequest(ctx, req);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processRequest(ChannelHandlerContext ctx, ByteBuf req)
/*     */   {
/*  90 */     ListeningExecutorService service = TaskExecutors.getInstance().getService();
/*     */ 
/*  92 */     ListenableFuture future = service.submit(new GatewayTask(req));
/*  93 */     Futures.addCallback(future, new GatewayResponseFuture(ctx));
/*     */   }
/*     */ 
/*     */   public void handlerAdded(ChannelHandlerContext ctx)
/*     */     throws Exception
/*     */   {
/*  99 */     ctx.alloc().buffer(512);
/*     */   }
/*     */ 
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
/*     */   {
/* 104 */     this.buf.release();
/* 105 */     this.buf = null;
/*     */   }
/*     */ 
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
/*     */   {
/* 110 */     logger.error("exceptionCaught on socket", cause);
/* 111 */     ctx.close();
/*     */   }
/*     */ 
/*     */   public class GatewayResponseFuture implements FutureCallback<byte[]>
/*     */   {
/*     */     private ChannelHandlerContext ctx;
/*     */ 
/*     */     public GatewayResponseFuture(ChannelHandlerContext ctx) {
/* 119 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     public void onFailure(Throwable t) {
/* 123 */       GatewayServerHandler.logger.error("GatewayResponseFuture call Online Ticket Server fail", t);
/*     */     }
/*     */ 
/*     */     public void onSuccess(byte[] result) {
/* 127 */       if (result != null)
/*     */       {
/* 129 */         int payloadSize = result.length;
/*     */ 
/* 131 */         ByteBuf respBuf = this.ctx.alloc().buffer(4 + payloadSize);
/*     */ 
/* 134 */         respBuf.writeInt(payloadSize);
/*     */ 
/* 137 */         respBuf.writeBytes(result);
/*     */ 
/* 140 */         this.ctx.writeAndFlush(respBuf);
/*     */       } else {
/* 142 */         GatewayServerHandler.logger.error("GatewayResponseFuture onSuccess the result is null..");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public class GatewayTask implements Callable<byte[]>
/*     */   {
/*     */     private ByteBuf req;
/*     */ 
/*     */     public GatewayTask(ByteBuf req) {
/* 152 */       this.req = req;
/*     */     }
/*     */ 
/*     */     public byte[] call()
/*     */       throws Exception
/*     */     {
/* 158 */       String packet = new String(this.req.array());
/*     */ 
/* 160 */       return RemoteServer.invokeRemoteServer(packet);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.GatewayServerHandler
 * JD-Core Version:    0.6.2
 */