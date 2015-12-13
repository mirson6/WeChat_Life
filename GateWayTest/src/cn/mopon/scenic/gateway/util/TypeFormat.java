/*     */ package cn.mopon.scenic.gateway.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Random;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class TypeFormat
/*     */ {
/* 258 */   private final char[] r = { 'Q', 'w', 'E', '8', 'a', 'S', '2', 'd', 'Z', 'x', '9', 'c', '7', 'p', 'O', '5', 'i', 'K', '3', 'm', 'j', 'U', 'f', 'r', '4', 'V', 'y', 'L', 't', 'N', '6', 'b', 'g', 'H' }; private final char[] b = { 'q', 'W', 'e', 'A', 's', 'D', 'z', 'X', 'C', 'P', 'o', 'I', 'k', 'M', 'J', 'u', 'F', 'R', 'v', 'Y', 'T', 'n', 'B', 'G', 'h' }; private final int l = 34; private final int s = 6;
/*     */ 
/*     */   public String iosToUtf(String str)
/*     */     throws Exception
/*     */   {
/*  30 */     if ((str != null) && (str.length() > 0)) {
/*  31 */       return new String(str.getBytes("ISO-8859-1"), 
/*  32 */         Charset.forName("UTF-8"));
/*     */     }
/*  34 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/*  44 */     Calendar calendar = Calendar.getInstance(
/*  45 */       TimeZone.getTimeZone("GMT+08:00"));
/*  46 */     Date date = calendar.getTime();
/*  47 */     return date;
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date)
/*     */   {
/*  57 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  58 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String formatCNDate(Date date)
/*     */   {
/*  68 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年  M 月 d 日 HH 点 mm 分 ss 秒");
/*  69 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String formatCNSimple(Date date)
/*     */   {
/*  79 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年  M 月 d 日");
/*  80 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String fromatSimple(Date date)
/*     */   {
/*  90 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*  91 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public String fromatMother(Date date)
/*     */   {
/* 101 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
/* 102 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public String formatTime(Date date)
/*     */   {
/* 112 */     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
/* 113 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String formatCNTime(Date date)
/*     */   {
/* 123 */     SimpleDateFormat sdf = new SimpleDateFormat("HH 点 mm 分 ss 秒");
/* 124 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public String getOrderSn(long supplierId)
/*     */   {
/* 134 */     SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
/* 135 */     String a = String.valueOf(System.currentTimeMillis()).substring(9);
/* 136 */     if (a.length() < 4)
/* 137 */       a = a + "0";
/* 138 */     return supplierId + sdf.format(new Date()) + a;
/*     */   }
/*     */ 
/*     */   public static String getDatetimeline()
/*     */   {
/* 148 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
/* 149 */     return sdf.format(new Date());
/*     */   }
/*     */ 
/*     */   public static String getCurrentTime()
/*     */   {
/* 159 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsssss");
/* 160 */     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
/* 161 */     Date date = calendar.getTime();
/* 162 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public Date formatString(String time)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 176 */       return sdf.parse(time);
/*     */     } catch (Exception e) {
/* 178 */       e.printStackTrace();
/*     */     }
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   public Date formatStringFull(String time)
/*     */   {
/*     */     try
/*     */     {
/* 192 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 193 */       return sdf.parse(time);
/*     */     } catch (Exception e) {
/* 195 */       e.printStackTrace();
/*     */     }
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getToday()
/*     */   {
/* 207 */     Calendar now = Calendar.getInstance();
/* 208 */     now.set(11, 0);
/* 209 */     now.set(12, 0);
/* 210 */     now.set(13, 0);
/* 211 */     now.set(14, 0);
/*     */ 
/* 213 */     return now.getTime();
/*     */   }
/*     */ 
/*     */   public Date getDateAft(Date d, int day)
/*     */   {
/* 222 */     Calendar now = Calendar.getInstance();
/* 223 */     now.setTime(d);
/* 224 */     now.set(5, now.get(5) + day);
/*     */ 
/* 226 */     return now.getTime();
/*     */   }
/*     */ 
/*     */   public Date getDateBefore(Date d, int day)
/*     */   {
/* 235 */     Calendar now = Calendar.getInstance();
/* 236 */     now.setTime(d);
/* 237 */     now.set(5, now.get(5) - day);
/*     */ 
/* 239 */     return now.getTime();
/*     */   }
/*     */ 
/*     */   public int toInt(String str, int defaultValue)
/*     */   {
/* 248 */     if (str == null)
/* 249 */       return defaultValue;
/*     */     try
/*     */     {
/* 252 */       return Integer.parseInt(str); } catch (NumberFormatException nfe) {
/*     */     }
/* 254 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public String toSerialNumber(long num)
/*     */   {
/* 273 */     char[] buf = new char[32];
/*     */ 
/* 275 */     int charPos = 32;
/*     */ 
/* 277 */     while (num / 34L > 0L)
/*     */     {
/* 279 */       buf[(--charPos)] = this.r[((int)(num % 34L))];
/*     */ 
/* 281 */       num /= 34L;
/*     */     }
/*     */ 
/* 285 */     buf[(--charPos)] = this.r[((int)(num % 34L))];
/*     */ 
/* 287 */     String str = new String(buf, charPos, 32 - charPos);
/*     */ 
/* 289 */     if (str.length() < 6)
/*     */     {
/* 291 */       StringBuffer sb = new StringBuffer();
/*     */ 
/* 293 */       Random rnd = new Random();
/*     */ 
/* 295 */       for (int i = 0; i < 6 - str.length(); i++)
/*     */       {
/* 297 */         sb.append(this.b[rnd.nextInt(24)]);
/*     */       }
/*     */ 
/* 301 */       str = str + sb.toString();
/*     */     }
/*     */ 
/* 305 */     return str;
/*     */   }
/*     */ 
/*     */   public String formatMoney(double money)
/*     */   {
/* 310 */     DecimalFormat dFormat = new DecimalFormat("#,##0.00");
/* 311 */     return dFormat.format(money);
/*     */   }
/*     */ 
/*     */   public String formatDouble(double value) {
/* 315 */     DecimalFormat dFormat = new DecimalFormat("##.00");
/* 316 */     return dFormat.format(value);
/*     */   }
/*     */ 
/*     */   public Date getAddTime()
/*     */   {
/* 322 */     Calendar cal = Calendar.getInstance();
/* 323 */     cal.setTime(new Date());
/* 324 */     cal.add(10, 1);
/* 325 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static String digitStr(String value, int capacity) {
/* 329 */     String temp = "";
/* 330 */     if (value.length() != capacity) {
/* 331 */       for (int i = 0; i < capacity - value.length(); i++) {
/* 332 */         temp = temp + "0";
/*     */       }
/* 334 */       value = temp + value;
/*     */     }
/* 336 */     return value;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 341 */     System.out.println(digitStr("222222", 16));
/*     */   }
/*     */ }

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.util.TypeFormat
 * JD-Core Version:    0.6.2
 */