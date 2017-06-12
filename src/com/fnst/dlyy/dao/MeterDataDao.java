package com.fnst.dlyy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnst.dlyy.entity.Energyzl;
import com.fnst.dlyy.entity.MeterDailyData;
import com.fnst.dlyy.entity.MeterData;

public interface MeterDataDao {
	public List<MeterData> getNowData();
	public List<MeterData> getNewInitialData();
	public List<MeterDailyData> getLastDailyData();
	public List<MeterData> getLastMonthData();
	public List<String> getNewInitialDataMeterName();
	public List<String> getNowDataMeterNames();

	//获取各项能耗单价接口
	public String getElecPrice();
	public String getWaterPrice();
	public String getGasPrice();
	public String getWarmPrice();
	public String getOilPrice();

	public void addMeterNowData(MeterData meterNowData);
	public void addMeterDailyData(MeterDailyData meterDailyData);
	public void addMeterDailyInitialData(MeterData meterDailyData);
	public void addMeterMonthData(MeterData meterMonthData);
	public void addEnergyzl(Energyzl energyzl);
	
	public void deleteAllNowData();
	
	public void updateMeterDailyDataTip(MeterDailyData meterDailyData);
	public void updateMeterDailyDataPeak(MeterDailyData meterDailyData);
	public void updateMeterDailyDataValley(MeterDailyData meterDailyData);
	public void updateMeterDailyDataNormal(MeterDailyData meterDailyData);
	public void updateMeterNowData(MeterData meterNowData);
	public void updateMeterMonthData(MeterData meterMonthData);
	public void updateEnergyzl(Energyzl energyzl);
	
	public int getMetersCount();
	public List<String> getMetersNames();
	public List<String> getAllMeterTypeByBuild(String build);
	public String getElecTotal(@Param("electype")String ELECTYPE, @Param("metertype") String METERTYPE, @Param("statisticmonth") String STATISTICMONTH);
	public String getWaterTotal(@Param("metertype") String METERTYPE, @Param("statisticmonth") String STATISTICMONTH);
	public List<String> getAllBuild();
	
	public List<Object> getEnergyzlByDateAndItem(@Param("shijian") String SHIJIAN, @Param("xiangmu") String XIANGMU);
	public List<String> getMeterMonthTime (@Param("collecttime") String COLLECTTIME);
	public List<String> getMeterDailyTime(@Param("collecttime") String COLLECTTIME);
}
