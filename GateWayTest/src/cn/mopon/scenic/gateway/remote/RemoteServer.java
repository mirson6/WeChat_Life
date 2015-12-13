/*    */ package cn.mopon.scenic.gateway.remote;
/*    */ 
/*    */ import cn.mopon.scenic.gateway.constant.TransactionNo;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */   
/*    */ public abstract class RemoteServer
/*    */ {
/*    */   public static byte[] invokeRemoteServer(String packet)
/*    */     throws Exception
/*    */   {
/* 24 */     String transactionCode = getNodeValue(packet, "TransactionCode");
/* 25 */     String readModel = getNodeValue(packet, "ReadModel");
/* 26 */     String mediaValue = null;
/* 27 */     if (transactionCode.equals(TransactionNo.V_GATEWAY_CHECK.name()))
/*    */     {  //test
/* 29 */       mediaValue = getNodeValue(packet, "MediaValue");
/*    */ 
/* 31 */       if ((mediaValue.startsWith("001")) || ("4".equals(readModel))) {
/* 32 */         return OffLineWsCall.getSingleton().remoteCall(packet, mediaValue, transactionCode);
/*    */       }
/*    */     }
/* 35 */     else if (transactionCode.equals(TransactionNo.V_GATEWAY_CONFIRM.name()))
/*    */     {
/* 37 */       mediaValue = getNodeValue(packet, "VoucherValue");
/* 38 */       if ((mediaValue.startsWith("001")) || ("4".equals(readModel))) {
/* 39 */         return OffLineWsCall.getSingleton().remoteCall(packet, mediaValue, transactionCode);
/*    */       }
/*    */     }
/*    */ 
/* 43 */     return OnLineHttpCall.getSingleton().remoteCall(packet, mediaValue, transactionCode);
/*    */   }
/*    */ 
/*    */   protected static String getNodeValue(String packet, String nodeName)
/*    */   {
/* 56 */     nodeName = Pattern.quote(nodeName);
/* 57 */     Pattern pattern = Pattern.compile("<" + nodeName + ">(.*?)</" + nodeName + ">");
/* 58 */     Matcher matcher = pattern.matcher(packet);
/*    */ 
/* 60 */     if (matcher.find()) {
/* 61 */       return matcher.group(1);
/*    */     }
/*    */ 
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   protected static String replaceTemplate(String input, Map<String, String> replacements)
/*    */   {
/* 82 */     StringBuilder regexBuilder = new StringBuilder();
/* 83 */     Iterator it = replacements.keySet().iterator();
/* 84 */     regexBuilder.append(Pattern.quote((String)it.next()));
/*    */ 
/* 86 */     while (it.hasNext()) {
/* 87 */       regexBuilder.append('|').append(Pattern.quote((String)it.next()));
/*    */     }
/* 89 */     Matcher matcher = Pattern.compile(regexBuilder.toString()).matcher(input);
/* 90 */     StringBuffer out = new StringBuffer(input.length() + input.length() / 10);
/*    */ 
/* 92 */     while (matcher.find()) {
/* 93 */       matcher.appendReplacement(out, Matcher.quoteReplacement((String)replacements.get(matcher.group())));
/*    */     }
/* 95 */     matcher.appendTail(out);
/* 96 */     return out.toString();
/*    */   }
/*    */ 
/*    */   public abstract byte[] remoteCall(String paramString1, String paramString2, String paramString3)
/*    */     throws Exception;
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.remote.RemoteServer
 * JD-Core Version:    0.6.2
 */