/*    */ package cn.mopon.scenic.gateway.constant;
/*    */ 
/*    */ public class ResponseConstants
/*    */ {
/*    */   private static final String VERSION = "1.0";
/*    */   public static final String RESPONSE_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Trade><Head><Version>1.0</Version><TimeStamp>${TimeStamp}</TimeStamp><SequenceId>${SequenceId}</SequenceId><StatusCode>${StatusCode}</StatusCode><Message>${Message}</Message></Head><Body>${Body}</Body></Trade>";
/* 40 */   public static final String RESPONSE_BODY_EXTENDAREA = 300 + 
/* 34 */     "<FlexField1></FlexField1>" + 
/* 35 */     "<FlexField2></FlexField2>" + 
/* 36 */     "<FlexField3></FlexField3>" + 
/* 37 */     "<FlexField4></FlexField4>" + 
/* 38 */     "<FlexField5></FlexField5>" + 
/* 39 */     "<FlexField6></FlexField6>" + 
/* 40 */     "<FlexField7></FlexField7>";
/*    */ 
/* 53 */   public static final String RESPONSE_GATES_CONSUME_CONFIRM_BODY = 1000 + 
/* 46 */     "<Body>" + 
/* 47 */     "<VoucherValue>${VoucherValue}</VoucherValue>" + 
/* 48 */     "<CanPassNumber>${CanPassNumber}</CanPassNumber>" + 
/* 49 */     "<HadPassNumber>${HadPassNumber}</HadPassNumber>" + 
/* 50 */     "<ExtendArea>" + 
/* 51 */     RESPONSE_BODY_EXTENDAREA + 
/* 52 */     "</ExtendArea>" + 
/* 53 */     "</Body>";
/*    */ 
/* 93 */   public static final String RESPONSE_GATES_CONSUME_BODY = 1500 + 
/* 58 */     "<Body>" + 
/* 59 */     "<DisplayArea>" + 
/* 60 */     "<DisplayMessage>${DisplayMessage}</DisplayMessage>" + 
/* 61 */     "</DisplayArea>" + 
/* 62 */     "<ControlArea>" + 
/* 63 */     "<AllowEnterNumber>${AllowEnterNumber}</AllowEnterNumber>" + 
/* 64 */     "<NeedAdminSwipe>${NeedAdminSwipe}</NeedAdminSwipe>" + 
/* 65 */     "<NeedFingerPrint></NeedFingerPrint>" + 
/* 66 */     "<NeedHeadPic></NeedHeadPic>" + 
/* 67 */     "<NeedIdCard></NeedIdCard>" + 
/* 68 */     "<CanPassNumber>${CanPassNumber}</CanPassNumber>" + 
/* 69 */     "<HadPassNumber>${HadPassNumber}</HadPassNumber>" + 
/* 70 */     "</ControlArea>" + 
/* 72 */     "<DataArea>" + 
/* 73 */     "<VoucherId></VoucherId>" + 
/* 74 */     "<VoucherValue>${VoucherValue}</VoucherValue>" + 
/* 75 */     "<MediaValue>${MediaValue}</MediaValue>" + 
/* 76 */     "<MediaType>${MediaType}</MediaType>" + 
/* 77 */     "<FingerPrintData></FingerPrintData>" + 
/* 78 */     "<HeadPicData></HeadPicData>" + 
/* 79 */     "<IdCardData></IdCardData>" + 
/* 81 */     "<ComsumeAreaName></ComsumeAreaName>" + 
/* 82 */     "<TicketId></TicketId>" + 
/* 83 */     "<TicketName></TicketName>" + 
/* 84 */     "<TicketAliseName></TicketAliseName>" + 
/* 85 */     "<ChannelId></ChannelId>" + 
/* 86 */     "<ChannelName></ChannelName>" + 
/* 87 */     "<TransactionTime>${TransactionTime}</TransactionTime>" + 
/* 89 */     "</DataArea>" + 
/* 90 */     "<ExtendArea>" + 
/* 91 */     RESPONSE_BODY_EXTENDAREA + 
/* 92 */     "</ExtendArea>" + 
/* 93 */     "</Body>";
/*    */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.constant.ResponseConstants
 * JD-Core Version:    0.6.2
 */