/*    */ package cn.mopon.scenic.gateway.service;
/*    */ 
/*    */ import cn.mopon.scenic.gateway.service.impl.WSTicketServiceImpl;
/*    */ import cn.mopon.scenic.gateway.util.TypeFormat;
/*    */ import fr.irec.www.webserviceac.CheckTicket;
/*    */ import fr.irec.www.webserviceac.TimeoutTicket;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class WSMain
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 17 */     IWSTicketService ws = new WSTicketServiceImpl();
/* 18 */     CheckTicket ct = new CheckTicket();
/* 19 */     ct.setCodeControlPoint("80001zk-10");
/* 20 */     ct.setBarCodeTicket("00100645696841");
/* 21 */     ct.setEntryDateTimeString(TypeFormat.getDatetimeline());
/* 22 */     ct.setIsExit(false);
/* 23 */     ct.setTransactionId("01001010000101");
/*    */ 
/* 25 */     TimeoutTicket ticket = new TimeoutTicket();
/*    */ 
/* 27 */     ticket.setBarCodeTicket("123123123");
/* 28 */     ticket.setCodeControlPoint("12321321");
/* 29 */     boolean res = ws.timeOutTicket(ticket);
/* 30 */     System.out.println(res);
/*    */   }
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.service.WSMain
 * JD-Core Version:    0.6.2
 */