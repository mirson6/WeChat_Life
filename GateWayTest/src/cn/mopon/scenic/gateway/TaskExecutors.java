/*    */ package cn.mopon.scenic.gateway;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListeningExecutorService;
/*    */ import com.google.common.util.concurrent.MoreExecutors;
/*    */ import java.util.concurrent.Executors;
/*    */ 
/*    */ public class TaskExecutors
/*    */ {
/*    */   private static final int WORKER_THREADS_NUMBER = 32;
/*    */   private ListeningExecutorService service;
/* 20 */   private static TaskExecutors instance = new TaskExecutors();
/*    */ 
/*    */   public ListeningExecutorService getService() {
/* 23 */     return this.service;
/*    */   }
/*    */ 
/*    */   public static TaskExecutors getInstance() {
/* 27 */     return instance;
/*    */   }
/*    */ 
/*    */   private TaskExecutors() {
/* 31 */     this.service = MoreExecutors.listeningDecorator(
/* 32 */       Executors.newFixedThreadPool(32));
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.TaskExecutors
 * JD-Core Version:    0.6.2
 */