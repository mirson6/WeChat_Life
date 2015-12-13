/*     */ package cn.mopon.scenic.gateway.remote;
/*     */ // this is the on line HttpCAll
/*     */ import cn.mopon.scenic.gateway.Configuration;
/*     */ import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
/*     */ import org.apache.commons.httpclient.HostConfiguration;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.params.HostParams;
/*     */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class OnLineHttpCall extends RemoteServer
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(OnLineHttpCall.class);
/*     */   private MultiThreadedHttpConnectionManager connectionManager;
/*     */   private HttpClient client;
/*  40 */   private static final Integer maxTotalConn = Integer.valueOf(20);
/*     */ 
/*  43 */   private static final Integer maxConnPerHost = Integer.valueOf(3);
/*     */ 
/*  45 */   private static final Integer connectionTimeout = Integer.valueOf(30000);
/*     */ 
/*  47 */   private static volatile OnLineHttpCall onLineHttpCall = null;
/*     */ 
/*     */   private OnLineHttpCall()
/*     */   {
/*  51 */     this.connectionManager = new MultiThreadedHttpConnectionManager();
/*     */ 
/*  54 */     HttpConnectionManagerParams params = this.connectionManager.getParams();
/*     */ 
/*  56 */     if (maxConnPerHost.intValue() > maxTotalConn.intValue())
/*     */     {
/*  58 */       params.setMaxTotalConnections(maxConnPerHost.intValue());
/*     */     }
/*     */     else
/*     */     {
/*  62 */       params.setMaxTotalConnections(maxTotalConn.intValue());
/*     */     }
/*     */ 
/*  65 */     params.setDefaultMaxConnectionsPerHost(maxConnPerHost.intValue());
/*     */ 
/*  67 */     params.setConnectionTimeout(connectionTimeout.intValue());
/*     */ 
/*  69 */     params.setSoTimeout(connectionTimeout.intValue());
/*     */ 
/*  71 */     this.client = new HttpClient(this.connectionManager);
/*     */ 
/*  73 */     HostConfiguration hostConf = this.client.getHostConfiguration();
/*     */ 
/*  75 */     hostConf.getParams().setParameter("http.protocol.content-charset", "UTF-8");
/*     */   }
/*     */ 
/*     */   public static OnLineHttpCall getSingleton()
/*     */   {
/*  93 */     if (onLineHttpCall == null) {
/*  94 */       synchronized (OnLineHttpCall.class) {
/*  95 */         if (onLineHttpCall == null) {
/*  96 */           onLineHttpCall = new OnLineHttpCall();
/*     */         }
/*     */       }
/*     */     }
/* 100 */     return onLineHttpCall;
/*     */   }
/*     */ 
/*     */   public byte[] remoteCall(String packet, String voucherValue, String transactionCode)
/*     */     throws Exception
/*     */   {
/* 107 */     byte[] resp = null;
/*     */ 
/* 109 */     PostMethod method = new PostMethod(Configuration.getInstance().getUrl());
/*     */ 
/* 112 */     method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(3, false));
/*     */ 
/* 115 */     method.addParameter("xmlContent", packet);
/*     */     try
/*     */     {
/* 119 */       long begin = System.currentTimeMillis();
/*     */ 
/* 122 */       int statusCode = this.client.executeMethod(method);
/*     */ 
/* 125 */       if (statusCode == 200)
/*     */       {
/* 127 */         resp = method.getResponseBody();
/*     */       }
/*     */ 
/* 130 */       String seqID = getNodeValue(packet, "SequenceId");
/* 131 */       logger.info("<<SPEND TIME>>调用线上验票系统, 接口号:" + transactionCode + ", 流水号: " + seqID + ", 媒介值: " + voucherValue + ", 用时:" + (System.currentTimeMillis() - begin) + "ms");
/*     */     }
/*     */     catch (Exception e) {
/* 134 */       logger.error(e.getMessage(), e);
/* 135 */       e.printStackTrace();
/* 136 */       throw e;
/*     */     } finally {
/* 138 */       method.releaseConnection();
/*     */     }
/* 140 */     return resp;
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.remote.OnLineHttpCall
 * JD-Core Version:    0.6.2
 */