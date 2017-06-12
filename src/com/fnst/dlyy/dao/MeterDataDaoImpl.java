package com.fnst.dlyy.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.fnst.dlyy.entity.Energyzl;
import com.fnst.dlyy.entity.MeterDailyData;
import com.fnst.dlyy.entity.MeterData;
import com.fnst.dlyy.util.MybatisUtil;

public class MeterDataDaoImpl {
	public static void main(String[] args) {
//		MeterData meterData = new MeterData();
//		meterData.setCollecttime("aaa");
		isMeterMonthTimeExist("2017-06-09 18:00:00");
	}
	public List<MeterData> getNowData() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<MeterData> meterNowDataList = meterDataDao.getNowData();
		session.close();
		return meterNowDataList;
	}

	public List<MeterData> getNewInitialData() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<MeterData> meterInitialDataList = meterDataDao.getNewInitialData();
		session.close();
		return meterInitialDataList;
	}

	public List<String> getNewInitialDataMeterName() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> initialDataMeterName = meterDataDao.getNewInitialDataMeterName();
		session.close();
		return initialDataMeterName;
	}

	public List<String> getNowDataMeterNames() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> nowDataMeterNameList = meterDataDao.getNowDataMeterNames();
		session.close();
		return nowDataMeterNameList;
	}

	public List<MeterDailyData> getLastDailyData() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<MeterDailyData> meterDailyDataList = meterDataDao.getLastDailyData();
		session.close();
		return meterDailyDataList;
	}

	public List<MeterData> getLastMonthData() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<MeterData> meterMonthDataList = meterDataDao.getLastMonthData();
		session.close();
		return meterMonthDataList;
	}

	public int getMetersCount() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		int metersCount = meterDataDao.getMetersCount();
		session.close();
		return metersCount;
	}

	public List<String> getMetersNames() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> meterNameList = meterDataDao.getMetersNames();
		session.close();
		return meterNameList;
	}

	public void addMeterNowData(MeterData meterNowData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.addMeterNowData(meterNowData);
		session.commit();
		session.close();
	}

	public void deleteAllNowData() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.deleteAllNowData();
		session.commit();
		session.close();
	}

	public void addMeterDailyData(MeterDailyData meterDailyData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.addMeterDailyData(meterDailyData);
		session.commit();
		session.close();
	}

	public void addMeterDailyInitialData(MeterData meterDailyData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.addMeterDailyInitialData(meterDailyData);
		session.commit();
		session.close();
	}

	public static void addMeterMonthData(MeterData meterMonthData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.addMeterMonthData(meterMonthData);
		session.commit();
		session.close();
	}

	public void addEnergyzl(Energyzl energyzl) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.addEnergyzl(energyzl);
		session.commit();
		session.close();
System.out.println("-----------------add energyzl success!");
	}

	public void updateMeterDailyData(MeterDailyData meterDailyData, String timeType) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		if (timeType.equals("valley")) {
			meterDataDao.updateMeterDailyDataValley(meterDailyData);
		} else if (timeType.equals("normal")) {
			meterDataDao.updateMeterDailyDataNormal(meterDailyData);
		} else if (timeType.equals("tip")) {
			meterDataDao.updateMeterDailyDataTip(meterDailyData);
		} else if (timeType.equals("peak")) {
			meterDataDao.updateMeterDailyDataPeak(meterDailyData);
		} else if (timeType.equals("dailyEnd")) {
			meterDataDao.updateMeterDailyDataValley(meterDailyData);
		}
		session.commit();
		session.close();
	}

	public void updateMeterMonthData(MeterData meterMonthData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.updateMeterMonthData(meterMonthData);
		session.commit();
		session.close();
		System.out.println("-----------------month data update success!");
	}

	public void updateMeterNowData(MeterData meterNowData) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.updateMeterNowData(meterNowData);
		session.commit();
		session.close();
		System.out.println("-----------------now data update success!");
	}

	public void updateEnergyzl(Energyzl energyzl) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		meterDataDao.updateEnergyzl(energyzl);
		session.commit();
		session.close();
		System.out.println("-----------------energyzl update success!");
	}

	public List<String> getMeterTypeList(String build) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> meterTypeList = meterDataDao.getAllMeterTypeByBuild(build);
		session.close();
		return meterTypeList;
	}

	public String getElecTotal(String elecType, String meterType, String statisticMonth) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String total = meterDataDao.getElecTotal(elecType, meterType, statisticMonth);
		session.close();
		return total;
	}

	public String getWaterTotal(String meterType, String statisticMonth) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String total = meterDataDao.getWaterTotal(meterType, statisticMonth);
		session.close();
		return total;
	}

	public List<String> getBuildList() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> buildList = meterDataDao.getAllBuild();
		session.close();
		return buildList;
	}

	public boolean isEnergyzlExist(String date, String item) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<Object> energyzlList = meterDataDao.getEnergyzlByDateAndItem(date, item);
		session.close();
		if (!energyzlList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMeterMonthTimeExist(String collectTime) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> list = meterDataDao.getMeterMonthTime(collectTime);
		session.close();
		if (!list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMeterDailyTimeExist(String collectTime) {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		List<String> list = meterDataDao.getMeterDailyTime(collectTime);
		session.close();
		if (!list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String getElecPrice() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String price = meterDataDao.getElecPrice();
		session.close();
		return price;
	}

	public String getWaterPrice() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String price = meterDataDao.getWaterPrice();
		session.close();
		return price;
	}

	public String getGasPrice() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String price = meterDataDao.getGasPrice();
		session.close();
		return price;
	}

	public String getWarmPrice() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String price = meterDataDao.getWarmPrice();
		session.close();
		return price;
	}

	public String getOilPrice() {
		SqlSession session = MybatisUtil.getSqlSession();
		MeterDataDao meterDataDao = session.getMapper(MeterDataDao.class);
		String price = meterDataDao.getOilPrice();
		session.close();
		return price;
	}

}
