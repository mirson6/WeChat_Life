/*    */ package cn.mopon.scenic.gateway;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.channel.socket.SocketChannel;
/*    */ import io.netty.handler.logging.LoggingHandler;
/*    */ 
/*    */ public class GatewayServerInitializer extends ChannelInitializer<SocketChannel>
/*    */ {
/*    */   protected void initChannel(SocketChannel ch)
/*    */     throws Exception
/*    */   {
/* 12 */     ChannelPipeline pipeline = ch.pipeline();
/*    */ 
/* 15 */     boolean debug = Configuration.getInstance().isDebug();
/* 16 */     if (debug) {
/* 17 */       pipeline.addLast(new ChannelHandler[] { new LoggingHandler() });
/*    */     }
/*    */ 
/* 21 */     pipeline.addLast(new ChannelHandler[] { new GatewayServerHandler() });
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.GatewayServerInitializer
 * JD-Core Version:    0.6.2
 */