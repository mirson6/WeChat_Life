/*     */ package cn.mopon.scenic.gateway;
/*     */ 
/*     */ import cn.mopon.scenic.gateway.util.Cache;
/*     */ import cn.mopon.scenic.gateway.util.SysProperties;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import java.io.File;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class Application
/*     */ {
/*  30 */   private static final Logger logger = LoggerFactory.getLogger(Application.class);
/*     */ 
/*  33 */   private static int port = 11111;
/*     */ 
/*  36 */   private static String url = "http://192.168.26.1:8080/site/common/toPay/api/transaction";
/*     */ 
/*  39 */   private static boolean debug = false;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  45 */     if (!parseConfiguration(args)) {
/*  46 */       return;
/*     */     }
/*     */ 
/*  50 */     EventLoopGroup boss = new NioEventLoopGroup();
/*  51 */     EventLoopGroup worker = new NioEventLoopGroup();
/*     */     try
/*     */     {
/*  55 */       ServerBootstrap boot = new ServerBootstrap();
/*  56 */       ((ServerBootstrap)boot.group(boss, worker).channel(NioServerSocketChannel.class)).childHandler(new GatewayServerInitializer());
/*  57 */       ChannelFuture f = boot.bind(port).sync();
/*  58 */       logger.info(" SERVER START ...");
/*  59 */       f.channel().closeFuture().sync();
/*     */     }
/*     */     catch (InterruptedException e) {
/*  62 */       logger.error("Can't start gateway process", e);
/*  63 */       return;
/*     */     } finally {
/*  65 */       boss.shutdownGracefully();
/*  66 */       worker.shutdownGracefully();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean parseConfiguration(String[] args)
/*     */   {
/*  72 */     if (args.length < 1) {
/*  73 */       logger.error("Usage: java cn.mopon.scenic.gateway.Application [configuration]");
/*  74 */       return false;
/*     */     }
/*     */ 
/*  77 */     String propFileName = args[0];
/*  78 */     File propFile = new File(propFileName);
/*  79 */     if ((!propFile.exists()) || (!propFile.isFile()) || (!propFile.canRead())) {
/*  80 */       logger.error("Usage: java cn.mopon.scenic.gateway.Application [configuration]");
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  87 */       SysProperties.initProperties(propFile);
/*     */ 
/*  90 */       Cache.initIrecCodes();
/*     */     }
/*     */     catch (Exception e) {
/*  93 */       logger.error(e.getMessage(), e);
/*  94 */       return false;
/*     */     }
/*     */ 
/*  98 */     String ports = SysProperties.getProperty("port");
/*  99 */     if (ports != null) {
/* 100 */       port = Integer.valueOf(ports).intValue();
/*     */     }
/*     */ 
/* 103 */     url = SysProperties.getProperty("url");
/* 104 */     if (url == null) {
/* 105 */       logger.error("Missing URL configuration item");
/* 106 */       return false;
/*     */     }
/*     */ 
/* 109 */     String debugs = SysProperties.getProperty("debug");
/* 110 */     if (debugs != null) {
/* 111 */       if (debugs.equalsIgnoreCase("TRUE"))
/* 112 */         debug = true;
/* 113 */       else if (debugs.equalsIgnoreCase("FALSE")) {
/* 114 */         debug = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     Configuration conf = Configuration.getInstance();
/* 120 */     conf.setPort(port);
/* 121 */     conf.setUrl(url);
/* 122 */     conf.setDebug(debug);
/*     */ 
/* 124 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.Application
 * JD-Core Version:    0.6.2
 */