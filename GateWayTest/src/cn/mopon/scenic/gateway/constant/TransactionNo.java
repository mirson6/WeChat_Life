/*     */ package cn.mopon.scenic.gateway.constant;
/*     */ 
/*     */ public enum TransactionNo
/*     */ {
/*  14 */   INIT_TERMINAL, 
/*     */ 
/*  19 */   USER_LOGIN,   
/*     */ 
/*  24 */   QUERY_APPLICATION, 
/*     */ 
/*  29 */   PAY_APPLICATION, 
/*     */ 
/*  34 */   REPRINT_AUTHORIZE, 
/*     */ 
/*  39 */   PADDING_TRAN, 
/*     */ 
/*  44 */   ACTIVE_TRAN, 
/*     */ 
/*  49 */   DAY_STATEMENT, 
/*     */ 
/*  54 */   V_GATEWAY_CHECK, 
/*     */ 
/*  59 */   V_GATEWAY_CONFIRM, 
/*     */ 
/*  64 */   V_GATEWAY_GATHER, 
/*     */ 
/*  69 */   V_POS_CHECK, 
/*     */ 
/*  74 */   V_POS_CONFIRM, 
/*     */ 
/*  79 */   V_POS_EXCHANGE, 
/*     */ 
/*  84 */   V_POS_REPRINT, 
/*     */ 
/*  89 */   USER_LOGIN_OUT;
/*     */ 
/*     */   public static TransactionNo parseTradeCodeToEnum(String tradeCode)
/*     */   {
/*  98 */     if ((tradeCode == null) || ("".equals(tradeCode)))
/*     */     {
/* 100 */       return null;
/*     */     }
/* 102 */     return valueOf(tradeCode);
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.constant.TransactionNo
 * JD-Core Version:    0.6.2
 */