package com.fnst.dlyy.properties;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.javassist.expr.NewArray;

public class SystemProperties {
//	private static String tip1 = "13:00:00";
//	private static String tip1_pre = "12:45:00";
//	private static String tip2 = "17:00:00";
//	private static String tip2_pre = "16:45:00";
//	private static String peak1 = "15:00:00";
//	private static String peak1_pre = "14:45:00";
//	private static String peak2 = "21:00:00";
//	private static String peak2_pre = "20:45:00";
//	private static String valley = "07:00:00";
//	private static String valley_pre = "06:45:00";
//	private static String normal1 = "18:00:00";
//	private static String normal1_pre = "17:45:00";
//	private static String normal2 = "23:00:00";
//	private static String normal2_pre = "22:45:00";
//	private static String dailyEnd = "00:00:00";
//	private static String dailyEnd_pre = "23:45:00";
//	
//	private static String [] SummerMonth = {"07", "08"};
//	
//	public static String [] ElecType = {"valley","normal","peak","tip","dailyEnd"};
	
	//采集器个数
	private static int numberOfMeter;
	
	//夏天的月份
	private static String SummerMonth;
	
	//采集时间间隔
	private static String collectIntervalTime;
	
	//尖峰谷平时间段的电价
	private static String tipPrice;
	private static String valleyPrice;
	private static String normalPrice;
	private static String peakPrice;
	private static String waterPrice;
	private static String gasPrice;
	private static String hotPrice;
	private static String oilPrice;
	
	//缺失的表计名称
	private static List<String> lackMeterNameList  = new ArrayList<String>();
	
	//新增的表计名称
	private static List<String> extraMeterNameList = new ArrayList<String>();
	
	
	
	public static String getSummerMonth() {
		return SummerMonth;
	}
	public static void setSummerMonth(String summerMonth) {
		SummerMonth = summerMonth;
	}
	public static String getCollectIntervalTime() {
		return collectIntervalTime;
	}
	public static void setCollectIntervalTime(String collectIntervalTime) {
		SystemProperties.collectIntervalTime = collectIntervalTime;
	}
	public static String getTipPrice() {
		return tipPrice;
	}
	public static void setTipPrice(String tipPrice) {
		SystemProperties.tipPrice = tipPrice;
	}
	public static String getValleyPrice() {
		return valleyPrice;
	}
	public static void setValleyPrice(String valleyPrice) {
		SystemProperties.valleyPrice = valleyPrice;
	}
	public static String getNormalPrice() {
		return normalPrice;
	}
	public static void setNormalPrice(String normalPrice) {
		SystemProperties.normalPrice = normalPrice;
	}
	public static String getPeakPrice() {
		return peakPrice;
	}
	public static void setPeakPrice(String peakPrice) {
		SystemProperties.peakPrice = peakPrice;
	}
	public static String getWaterPrice() {
		return waterPrice;
	}
	public static void setWaterPrice(String waterPrice) {
		SystemProperties.waterPrice = waterPrice;
	}
	public static String getGasPrice() {
		return gasPrice;
	}
	public static void setGasPrice(String gasPrice) {
		SystemProperties.gasPrice = gasPrice;
	}
	public static String getHotPrice() {
		return hotPrice;
	}
	public static void setHotPrice(String hotPrice) {
		SystemProperties.hotPrice = hotPrice;
	}
	public static String getOilPrice() {
		return oilPrice;
	}
	public static void setOilPrice(String oilPrice) {
		SystemProperties.oilPrice = oilPrice;
	}
	public static List<String> getLackMeterNameList() {
		return lackMeterNameList;
	}
	public static void setLackMeterNameList(List<String> lackMeterNameList) {
		SystemProperties.lackMeterNameList = lackMeterNameList;
	}
	public static List<String> getExtraMeterNameList() {
		return extraMeterNameList;
	}
	public static void setExtraMeterNameList(List<String> extraMeterNameList) {
		SystemProperties.extraMeterNameList = extraMeterNameList;
	}
	public static int getNumberOfMeter() {
		return numberOfMeter;
	}
	public static void setNumberOfMeter(int numberOfMeter) {
		SystemProperties.numberOfMeter = numberOfMeter;
	}
}
