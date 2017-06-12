package com.fnst.dlyy.task;

import com.fnst.dlyy.entity.Energyzl;
import com.fnst.dlyy.entity.MeterDailyData;
import com.fnst.dlyy.entity.MeterData;
import com.fnst.dlyy.service.MeterDataService;
import com.fnst.dlyy.util.DataUtil;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTask {
	private MeterDataService meterDataService = new MeterDataService();
	public void execute() {
		Runnable updateNowAndDailyDataRun = new Runnable() {
			public void run() {
				//获取最新的采集到的一组数据
				List<MeterData> meterInitialDatas = meterDataService.getNewInitialData();
				if (meterInitialDatas == null) {
					return;
				}
				// 获取实时表日表月表中当前最近一组数据
				List<MeterData> meterNowDataList = meterDataService.getLastNowData();
				List<MeterDailyData> meterDailyDataList = meterDataService.getLastDailyData();
				List<MeterData> meterMonthDataList = meterDataService.getLastMonthData();
				System.out.println("get tables data success!!!");
				if (meterMonthDataList == null && meterDailyDataList == null
						&& meterNowDataList == null) {
					// 系统第一次运行时，外部数据表和日月表都为空，将数据导入日表月表
					meterDataService.addToMeterDataTable(meterInitialDatas);
					System.out.println("addToMeterDataTable success!!!");
				} else {
					Map<String, List<MeterData>> dataListMap = null;
					Map<String, List<MeterDailyData>> dailyDataListMap = null;

					String collectTime = meterInitialDatas.get(0).getCollecttime();
					String timeType = DataUtil.judgeTimeType(collectTime);
					//判断日期是否到第二天，是则用add的方式计算消耗，否则用update方式计算
//					if (meterDataService.isMeterDailyTimeExist(DataUtil.getYearMonthDayStr(collectTime))) {
//						dailyDataListMap = meterDataService.getDailyDataListMap(meterInitialDatas, meterDailyDataList, timeType, "update");
//						meterDataService.updateToDailyTable(dailyDataListMap.get("updateDataList"), timeType);
//						meterDataService.addToDailyTable(dailyDataListMap.get("addDataList"));
//					} else {
//						dailyDataListMap = meterDataService.getDailyDataListMap(meterInitialDatas, meterDailyDataList, timeType, "add");
//						meterDataService.addToDailyTable(dailyDataListMap.get("addDataList"));
//					}

					//判断日期是否到下个月，是则用add的方式计算消耗，否则用update方式计算
					if (meterDataService.isMeterMonthTimeExist(collectTime)) {
						dataListMap = meterDataService.getDataListMap(meterInitialDatas, meterMonthDataList, "update");
						meterDataService.updateToMonthTable(dataListMap.get("updateDataList"));
						System.out.println("updateToMonthTable success!!!");
						meterDataService.updateToNowTable(dataListMap.get("updateDataList"));
						System.out.println("updateToNowTable success!!!");
						meterDataService.addToMonthTable(dataListMap.get("addDataList"));
						System.out.println("addToMonthTable success!!!");
						meterDataService.addToNowTable(dataListMap.get("addDataList"));
						System.out.println("addToNowTable success!!!");
					} else {
						dataListMap = meterDataService.getDataListMap(meterInitialDatas, meterMonthDataList, "add");
						meterDataService.addToMonthTable(dataListMap.get("addDataList"));
						System.out.println("addToMonthTable success!!!");
						meterDataService.updateToNowTable(dataListMap.get("addDataList"));
						System.out.println("updateToNowTable success!!!");
					}
					System.out.println("success--------------------");

//					System.out.println("start to =====meterDataService.getEnergyzlList");
//					List<MeterData> meterDataList = dataListMap.get("addDataList");
//					meterDataList.addAll(dataListMap.get("updateDataList"));
//
//					List<Energyzl> energyzlList = meterDataService.getEnergyzlListFromMonthData(meterDataList);
//					// 添加到能耗总览表中
//					System.out.println("start to add energyzl");
//					meterDataService.addEnergyzl(energyzlList);
					// 收集到历史数据(20170606:应该跟日表增加一天时同步添加)
//					hisDataAnalysisBizc.execInsertHisData();
				}


//				//获取最新的采集到的一组数据
//				List<MeterData> meterInitialDatas = meterDataService.getNewInitialData();
//				//从原始数据表获取最新采集到的所有表计名称集合  & 从实时数据表中获取上一次采集到的所有表计名称的集合
//				List<String> initialDataMeterNameList = meterDataService.getNewInitialDataMeterName();
//				List<String> nowDataMeterNameList = meterDataService.getNowDataMeterNames();
//				//获取表计管理中录入的表计总数，和表计名称的集合
//				Date collectTime = new Date();
//				/** 打印采集时间*/
//				System.out.println("collectTime : " + DataUtil.getDateStr(collectTime));
//				/** 判断采集来的表计个数是否出现偏差，判断是否表计出现故障*/
//				//存放新增或缺失的表计名称
//				List<String> differentMeterNameList = new ArrayList<String>();
//				//判断now_data里面有没有数据，如果没有，即系统第一次运行，使用配置文件中的表计个数来判断
//				if (nowDataMeterNameList.isEmpty()) {
//					System.out.println("----------------------------system first run!");
//					meterDataService.initialMeterNowData(meterInitialDatas);
//				} else {
//					if (initialDataMeterNameList.size() == nowDataMeterNameList.size()) {
//						System.out.println("Number Of Meters Is Normal.");
//						/** (1) 将！原始！表的数据统计到！实时！表中*/
//						meterDataService.addMeterNowData(meterInitialDatas);
//					} else if (initialDataMeterNameList.size() > nowDataMeterNameList.size()) {
//						System.out.println("Collect Meter Number Is More.");
//						//采集的数据比原先的多，即 增加了一个采集器时
//						for (int i = 0; i < initialDataMeterNameList.size(); i++) {
//							if (!nowDataMeterNameList.contains(initialDataMeterNameList.get(i))) {
//								differentMeterNameList.add(initialDataMeterNameList.get(i));
//							}
//						}
//						//设置到系统参数类中通过配置文件与页面进行通信
//						if (!differentMeterNameList.isEmpty()) {
//							SystemProperties.setExtraMeterNameList(differentMeterNameList);
//							for (String string : differentMeterNameList) {
//								System.out.println(string + ": is new meter");
//							}
//						}
//						/** (1) 将！原始！表的数据统计到！实时！表中*/
//						meterDataService.addMeterNowData(meterInitialDatas);
//					} else if (initialDataMeterNameList.size() < nowDataMeterNameList.size()) {
//						System.out.println("Collect Meter Number Is Less.");
//						//采集的数据比原先的少，即 有采集器出现故障，没有采集到数据
//						for (int i = 0; i < nowDataMeterNameList.size(); i++) {
//							if (!initialDataMeterNameList.contains(nowDataMeterNameList.get(i))) {
//								differentMeterNameList.add(nowDataMeterNameList.get(i));
//							}
//						}
//						if (!differentMeterNameList.isEmpty()) {
//							SystemProperties.setLackMeterNameList(differentMeterNameList);
//							for (String string : differentMeterNameList) {
//								System.out.println(string + ": is lack meter");
//							}
//						}
//						/** (1) 将！原始！表的数据统计更新到！实时！表中*/
//						meterDataService.updateMeterNowData(meterInitialDatas);
//					} else {
//						System.out.println("=============Statistic Now Data Error!");
//					}
//				}
//
//				/** (2) 将！原始！表的数据统计到！每日！表中*/
//				meterDataService.addMeterDailyData(meterInitialDatas);
//				/** (3) 将！每日！表的数据统计到！每月！表中*/
//				/** 判断日期是否是月的最后一天*/
//				if (meterDataService.isNeedAddMonthData(collectTime)) {
//					meterDataService.addMeterMonthData();
//				}
//				/** (4) 计算各项能耗费用，统计到能耗总览中*/
//				String statisticMonth = DataUtil.getYearMonthStr(collectTime);
//				meterDataService.energyStatistic(statisticMonth);
			}
		};
		
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
	    service.scheduleAtFixedRate(updateNowAndDailyDataRun, 0, 30, TimeUnit.SECONDS);
	}
}
