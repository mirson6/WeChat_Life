/*    */ package cn.mopon.scenic.gateway.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Cache
/*    */ {
/* 21 */   private static Map<String, Object> value = new HashMap();
/*    */   public static final String IREC_CODE = "IREC_CODE";
/*    */ 
/*    */   public static void addValue(String key, Object obj)
/*    */   {
/* 29 */     value.put(key, obj);
/*    */   }
/*    */ 
/*    */   public static Object getValue(String key) {
/* 33 */     return value.get(key);
/*    */   }
/*    */ 
/*    */   public static void initIrecCodes()
/*    */     throws Exception
/*    */   {
/* 44 */     String irecEuqs = SysProperties.getProperty("sys.irec.code");
/*    */ 
/* 46 */     Map irecCodeMap = new HashMap();
/*    */     try {
/* 48 */       for (String str : irecEuqs.split(",")) {
/* 49 */         String[] t = str.split(":");
/* 50 */         if ((t[0] == null) || (t[0].equals("")) || (t[1] == null) || (t[1].equals(""))) {
/* 51 */           throw new Exception(" 配置文件格式错误 请检查 sys.irec.code的配置数据");
/*    */         }
/* 53 */         irecCodeMap.put(t[0].trim(), t[1].trim());
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 57 */       throw new Exception(" 配置文件格式错误 请检查 sys.irec.code的配置数据", e);
/*    */     }
/*    */ 
/* 60 */     addValue("IREC_CODE", irecCodeMap);
/*    */   }
/*    */ 
/*    */   public static String getIrecCodeByEqNo(String eqNo)
/*    */   {
/* 73 */     Map dataMap = (Map)getValue("IREC_CODE");
/*    */ 
/* 75 */     String res = (String)dataMap.get(eqNo);
/*    */ 
/* 77 */     if (res == null) {
/* 78 */       res = "80001zk-10";
/*    */     }
/*    */ 
/* 81 */     return res;
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.util.Cache
 * JD-Core Version:    0.6.2
 */