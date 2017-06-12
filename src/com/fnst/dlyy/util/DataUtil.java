package com.fnst.dlyy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import com.fnst.dlyy.properties.SystemProperties;

public class DataUtil {
//	private static String tip1 = "13:00:00";
//	private static String tip1_pre = "12:00:00";
//	private static String tip2 = "17:00:00";
//	private static String tip2_pre = "16:00:00";
//	private static String peak1 = "15:00:00";
//	private static String peak1_pre = "14:00:00";
//	private static String peak2 = "21:00:00";
//	private static String peak2_pre = "20:00:00";
//	private static String valley = "07:00:00";
//	private static String valley_pre = "06:00:00";
//	private static String normal1 = "18:00:00";
//	private static String normal1_pre = "17:00:00";
//	private static String normal2 = "23:00:00";
//	private static String normal2_pre = "22:00:00";
//	private static String dailyEnd = "24:00:00";
//	private static String dailyEnd_pre = "23:00:00";

	private static String summer_tip1 = "13:00:00";
	private static String summer_tip1_pre = "12:00:00";
	private static String summer_tip2 = "17:00:00";
	private static String summer_tip2_pre = "16:00:00";
	private static String summer_peak1 = "12:00:00";
	private static String summer_peak1_pre = "10:00:00";
	private static String summer_peak2 = "15:00:00";
	private static String summer_peak2_pre = "13:00:00";
	private static String summer_peak3 = "21:00:00";
	private static String summer_peak3_pre = "18:00:00";
	private static String summer_valley = "07:00:00";
	private static String summer_valley_pre = "00:00:00";
	private static String summer_normal1 = "10:00:00";
	private static String summer_normal1_pre = "07:00:00";
	private static String summer_normal2 = "16:00:00";
	private static String summer_normal2_pre = "15:00:00";
	private static String summer_normal3 = "18:00:00";
	private static String summer_normal3_pre = "17:00:00";
	private static String summer_normal4 = "23:00:00";
	private static String summer_normal4_pre = "21:00:00";
	private static String summer_dailyEnd = "24:00:00";
	private static String summer_dailyEnd_pre = "23:00:00";

	private static String peak1 = "15:00:00";
	private static String peak1_pre = "10:00:00";
	private static String peak2 = "21:00:00";
	private static String peak2_pre = "18:00:00";
	private static String valley = "07:00:00";
	private static String valley_pre = "00:00:00";
	private static String normal1 = "10:00:00";
	private static String normal1_pre = "07:00:00";
	private static String normal2 = "18:00:00";
	private static String normal2_pre = "15:00:00";
	private static String normal3 = "23:00:00";
	private static String normal3_pre = "21:00:00";
	private static String dailyEnd = "24:00:00";
	private static String dailyEnd_pre = "23:00:00";
	
	private static String [] SummerMonth = {"07", "08"};
	
	public static String [] ElecType = {"tip","peak","valley","normal","dailyEnd"};
	
	public static Date getYearMonthDate (String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		try {
			System.out.println(date);
			newDate = sdf.parse(date+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static String getYearMonthDayStr(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String str=sdf.format(date);
		return str+"-01";
	}

	public static String getDateStr(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String str=sdf.format(date);
		return str;
	}


	public static String getTimeStr(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String str=sdf.format(date);
		return str;
	}
	
	public static String getTimeStr(String date) {
		return date.substring(date.indexOf(" ")+1, date.length());
	}
	
	public static String getMonthStr(String date) {
		return date.substring(date.indexOf("-")+1, date.indexOf("-")+3);
	}
	
	public static String getYearMonthStr(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String str=sdf.format(date);
		return str;
	}
	
	public static String getStrSub(String param1, String param2) {
		float num1 = 0;
		float num2 = 0;
		if (param1 != null && !param1.isEmpty() && !param1.equals("")) {
			num1 = Float.parseFloat(param1);
		}
		if (param2 != null && !param2.isEmpty() && !param2.equals("")) {
			num2 = Float.parseFloat(param2);
		}
		return num1 - num2 + "";
	}
	public static String getStrSum(String param1, String param2) {
		float num1 = 0;
		float num2 = 0;
		if (param1 != null && !param1.isEmpty() && !param1.equals("")) {
			num1 = Float.parseFloat(param1);
		}
		if (param2 != null && !param2.isEmpty() && !param2.equals("")) {
			num2 = Float.parseFloat(param2);
		}
		return num1 + num2 + "";
	}
	public static String getStrProduct(String param1, String param2) {
		float num1 = 0;
		float num2 = 0;
		if (param1 != null && !param1.isEmpty() && !param1.equals("")) {
			num1 = Float.parseFloat(param1);
		}
		if (param2 != null && !param2.isEmpty() && !param2.equals("")) {
			num2 = Float.parseFloat(param2);
		}
		return num1 * num2 + "";
	}
	
	public static String getUUID() {
		String str = UUID.randomUUID().toString();
		return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}

	public static String judgeTimeType(String date) {
		System.out.println(date);
		String time = DataUtil.getTimeStr(date);
		System.out.println(time);
		boolean isSummerTime = checkSummerTime(date);
System.out.println("is Summer Time:"+isSummerTime);
		if (isSummerTime) {
			//0:谷   1:平    2:峰     3:尖    4:谷
			if (time.compareTo(summer_tip1_pre) > 0 && time.compareTo(summer_tip1) < 0 || time.compareTo(summer_tip2_pre) > 0 && time.compareTo(summer_tip2) < 0) {
				return ElecType[0];
			} else if (time.compareTo(summer_peak1_pre) > 0 && time.compareTo(summer_peak1) < 0 || time.compareTo(summer_peak2_pre) > 0 && time.compareTo(summer_peak2) < 0 || time.compareTo(summer_peak3_pre) > 0 && time.compareTo(summer_peak3) < 0) {
				return ElecType[1];
			} else if (time.compareTo(summer_valley_pre) > 0 && time.compareTo(summer_valley) < 0) {
				return ElecType[2];
			} else if (time.compareTo(summer_normal1_pre) > 0 && time.compareTo(summer_normal1) < 0 || time.compareTo(summer_normal2_pre) > 0 && time.compareTo(summer_normal2) < 0|| time.compareTo(summer_normal3_pre) > 0 && time.compareTo(summer_normal3) < 0|| time.compareTo(summer_normal4_pre) > 0 && time.compareTo(summer_normal4) < 0) {
				return ElecType[3];
			} else if (time.compareTo(summer_dailyEnd_pre) > 0 && time.compareTo(summer_dailyEnd) < 0) {
				return ElecType[4];
			} else {
				return null;
			}
		} else {
			//0:谷   1:平    2:峰     3:尖    4:谷
			//非夏季，没有尖
			if (time.compareTo(peak1_pre) > 0 && time.compareTo(peak1) < 0 || time.compareTo(peak2_pre) > 0 && time.compareTo(peak2) < 0) {
				return ElecType[1];
			} else if (time.compareTo(valley_pre) > 0 && time.compareTo(valley) < 0) {
				return ElecType[2];
			} else if (time.compareTo(normal1_pre) > 0 && time.compareTo(normal1) < 0 || time.compareTo(normal2_pre) > 0 && time.compareTo(normal2) < 0) {
				return ElecType[3];
			} else if (time.compareTo(dailyEnd_pre) > 0 && time.compareTo(dailyEnd) < 0) {
				return ElecType[4];
			} else {
				return null;
			}
		}
	}
	
	public static boolean checkSummerTime(String date) {
		boolean isSummerTime = false;
		String month = getMonthStr(date);
		for (int i = 0; i < SummerMonth.length; i++) {
			if (SummerMonth[i].equals(month)) {
				isSummerTime = true;
				break;
			}
		}
		return isSummerTime;
	}
	
	public static Date getTimeDate (String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = new Date();
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	
	public static boolean isMonthLastDay(Date date) {
		if (30 == date.getDay() && (4 == date.getMonth() || 6 == date.getMonth() || 9 == date.getMonth() || 11 == date.getMonth())) {
			return true;
		} else if (31 == date.getDay()) {
			return true;
		} else if (28 == date.getDay() && 2 == date.getMonth()) {
			return true;
		} else if (29 == date.getDay() && 2 == date.getMonth() && 0 == (date.getYear() % 4)) {
			return true;
		}
		return false;
	}
	
	public static boolean isDayLastCollect(Date date) {
		int intervalTime = Integer.parseInt(SystemProperties.getCollectIntervalTime());
		//15从配置文件类读取。记得改过来
		if (date.getHours() == 23 && (date.getMinutes() + intervalTime) > 60) {
			return true;
		}
		return false;
	}
	
	public static String getEnergyItem(String meterType) {
		if (meterType.contains("表")) {
			return meterType.substring(0, meterType.indexOf("表"));
		} else {
			return meterType;
		}
	}
	
	public static void readPropertiesFile() {
		Properties prop = new Properties();
		try {
			// 读取属性文件dlyy.properties
			File file = new File(DataUtil.class.getResource("/dlyy.properties").toURI());
			FileInputStream in = new FileInputStream(file);
			prop.load(in); //加载属性列表
			SystemProperties.setSummerMonth(prop.getProperty("SummerMonth"));
			SystemProperties.setCollectIntervalTime(prop.getProperty("CollectIntervalTime"));
			SystemProperties.setTipPrice(prop.getProperty("TipPrice"));
			SystemProperties.setValleyPrice(prop.getProperty("ValleyPrice"));
			SystemProperties.setNormalPrice(prop.getProperty("NormalPrice"));
			SystemProperties.setPeakPrice(prop.getProperty("PeakPrice"));
			SystemProperties.setWaterPrice(prop.getProperty("WaterPrice"));
			SystemProperties.setGasPrice(prop.getProperty("GasPrice"));
			SystemProperties.setHotPrice(prop.getProperty("HotPrice"));
			SystemProperties.setOilPrice(prop.getProperty("OilPrice"));
			
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void writePropertiesFile() throws IOException, URISyntaxException {
		Properties prop = new Properties();
		File file = new File(DataUtil.class.getResource("/dlyy.properties").toURI());
		FileInputStream in = new FileInputStream(file);
		prop.load(in); //加载属性列表
		System.out.println(DataUtil.class.getResource("/dlyy.properties").toString());
		System.out.println(DataUtil.class.getResource("/dlyy.properties").toURI());
		if(file.delete()) {
			System.out.println("success");
		}
		FileOutputStream oFile = new FileOutputStream(file);// true表示追加打开
		prop.setProperty("collectIntervalTime", "123");
		prop.store(oFile, "The New properties file");
		oFile.close();
		System.out.println("over");
	}

	public static String getYearMonthDayStr(String date) {
		return date.substring(0,date.lastIndexOf("-")+3);
	}

	public static Date getYearMonthDayDateToEnergyzl (String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		try {
			newDate = sdf.parse(date+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
}
