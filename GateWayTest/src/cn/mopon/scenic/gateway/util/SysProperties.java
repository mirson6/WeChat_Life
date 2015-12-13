/*    */ package cn.mopon.scenic.gateway.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class SysProperties
/*    */ {
/* 24 */   private static Properties prop = null;
/*    */ 
/*    */   public static synchronized void initProperties(File propFile)
/*    */     throws Exception
/*    */   {
/* 39 */     if (prop == null)
/*    */     {
/* 41 */       Reader reader = null;
/*    */       try {
/* 43 */         reader = new FileReader(propFile);
/*    */       }
/*    */       catch (FileNotFoundException e) {
/* 46 */         throw new Exception("Missing configuration : " + e.getMessage(), e);
/*    */       }
/*    */       try
/*    */       {
/* 50 */         prop = new Properties();
/* 51 */         prop.load(reader);
/*    */       }
/*    */       catch (IOException e) {
/* 54 */         throw new Exception("can't read configuration : " + e.getMessage(), e);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getProperty(String key)
/*    */   {
/* 68 */     return prop.getProperty(key);
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.util.SysProperties
 * JD-Core Version:    0.6.2
 */