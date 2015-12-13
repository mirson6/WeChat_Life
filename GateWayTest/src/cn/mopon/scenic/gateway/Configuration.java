/*    */ package cn.mopon.scenic.gateway;
/*    */ 
/*    */ public class Configuration
/*    */ {
/*  5 */   private static Configuration instance = new Configuration();
/*    */   private String url;
/* 11 */   private int port = 11111;
/*    */ 
/* 14 */   private boolean debug = false;
/*    */ 
/*    */   public static Configuration getInstance()
/*    */   {
/* 21 */     return instance;
/*    */   }
/*    */ 
/*    */   public String getUrl() {
/* 25 */     return this.url;
/*    */   }
/*    */ 
/*    */   public void setUrl(String url) {
/* 29 */     this.url = url;
/*    */   }
/*    */ 
/*    */   public int getPort() {
/* 33 */     return this.port;
/*    */   }
/*    */ 
/*    */   public void setPort(int port) {
/* 37 */     this.port = port;
/*    */   }
/*    */ 
/*    */   public boolean isDebug() {
/* 41 */     return this.debug;
/*    */   }
/*    */ 
/*    */   public void setDebug(boolean debug) {
/* 45 */     this.debug = debug;
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.Configuration
 * JD-Core Version:    0.6.2
 */