/*     */ package cn.mopon.scenic.gateway.service.impl;
/*     */ 
/*     */ import cn.mopon.scenic.gateway.service.IWSTicketService;
/*     */ import cn.mopon.scenic.gateway.util.SysProperties;
/*     */ import fr.irec.www.webserviceac.CheckTicket;
/*     */ import fr.irec.www.webserviceac.CheckTicketResponse;
/*     */ import fr.irec.www.webserviceac.InitControlPoint2;
/*     */ import fr.irec.www.webserviceac.InitControlPoint2Response;
/*     */ import fr.irec.www.webserviceac.KeepAlive;
/*     */ import fr.irec.www.webserviceac.KeepAliveResponse;
/*     */ import fr.irec.www.webserviceac.Passage;
/*     */ import fr.irec.www.webserviceac.PassageResponse;
/*     */ import fr.irec.www.webserviceac.ServiceACStub;
/*     */ import fr.irec.www.webserviceac.TimeoutTicket;
/*     */ import fr.irec.www.webserviceac.TimeoutTicketResponse;
/*     */ import java.rmi.RemoteException;
/*     */ import org.apache.axis2.AxisFault;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class WSTicketServiceImpl
/*     */   implements IWSTicketService
/*     */ {
/*  29 */   protected static ServiceACStub stub = null;
/*     */ 
/*  31 */   private static final Logger logger = LoggerFactory.getLogger(WSTicketServiceImpl.class);
/*     */ 
/*     */   static
/*     */   {
/*     */     try {
/*  36 */       stub = new ServiceACStub(SysProperties.getProperty("irec.ws.tgendpoint"));
/*     */     } catch (AxisFault e) {
/*  38 */       logger.debug(" create WebService instance happen Exception ", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public InitControlPoint2Response initControlPoint(InitControlPoint2 initControlPoint2)
/*     */   {
/*  54 */     InitControlPoint2Response response = null;
/*     */     try {
/*  56 */       logger.debug("irec method initControlPoint");
/*  57 */       response = stub.initControlPoint2(initControlPoint2);
/*  58 */       logger.debug("response:" + response.getInitControlPoint2Result());
/*     */     } catch (RemoteException e) {
/*  60 */       e.printStackTrace();
/*     */     }
/*  62 */     return response;
/*     */   }
/*     */ 
/*     */   public String keepAlive(KeepAlive KeepAlive)
/*     */   {
/*  73 */     KeepAliveResponse response = null;
/*  74 */     String dateline = null;
/*     */     try {
/*  76 */       logger.debug("irec method KeepAlive");
/*  77 */       response = stub.keepAlive(KeepAlive);
/*  78 */       if (response.getKeepAliveResult() != null) {
/*  79 */         dateline = response.getKeepAliveResult();
/*     */       }
/*  81 */       logger.debug("response:" + dateline);
/*     */     } catch (RemoteException e) {
/*  83 */       e.printStackTrace();
/*     */     }
/*  85 */     return dateline;
/*     */   }
/*     */ 
/*     */   public CheckTicketResponse checkTicket(CheckTicket checkTicket)
/*     */   {
/*  96 */     CheckTicketResponse response = null;
/*     */     try {
/*  98 */       logger.debug("irec method checkTicket");
/*  99 */       response = stub.checkTicket(checkTicket);
/* 100 */       logger.debug("response:" + response.getCheckTicketResult());
/*     */     }
/*     */     catch (RemoteException e) {
/* 103 */       e.printStackTrace();
/*     */     }
/* 105 */     return response;
/*     */   }
/*     */ 
/*     */   public PassageResponse passage(Passage passage) {
/* 109 */     PassageResponse response = null;
/*     */     try {
/* 111 */       response = stub.passage(passage);
/*     */     }
/*     */     catch (RemoteException e) {
/* 114 */       e.printStackTrace();
/*     */     }
/* 116 */     return response;
/*     */   }
/*     */ 
/*     */   public boolean timeOutTicket(TimeoutTicket ticket) {
/*     */     try {
/* 121 */       TimeoutTicketResponse reponse = stub.timeoutTicket(ticket);
/* 122 */       return reponse.getTimeoutTicketResult();
/*     */     } catch (RemoteException e) {
/* 124 */       e.printStackTrace();
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.service.impl.WSTicketServiceImpl
 * JD-Core Version:    0.6.2
 */