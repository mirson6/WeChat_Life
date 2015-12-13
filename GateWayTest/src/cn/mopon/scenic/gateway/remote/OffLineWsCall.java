/*     */ package cn.mopon.scenic.gateway.remote;
/*     */  // this is hostA changed HostA pust 2
/*     */  // this is hostA changed new hostB
 
// HOst B Add again
/*     */ import cn.mopon.scenic.gateway.constant.ResponseConstants;
/*     */ import cn.mopon.scenic.gateway.constant.TransactionNo;
/*     */ import cn.mopon.scenic.gateway.service.IWSTicketService;
/*     */ import cn.mopon.scenic.gateway.service.impl.WSTicketServiceImpl;
/*     */ import cn.mopon.scenic.gateway.util.Cache;
/*     */ import cn.mopon.scenic.gateway.util.TypeFormat;
/*     */ import fr.irec.www.webserviceac.CheckTicket;
/*     */ import fr.irec.www.webserviceac.CheckTicketResponse;
/*     */ import fr.irec.www.webserviceac.Passage;
/*     */ import fr.irec.www.webserviceac.PassageResponse;
/*     */ import fr.irec.www.webserviceac.Response;
/*     */ import fr.irec.www.webserviceac.ResponseCode;
/*     */ import fr.irec.www.webserviceac.TimeoutTicket;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class OffLineWsCall extends RemoteServer
/*     */ {  
/*  36 */   private static final Logger logger = LoggerFactory.getLogger(OffLineWsCall.class);
/*     */ 
/*  38 */   private static volatile OffLineWsCall offLineWsCall = null;
/*     */   
/*  40 */   private IWSTicketService wsTicketService = new WSTicketServiceImpl();
/*     */   
/*     */   public static OffLineWsCall getSingleton()
/*     */   {
/*  56 */     if (offLineWsCall == null) {
/*  57 */       synchronized (OffLineWsCall.class) {
/*  58 */         if (offLineWsCall == null) {
/*  59 */           offLineWsCall = new OffLineWsCall();
/*     */         }
/*     */       }
/*     */     }
/*  63 */     return offLineWsCall;
/*     */   }
/*     */ 
/*     */   private String remoteCallConfirm(String packet, String voucherValue)
/*     */   {
/*  76 */     logger.info("IREC票确认:" + voucherValue);
/*     */ 
/*  79 */     int nbEntry = 0;
/*  80 */     int nbEntryRemain = 0;
/*  81 */     String cvtResult = null;
/*  82 */     String deviceID = getNodeValue(packet, "DeviceNo");
/*  83 */     String seqID = getNodeValue(packet, "SequenceId");
/*  84 */     String codeControlPoint = Cache.getIrecCodeByEqNo(deviceID);
/*     */     try {
/*  86 */       nbEntry = Integer.valueOf(getNodeValue(packet, "PassNumber")).intValue();
/*  87 */       nbEntryRemain = Integer.valueOf(getNodeValue(packet, "CanPassNumber")).intValue();
/*     */     } catch (Exception e) {
/*  89 */       logger.error("offline wsCall confirm interface, the parameter PassNumber or CanPassNumber has error ==> " + e.getMessage(), e);
/*  90 */       Map params = new HashMap();
/*  91 */       params.put("${StatusCode}", "1");
/*  92 */       params.put("${Message}", "PassNumber or CanPassNumber 参数错误");
/*  93 */       return cvt2FailProtoclXml(params);
/*     */     }
/*  95 */     long begin = System.currentTimeMillis();
/*  96 */     if (nbEntry > 0) {
/*  97 */       Passage passage = new Passage();
/*  98 */       passage.setBarCodeTicket(voucherValue);
/*  99 */       passage.setEntryDateTimeString(TypeFormat.getDatetimeline());
/* 100 */       passage.setIsExit(false);
/* 101 */       passage.setNbEntry(nbEntry);
/*     */ 
/* 103 */       passage.setNbEntryRemain(nbEntryRemain);
/* 104 */       passage.setCodeControlPoint(codeControlPoint);
/*     */ 
/* 106 */       passage.setTransactionId(UUID.randomUUID().toString());
/*     */ 
/* 109 */       logger.info("调用Irec票去人消息参数: 通过人数:" + passage.getNbEntry() + ", 剩余可通过人数:" + 
/* 110 */         passage.getNbEntryRemain());
/*     */ 
/* 112 */       PassageResponse response = this.wsTicketService.passage(passage);
/*     */ 
/* 114 */       logger.info("IREC票确认返回信息:" + response.getPassageResult().getMessage());
/*     */ 
/* 116 */       logger.info("<<SPEND TIME>>调用IREC系统确认操作, 流水号: " + seqID + ", 用时: " + (System.currentTimeMillis() - begin));
/*     */ 
/* 118 */       cvtResult = cvt2SuccessConfirmProtoclXml(response, seqID, voucherValue);
/*     */     }
/*     */     else {
/* 121 */       logger.error("调用Irec超时确认包开始：, parameter barCodeTicket: " + voucherValue + ", CodeControlPoint: " + 
/* 122 */         codeControlPoint);
/*     */ 
/* 124 */       TimeoutTicket ticket = new TimeoutTicket();
/*     */ 
/* 128 */       ticket.setBarCodeTicket(voucherValue);
/* 129 */       ticket.setCodeControlPoint(codeControlPoint);
/* 130 */       boolean timeOutNotice = this.wsTicketService.timeOutTicket(ticket);
/*     */ 
/* 132 */       logger.error(" IREC票确认超时确认包,【result】: " + timeOutNotice);
/*     */ 
/* 134 */       logger.error("<<SPEND TIME>>调用IREC系统确认操作返回异常, 流水号: " + seqID + ", 用时:" + (System.currentTimeMillis() - begin));
/*     */ 
/* 136 */       Map params = new HashMap();
/* 137 */       params.put("${StatusCode}", "1");
/* 138 */       params.put("${Message}", "通过人数错误, 不能小于1");
/* 139 */       cvtResult = cvt2FailProtoclXml(params);
/*     */     }
/*     */ 
/* 143 */     return cvtResult;
/*     */   }
/*     */ 
/*     */   public byte[] remoteCall(String packet, String mediaValue, String transactionCode)
/*     */   {
/* 150 */     String cvtResult = null;
/* 151 */     if (transactionCode.equals(TransactionNo.V_GATEWAY_CHECK.name()))
/* 152 */       cvtResult = remoteCallConsume(packet, mediaValue);
/* 153 */     else if (transactionCode.equals(TransactionNo.V_GATEWAY_CONFIRM.name())) {
/* 154 */       cvtResult = remoteCallConfirm(packet, mediaValue);
/*     */     }
/* 156 */     return cvtResult.getBytes();
/*     */   }
/*     */ 
/*     */   private String remoteCallConsume(String packet, String mediaValue)
/*     */   {
/* 168 */     logger.info("验证IREC系统产生的门票");
/*     */ 
/* 170 */     String deviceID = getNodeValue(packet, "DeviceNo");
/* 171 */     String seqID = getNodeValue(packet, "SequenceId");
/* 172 */     long beginTime = System.currentTimeMillis();
/* 173 */     String trid = UUID.randomUUID().toString();
/* 174 */     CheckTicket ct = new CheckTicket();
/* 175 */     ct.setCodeControlPoint(Cache.getIrecCodeByEqNo(deviceID));
/* 176 */     ct.setBarCodeTicket(mediaValue);
/* 177 */     ct.setEntryDateTimeString(TypeFormat.getDatetimeline());
/* 178 */     ct.setIsExit(false);
/* 179 */     ct.setTransactionId(trid);
/* 180 */     CheckTicketResponse irecResponse = this.wsTicketService.checkTicket(ct);
/* 181 */     logger.info(irecResponse.toString());
/* 182 */     ResponseCode code = irecResponse.getCheckTicketResult().getCode();
/* 183 */     String message = irecResponse.getCheckTicketResult().getMessage();
/* 184 */     int entryCount = irecResponse.getCheckTicketResult().getEntryCount();
/* 185 */     String ticketBarcode = irecResponse.getCheckTicketResult().getTicketBarcode();
/* 186 */     int remainingEntry = irecResponse.getCheckTicketResult().getRemainingEntry();
/* 187 */     String reContent = "code:" + code.getValue() + "\n" + 
/* 188 */       "message:" + message + "\n" + 
/* 189 */       "entryCount:" + entryCount + "\n" + 
/* 190 */       "ticketBarcode:" + ticketBarcode + "\n" + 
/* 191 */       "remainingEntry:" + remainingEntry + "\n";
/* 192 */     String cvtResult = null;
/* 193 */     if ((code.getValue().equals(ResponseCode._TicketValid)) || 
/* 194 */       (code.getValue().equals(ResponseCode._TicketValidReentry)) || 
/* 195 */       (code.getValue().equals(ResponseCode._TicketValidOffline))) {
/* 196 */       logger.info("IREC验证票成功：" + reContent);
/*     */ 
/* 207 */       cvtResult = cvt2SuccessConsumeProtoclXml(irecResponse, seqID, mediaValue);
/*     */     } else {
/* 209 */       logger.error("IREC验证票失败：" + reContent);
/*     */ 
/* 212 */       Map params = new HashMap();
/* 213 */       params.put("${StatusCode}", "1");
/* 214 */       params.put("${Message}", message);
/* 215 */       cvtResult = cvt2FailProtoclXml(params);
/*     */     }
/*     */ 
/* 218 */     logger.info("<<SPEND TIME>>调用IREC系统验票操作, 流水号: " + seqID + ", 用时: " + (System.currentTimeMillis() - beginTime));
/* 219 */     return cvtResult;
/*     */   }
/*     */ 
/*     */   private String cvt2SuccessConfirmProtoclXml(PassageResponse resp, String seqId, String mediaValue)
/*     */   {
/* 231 */     Map params = new HashMap();
/* 232 */     params.put("${VoucherValue}", mediaValue);
/* 233 */     params.put("${CanPassNumber}", resp.getPassageResult().getRemainingEntry());
/* 234 */     int hadPassNumber = resp.getPassageResult().getEntryCount() - resp.getPassageResult().getRemainingEntry();
/* 235 */     if (hadPassNumber < 0) {
/* 236 */       logger.warn("offline wsCall, the entryCount is less than remainingCount, Please check : " + resp.getPassageResult().getTicketBarcode());
/*     */     }
/* 238 */     params.put("${HadPassNumber}", hadPassNumber);
/* 239 */     String xml = replaceTemplate(ResponseConstants.RESPONSE_GATES_CONSUME_CONFIRM_BODY, params);
/*     */ 
/* 242 */     params = new HashMap();
/* 243 */     params.put("${TimeStamp}", TypeFormat.getCurrentTime());
/* 244 */     params.put("${Body}", xml);
/* 245 */     params.put("${StatusCode}", "200");
/* 246 */     params.put("${Message}", "成功");
/* 247 */     params.put("${SequenceId}", seqId);
/*     */ 
/* 249 */     xml = replaceTemplate("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Trade><Head><Version>1.0</Version><TimeStamp>${TimeStamp}</TimeStamp><SequenceId>${SequenceId}</SequenceId><StatusCode>${StatusCode}</StatusCode><Message>${Message}</Message></Head><Body>${Body}</Body></Trade>", params);
/*     */ 
/* 251 */     return xml;
/*     */   }
/*     */ 
/*     */   private String cvt2SuccessConsumeProtoclXml(CheckTicketResponse resp, String seqId, String mediaValue)
/*     */   {
/* 265 */     Map params = new HashMap();
/* 266 */     params.put("${DisplayMessage}", "验证成功，请通过！");
/* 267 */     params.put("${AllowEnterNumber}", resp.getCheckTicketResult().getEntryCount());
/* 268 */     params.put("${NeedAdminSwipe}", "1");
/* 269 */     params.put("${HadPassNumber}", resp.getCheckTicketResult().getRemainingEntry());
/* 270 */     int canPassNumber = resp.getCheckTicketResult().getEntryCount() - resp.getCheckTicketResult().getRemainingEntry();
/* 271 */     if (canPassNumber < 0) {
/* 272 */       logger.warn("offline wsCall, the entryCount is less than remainingCount, Please check : " + resp.getCheckTicketResult().getTicketBarcode());
/*     */     }
/* 274 */     params.put("${CanPassNumber}", canPassNumber);
/*     */ 
/* 278 */     params.put("${VoucherValue}", mediaValue);
/* 279 */     params.put("${MediaValue}", mediaValue);
/* 280 */     params.put("${MediaType}", "1");
/*     */ 
/* 282 */     params.put("${TransactionTime}", TypeFormat.formatDate(new Date()));
/*     */ 
/* 284 */     String xml = replaceTemplate(ResponseConstants.RESPONSE_GATES_CONSUME_BODY, params);
/*     */ 
/* 286 */     params = new HashMap();
/* 287 */     params.put("${TimeStamp}", TypeFormat.getCurrentTime());
/* 288 */     params.put("${Body}", xml);
/* 289 */     params.put("${StatusCode}", "200");
/* 290 */     params.put("${SequenceId}", seqId);
/* 291 */     params.put("${Message}", "成功");
/*     */ 
/* 293 */     xml = replaceTemplate("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Trade><Head><Version>1.0</Version><TimeStamp>${TimeStamp}</TimeStamp><SequenceId>${SequenceId}</SequenceId><StatusCode>${StatusCode}</StatusCode><Message>${Message}</Message></Head><Body>${Body}</Body></Trade>", params);
/*     */ 
/* 295 */     return xml;
/*     */   }
/*     */ 
/*     */   private String cvt2FailProtoclXml(Map<String, String> params)
/*     */   {
/* 306 */     params.put("${TimeStamp}", TypeFormat.getCurrentTime());
/* 307 */     params.put("${Body}", "");
/*     */ 
/* 309 */     return replaceTemplate("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Trade><Head><Version>1.0</Version><TimeStamp>${TimeStamp}</TimeStamp><SequenceId>${SequenceId}</SequenceId><StatusCode>${StatusCode}</StatusCode><Message>${Message}</Message></Head><Body>${Body}</Body></Trade>", params);
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.remote.OffLineWsCall
 * JD-Core Version:    0.6.2
 */