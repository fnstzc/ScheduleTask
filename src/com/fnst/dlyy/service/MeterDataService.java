package com.fnst.dlyy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fnst.dlyy.dao.MeterDataDaoImpl;
import com.fnst.dlyy.entity.Energyzl;
import com.fnst.dlyy.entity.MeterDailyData;
import com.fnst.dlyy.entity.MeterData;
import com.fnst.dlyy.properties.SystemProperties;
import com.fnst.dlyy.util.DataUtil;

public class MeterDataService {

    private static String pastDailyMeterNum = "0";
    private MeterDataDaoImpl meterDataDaoImpl = new MeterDataDaoImpl();

    public void initialMeterNowData(List<MeterData> meterInitialDatas) {
        for (MeterData meterInitialData : meterInitialDatas) {
            MeterData meterNowData = new MeterData();
            meterNowData.setMetertype(meterInitialData.getMetertype());
            meterNowData.setMetername(meterInitialData.getMetername());
            meterNowData.setMeternum(meterInitialData.getMeternum());
            meterNowData.setCollecttime(meterInitialData.getCollecttime());

            meterDataDaoImpl.addMeterNowData(meterNowData);
        }
        System.out.println("add now" + meterInitialDatas.get(0).getMetername() + "__" + meterInitialDatas.get(0).getMeternum() + "__" + meterInitialDatas.get(0).getCollecttime());
    }

    public void updateMeterNowData(List<MeterData> meterInitialDatas) {
        for (MeterData meterInitialData : meterInitialDatas) {
            MeterData meterNowData = new MeterData();
            meterNowData.setMetertype(meterInitialData.getMetertype());
            meterNowData.setMetername(meterInitialData.getMetername());
            meterNowData.setMeternum(meterInitialData.getMeternum());
            meterNowData.setCollecttime(meterInitialData.getCollecttime());

            meterDataDaoImpl.updateMeterNowData(meterNowData);
        }
        System.out.println("update now" + meterInitialDatas.get(0).getMetername() + "__" + meterInitialDatas.get(0).getMeternum() + "__" + meterInitialDatas.get(0).getCollecttime());
    }

    public void addMeterDailyData(List<MeterData> meterInitialDatas, List<MeterDailyData> pastMeterDailyDatas, String timeType) {
        System.out.println("----------------------start to add meter daily data---------------");
        System.out.println("add daily" + meterInitialDatas.get(0).getMetername() + "__" + meterInitialDatas.get(0).getMeternum() + "__" + meterInitialDatas.get(0).getCollecttime());
        String timePartConsume = null;
        for (int i = 0; i < meterInitialDatas.size(); i++) {
            for (int j = 0; j < pastMeterDailyDatas.size(); j++) {
                //通过表名确保是相同的表数据覆盖
                if (meterInitialDatas.get(i).getMetername().equals(pastMeterDailyDatas.get(j).getMetername())) {
                    MeterDailyData meterDailyData = new MeterDailyData();
                    meterDailyData.setMeternum(meterInitialDatas.get(i).getMeternum());
                    meterDailyData.setMetertype(meterInitialDatas.get(i).getMetertype());
                    meterDailyData.setMetername(meterInitialDatas.get(i).getMetername());
                    meterDailyData.setCollecttime(meterInitialDatas.get(i).getCollecttime());

                    //原始数据表中消耗示数减去最近一次日表中的消耗示数，得出目前的日消耗量newDailyConsume
                    timePartConsume = DataUtil.getStrSub(meterInitialDatas.get(i).getMeternum(), pastMeterDailyDatas.get(j).getMeternum());
                    String newDailyConsume = DataUtil.getStrSub(meterInitialDatas.get(i).getMeternum(), pastDailyMeterNum);
                    meterDailyData.setConsume(newDailyConsume);

                    /**谷段的时间是从新一天的凌晨开始，故谷段的消耗数据是向表中新增一条数据，其他时间段均为update本条数据*/
                    if (timeType.equals("valley")) {
                        meterDailyData.setMeternum_VALLEY(timePartConsume);
                        meterDataDaoImpl.addMeterDailyData(meterDailyData);
                    } else if (timeType.equals("normal")) {
                        //取出同一天内上次的normal时间段内的消耗量，加上本次的消耗量。
                        String pastNormalConsume = pastMeterDailyDatas.get(j).getMeternum_NORMAL();
                        String newNormalConsume = DataUtil.getStrSum(pastNormalConsume, timePartConsume);
                        meterDailyData.setMeternum_NORMAL(newNormalConsume);
                        meterDataDaoImpl.updateMeterDailyData(meterDailyData, "normal");
                    } else if (timeType.equals("tip")) {
                        //取出同一天内上次的tip时间段内的消耗量，加上本次的消耗量。
                        String pastTipConsume = pastMeterDailyDatas.get(j).getMeternum_TIP();
                        String newTipConsume = DataUtil.getStrSum(pastTipConsume, timePartConsume);
                        meterDailyData.setMeternum_TIP(newTipConsume);
                        meterDataDaoImpl.updateMeterDailyData(meterDailyData, "tip");
                    } else if (timeType.equals("peak")) {
                        //取出同一天内上次的peak时间段内的消耗量，加上本次的消耗量。
                        String pastPeakConsume = pastMeterDailyDatas.get(j).getMeternum_PEAK();
                        String newPeakConsume = DataUtil.getStrSum(pastPeakConsume, timePartConsume);
                        meterDailyData.setMeternum_PEAK(newPeakConsume);
                        meterDataDaoImpl.updateMeterDailyData(meterDailyData, "peak");
                    } else if (timeType.equals("dailyEnd")) {
                        //取出同一天内上次的dailyEnd时间段内的消耗量，加上本次的消耗量,更新到valley字段中。
                        String pastValleyConsume = pastMeterDailyDatas.get(j).getMeternum_VALLEY();
                        String newValleyConsume = DataUtil.getStrSum(pastValleyConsume, timePartConsume);
                        meterDailyData.setMeternum_VALLEY(newValleyConsume);
                        meterDataDaoImpl.updateMeterDailyData(meterDailyData, "valley");
                        //重置上一天的表计示数
                        pastDailyMeterNum = meterDailyData.getMeternum();
                    }
                }
            }
        }
        System.out.println("--------------------add meter daily data success!!!---------------------");
        System.out.println("timePartConsume" + timePartConsume);
        if (timeType.equals("valley")) {
            System.out.println("valley = " + timePartConsume);
        } else if (timeType.equals("normal")) {
            System.out.println("normal = " + DataUtil.getStrSum(timePartConsume, pastMeterDailyDatas.get(0).getMeternum()));
        } else if (timeType.equals("tip")) {
            System.out.println("tip = " + DataUtil.getStrSum(timePartConsume, pastMeterDailyDatas.get(0).getMeternum()));
        } else if (timeType.equals("peak")) {
            System.out.println("peak = " + DataUtil.getStrSum(timePartConsume, pastMeterDailyDatas.get(0).getMeternum()));
        } else if (timeType.equals("dailyEnd")) {
            System.out.println("dailyEnd = " + DataUtil.getStrSum(timePartConsume, pastMeterDailyDatas.get(0).getMeternum()));
        }
        System.out.println("pastDailyMeterNum is " + pastDailyMeterNum);
    }

    public void addInitialDailyData(List<MeterData> meterInitialDatas) {
        System.out.println("--------------------start to add meter initial daily data---------------------");
        for (MeterData meterInitialData : meterInitialDatas) {
            MeterDailyData meterDailyData = new MeterDailyData();
            meterDailyData.setMetertype(meterInitialData.getMetertype());
            meterDailyData.setMetername(meterInitialData.getMetername());
            meterDailyData.setMeternum(meterInitialData.getMeternum());
            meterDailyData.setCollecttime(meterInitialData.getCollecttime());
            meterDailyData.setConsume("0");
            //尖峰谷平用量字段初始赋值为0
            meterDailyData.setMeternum_VALLEY("0");
            meterDailyData.setMeternum_TIP("0");
            meterDailyData.setMeternum_PEAK("0");
            meterDailyData.setMeternum_NORMAL("0");
            meterDataDaoImpl.addMeterDailyData(meterDailyData);
            pastDailyMeterNum = meterDailyData.getMeternum();
        }
        System.out.println("add initial daily" + meterInitialDatas.get(0).getMetername() + "__" + meterInitialDatas.get(0).getMeternum() + "__" + meterInitialDatas.get(0).getCollecttime());
    }

    public void addInitialMonthData(List<MeterDailyData> pastMeterDailyDatas) {
        System.out.println("-------------------start to add meter initial month data -----------------------");
        for (MeterDailyData meterDailyData : pastMeterDailyDatas) {
            MeterData meterMonthData = new MeterData();
            meterMonthData.setMetertype(meterDailyData.getMetertype());
            meterMonthData.setMetername(meterDailyData.getMetername());
            meterMonthData.setMeternum(meterDailyData.getMeternum());
            meterMonthData.setCollecttime(meterDailyData.getCollecttime());
            meterMonthData.setConsume("0");
            meterDataDaoImpl.addMeterMonthData(meterMonthData);
        }
        System.out.println("add initial month" + pastMeterDailyDatas.get(0).getMetername() + "__" + pastMeterDailyDatas.get(0).getMeternum() + "__" + pastMeterDailyDatas.get(0).getCollecttime());
    }

    public void addMeterMonthData(List<MeterDailyData> pastMeterDailyDatas, List<MeterData> pastMeterMonthDatas) {
        System.out.println("-------------------start to add meter month data -----------------------");
        for (int i = 0; i < pastMeterDailyDatas.size(); i++) {
            for (int j = 0; j < pastMeterMonthDatas.size(); j++) {
                if (pastMeterDailyDatas.get(i).getMetername().equals(pastMeterMonthDatas.get(j).getMetername())) {
                    MeterData meterMonthData = new MeterData();
                    meterMonthData.setMetertype(pastMeterDailyDatas.get(i).getMetertype());
                    meterMonthData.setMetername(pastMeterDailyDatas.get(i).getMetername());
                    meterMonthData.setMeternum(pastMeterDailyDatas.get(i).getMeternum());
                    meterMonthData.setCollecttime(pastMeterDailyDatas.get(i).getCollecttime());
                    //用最近一次的日表中的读数，减去最近一次月表的示数，得出月消耗量
                    String newMonthConsume = DataUtil.getStrSub(pastMeterDailyDatas.get(i).getMeternum(), pastMeterMonthDatas.get(j).getMeternum());
                    meterMonthData.setConsume(newMonthConsume);
                    meterDataDaoImpl.addMeterMonthData(meterMonthData);
                }
            }
        }
        System.out.println("add month data" + pastMeterDailyDatas.get(0).getMetername() + "__" + pastMeterDailyDatas.get(0).getMeternum() + "__" + pastMeterDailyDatas.get(0).getCollecttime());
    }

    public Map<String, String> calcuElecCost(String meterType, String statisticMonth) {
        String tipConsume = meterDataDaoImpl.getElecTotal("meternum_tip", meterType, statisticMonth);
        String valleyConsume = meterDataDaoImpl.getElecTotal("meternum_normal", meterType, statisticMonth);
        String normalConsume = meterDataDaoImpl.getElecTotal("meternum_valley", meterType, statisticMonth);
        String peakConsume = meterDataDaoImpl.getElecTotal("meternum_peak", meterType, statisticMonth);
        System.out.println("tipConsume = " + tipConsume + "valleyConsume = " + valleyConsume + "normalConsume = " + normalConsume + "peakConsume = " + peakConsume);
        String tipCost = DataUtil.getStrProduct(tipConsume, SystemProperties.getTipPrice());
        String valleyCost = DataUtil.getStrProduct(valleyConsume, SystemProperties.getValleyPrice());
        String normalCost = DataUtil.getStrProduct(normalConsume, SystemProperties.getNormalPrice());
        String peakCost = DataUtil.getStrProduct(peakConsume, SystemProperties.getPeakPrice());
        System.out.println("tipCost = " + tipCost + "valleyCost = " + valleyCost + "normalCost = " + normalCost + "peakCost = " + peakCost);
        Map<String, String> totalMap = new HashMap<String, String>();
        totalMap.put("xiaohao", DataUtil.getStrSum(DataUtil.getStrSum(tipConsume, valleyConsume), DataUtil.getStrSum(normalConsume, peakConsume)));
        totalMap.put("feiyong", DataUtil.getStrSum(DataUtil.getStrSum(tipCost, valleyCost), DataUtil.getStrSum(normalCost, peakCost)));
        return totalMap;
    }

    public Map<String, String> calcuEnergyCost(String meterType, String statisticMonth) {
        String waterConsume = meterDataDaoImpl.getWaterTotal(meterType, statisticMonth);
        String waterCost = DataUtil.getStrProduct(waterConsume, SystemProperties.getWaterPrice());
        Map<String, String> totalMap = new HashMap<String, String>();
        totalMap.put("xiaohao", waterConsume);
        totalMap.put("feiyong", waterCost);
        return totalMap;
    }

    public List<MeterData> getNewInitialData() {
        List<MeterData> meterInitialDatas = meterDataDaoImpl.getNewInitialData();
        if (meterInitialDatas == null || meterInitialDatas.isEmpty()) {
            System.out.println("initial datas is null");
            return null;
        } else {
            System.out.println("get initial datas successful!!");
            return meterInitialDatas;
        }
    }

    public List<String> getNewInitialDataMeterName() {
        List<String> initialDataMeterNameList = meterDataDaoImpl.getNewInitialDataMeterName();
        if (initialDataMeterNameList == null || initialDataMeterNameList.isEmpty()) {
            return null;
        } else {
            return initialDataMeterNameList;
        }
    }

    public List<String> getNowDataMeterNames() {
        List<String> nowDataMeterNameList = meterDataDaoImpl.getNowDataMeterNames();
        if (nowDataMeterNameList == null || nowDataMeterNameList.isEmpty()) {
            return null;
        } else {
            return nowDataMeterNameList;
        }
    }

    public int getMetersNumber() {
        int metersCount = meterDataDaoImpl.getMetersCount();
        return metersCount;
    }

    public List<String> getMetersNames() {
        List<String> meterNameList = meterDataDaoImpl.getMetersNames();
        if (meterNameList == null || meterNameList.isEmpty()) {
            return null;
        } else {
            return meterNameList;
        }
    }

    public void deleteAllNowData() {
        meterDataDaoImpl.deleteAllNowData();
    }

    public List<String> getMetertypeList(String build) {
        List<String> meterTypeList = meterDataDaoImpl.getMeterTypeList(build);
        if (meterTypeList == null || meterTypeList.isEmpty()) {
            return null;
        } else {
            return meterTypeList;
        }
    }

    public List<String> getBuildList() {
        List<String> buildList = meterDataDaoImpl.getBuildList();
        if (buildList == null || buildList.isEmpty()) {
            return null;
        } else {
            return buildList;
        }
    }

    public void addEnergyzl(Map<String, String> energyMap) {
        Energyzl energyzl = new Energyzl();
        energyzl.setId(DataUtil.getUUID());
        energyzl.setJianzhu(energyMap.get("jianzhu"));
        energyzl.setShijian(DataUtil.getYearMonthDate((energyMap.get("shijian"))));
        energyzl.setXiangmu(energyMap.get("xiangmu"));
        energyzl.setXiaohao(Double.parseDouble(energyMap.get("xiaohao")));
        energyzl.setFeiyong(Double.parseDouble(energyMap.get("feiyong")));
        System.out.println(energyzl.toString());
        if (meterDataDaoImpl.isEnergyzlExist(energyMap.get("shijian"), energyMap.get("xiangmu"))) {
            System.out.println("updateEnergyzl===============================");
            meterDataDaoImpl.updateEnergyzl(energyzl);
        } else {
            System.out.println("addEnergyzl===============================");
            meterDataDaoImpl.addEnergyzl(energyzl);
        }
    }

    public boolean isNeedAddMonthData(Date collectTime) {
        if (DataUtil.isMonthLastDay(collectTime) && DataUtil.isDayLastCollect(collectTime)) {
            System.out.println("This collect time is the last collect of the month!!!");
            return true;
        } else {
            System.out.println("not need to collect month data!!!");
        }
        return false;
    }


    public void addMeterDailyData(List<MeterData> meterInitialDatas) {
        System.out.println("--------------------start to add meter daily data---------------------");
        List<MeterDailyData> pastMeterDailyDatas = getLastDailyData();

        if (!meterInitialDatas.isEmpty() || meterInitialDatas != null) {
            if (pastMeterDailyDatas.isEmpty() || pastMeterDailyDatas == null) {
                addInitialDailyData(meterInitialDatas);
            } else {
                //判断所属时间段和季节
                String currentDate = meterInitialDatas.get(0).getCollecttime();
                String timeType = DataUtil.judgeTimeType(currentDate);
                System.out.println("timeType is " + timeType);

                if (timeType != null) {
                    addMeterDailyData(meterInitialDatas, pastMeterDailyDatas, timeType);
                } else {
                    System.out.println("-------------------data not need to collect!");
                }
            }
        }
    }

    public void addMeterMonthData() {
        System.out.println("--------------------start to add meter month data---------------------");
        List<MeterDailyData> pastMeterDailyDatas = getLastDailyData();
        List<MeterData> pastMeterMonthDatas = getLastMonthData();
        if (!pastMeterDailyDatas.isEmpty() && pastMeterDailyDatas != null) {
            if (pastMeterMonthDatas.isEmpty() || pastMeterMonthDatas == null) {
                addInitialMonthData(pastMeterDailyDatas);
            } else {
                addMeterMonthData(pastMeterDailyDatas, pastMeterMonthDatas);
            }
        } else {
            System.out.println("--------------------past daily data is empty ---------------------------");
        }
        System.out.println("--------------------add meter month data success!!!---------------------");
    }

    public String calcuConsume(String pastMeterNum, String meterNum) {
        if (pastMeterNum != null && !pastMeterNum.isEmpty() || meterNum != null && !meterNum.isEmpty()) {
            return (Integer.parseInt(meterNum) - Integer.parseInt(pastMeterNum)) + "";
        } else {
            return null;
        }
    }

    public void energyStatistic(String statisticMonth) {
        System.out.println("start to statistic to energyzl------------------");
        List<String> meterTypeList = new ArrayList<String>();
        List<String> buildList = getBuildList();
        if (buildList.isEmpty()) {
            System.out.println("buildList is null");
        } else {
            for (String build : buildList) {
                meterTypeList = getMetertypeList(build);
                Map<String, String> energyMap = getEnergyMap(meterTypeList, statisticMonth, build);
                addEnergyzl(energyMap);
            }
        }
    }

    public Map<String, String> getEnergyMap(List<String> meterTypeList, String statisticMonth, String build) {
        System.out.println("start to create energymap-------------------------------");
        Map<String, String> energyMap = new HashMap<String, String>();
        if (meterTypeList != null && !meterTypeList.isEmpty()) {
            //统计不同能耗类型的总数和费用
            for (String meterType : meterTypeList) {
                if (meterType.contains(("电表"))) {
                    String parentMeterType = "电表";
                    energyMap = calcuElecCost(parentMeterType, statisticMonth);
                    energyMap.put("xiangmu", DataUtil.getEnergyItem(parentMeterType));
                } else {
                    energyMap = calcuEnergyCost(statisticMonth, meterType);
                    energyMap.put("xiangmu", DataUtil.getEnergyItem(meterType));
                }
                energyMap.put("shijian", statisticMonth);
                energyMap.put("jianzhu", build);
            }
        } else {
            System.out.println("meter type is null");
        }

        return energyMap;
    }

    public Map<String, List<MeterDailyData>> getDailyDataListMap(List<MeterData> newMeterDatas, List<MeterDailyData> lastMeterDataList,
                                                       String timeType, String type) {
        System.out.println("start to get data list");
        Map<String, List<MeterDailyData>> dataListMap = new HashMap<String, List<MeterDailyData>>();
        List<MeterDailyData> addDataList = new ArrayList<MeterDailyData>();
        List<MeterDailyData> updateDataList = new ArrayList<MeterDailyData>();

        if ("add".equals(type)) {
            for (MeterData newMeterData : newMeterDatas) {
                MeterDailyData newMeterDailyData = new MeterDailyData();
                int count = 0;
                boolean haveFound = false;
                for (MeterDailyData lastMeterData : lastMeterDataList) {
                    count++;
                    if (newMeterData.getMetername().equals(lastMeterData.getMetername())) {
                        newMeterDailyData.setMeternum(newMeterData.getMeternum());
                        newMeterDailyData.setMetername(newMeterData.getMetername());
                        newMeterDailyData.setMetertype(newMeterData.getMetertype());
                        newMeterDailyData.setCollecttime(newMeterData.getCollecttime());
                        newMeterDailyData.setConsume(newMeterData.getConsume());
                        newMeterDailyData.setMeternum_TIP("0");
                        newMeterDailyData.setMeternum_PEAK("0");
                        newMeterDailyData.setMeternum_VALLEY("0");
                        newMeterDailyData.setMeternum_NORMAL("0");

                        String consume = DataUtil.getStrSub(newMeterData.getMeternum(), lastMeterData.getMeternum());
                        if (timeType.equals("valley")) {
                            newMeterDailyData.setMeternum_VALLEY(consume);
                        } else if (timeType.equals("normal")) {
                            newMeterDailyData.setMeternum_NORMAL(consume);
                        } else if (timeType.equals("tip")) {
                            newMeterDailyData.setMeternum_TIP(consume);
                        } else if (timeType.equals("peak")) {
                            newMeterDailyData.setMeternum_PEAK(consume);
                        } else if (timeType.equals("dailyEnd")) {
                            newMeterDailyData.setMeternum_VALLEY(consume);
                        }
                        newMeterData.setConsume(DataUtil.getStrSub(newMeterData.getMeternum(), lastMeterData.getMeternum()));
                        addDataList.add(newMeterDailyData);
                        haveFound = true;
                        break;
                    }
                }
                if (count == lastMeterDataList.size() && !haveFound) {
                    addDataList.add(newMeterDailyData);
                }
            }
        } else if ("update".equals(type)) {
            for (MeterData newMeterData : newMeterDatas) {
                MeterDailyData newMeterDailyData = new MeterDailyData();
                int count = 0;
                boolean haveFound = false;
                for (MeterDailyData lastMeterData : lastMeterDataList) {
                    count++;
                    if (newMeterDailyData.getMetername().equals(lastMeterData.getMetername())) {
                        newMeterDailyData.setMeternum(newMeterData.getMeternum());
                        newMeterDailyData.setMetername(newMeterData.getMetername());
                        newMeterDailyData.setMetertype(newMeterData.getMetertype());
                        newMeterDailyData.setCollecttime(newMeterData.getCollecttime());
                        newMeterDailyData.setConsume(newMeterData.getConsume());
                        newMeterDailyData.setMeternum_TIP(lastMeterData.getMeternum_TIP());
                        newMeterDailyData.setMeternum_PEAK(lastMeterData.getMeternum_PEAK());
                        newMeterDailyData.setMeternum_VALLEY(lastMeterData.getMeternum_VALLEY());
                        newMeterDailyData.setMeternum_NORMAL(lastMeterData.getMeternum_NORMAL());

                        String consume = DataUtil.getStrSub(newMeterDailyData.getMeternum(), lastMeterData.getMeternum());
                        if (timeType.equals("valley")) {
                            newMeterDailyData.setMeternum_VALLEY(DataUtil.getStrSum(lastMeterData.getMeternum_VALLEY(), consume));
                        } else if (timeType.equals("normal")) {
                            newMeterDailyData.setMeternum_NORMAL(DataUtil.getStrSum(lastMeterData.getMeternum_NORMAL(), consume));
                        } else if (timeType.equals("tip")) {
                            newMeterDailyData.setMeternum_TIP(DataUtil.getStrSum(lastMeterData.getMeternum_TIP(), consume));
                        } else if (timeType.equals("peak")) {
                            newMeterDailyData.setMeternum_PEAK(DataUtil.getStrSum(lastMeterData.getMeternum_PEAK(), consume));
                        } else if (timeType.equals("dailyEnd")) {
                            newMeterDailyData.setMeternum_VALLEY(DataUtil.getStrSum(lastMeterData.getMeternum_VALLEY(), consume));
                        }
                        newMeterDailyData.setConsume(DataUtil.getStrSum(lastMeterData.getConsume(), consume));
                        updateDataList.add(newMeterDailyData);
                        haveFound = true;
                        break;
                    }
                }
                if (count == newMeterDatas.size() && !haveFound) {
                    addDataList.add(newMeterDailyData);
                }
            }
        } else {
            System.out.println("type is error!!");
            return null;
        }
        dataListMap.put("addDataList", addDataList);
        dataListMap.put("updateDataList", updateDataList);
        return dataListMap;
    }

    public Map<String, List<MeterData>> getDataListMap(List<MeterData> newMeterDatas, List<MeterData> lastMeterDataList,
                                                       String type) {
        System.out.println("start to get data list");
        Map<String, List<MeterData>> dataListMap = new HashMap<String, List<MeterData>>();
        List<MeterData> addDataList = new ArrayList<MeterData>();
        List<MeterData> updateDataList = new ArrayList<MeterData>();

        if ("add".equals(type)) {
            for (MeterData newMeterData : newMeterDatas) {
                int count = 0;
                boolean haveFound = false;
                for (MeterData lastMeterData : lastMeterDataList) {
                    count++;
                    if (newMeterData.getMetername().equals(lastMeterData.getMetername())) {
                        newMeterData.setConsume(DataUtil.getStrSub(newMeterData.getMeternum(),
                                lastMeterData.getMeternum()));
                        addDataList.add(newMeterData);
                        haveFound = true;
                        break;
                    }
                }
                if (count == lastMeterDataList.size() && !haveFound) {
                    addDataList.add(newMeterData);
                }
            }
        } else if ("update".equals(type)) {
            for (MeterData newMeterData : newMeterDatas) {
                int count = 0;
                boolean haveFound = false;
                for (MeterData lastMeterData : lastMeterDataList) {
                    count++;
                    if (newMeterData.getMetername().equals(
                            lastMeterData.getMetername())) {
                        newMeterData.setConsume(DataUtil.getStrSum(
                                lastMeterData.getConsume(), DataUtil.getStrSub(
                                        newMeterData.getMeternum(),
                                        lastMeterData.getMeternum())));
                        updateDataList.add(newMeterData);
                        haveFound = true;
                        break;
                    }
                }
                if (count == newMeterDatas.size() && !haveFound) {
                    addDataList.add(newMeterData);
                }
            }
        } else {
            System.out.println("type is error!!");
            return null;
        }

        dataListMap.put("addDataList", addDataList);
        dataListMap.put("updateDataList", updateDataList);
        return dataListMap;
    }

    public List<MeterData> getLastNowData() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MeterDailyData> getLastDailyData() {
        List<MeterDailyData> meterDailyDatas = meterDataDaoImpl.getLastDailyData();
        if (meterDailyDatas == null || meterDailyDatas.isEmpty()) {
            return null;
        } else {
            return meterDailyDatas;
        }
    }

    public List<MeterData> getLastMonthData() {
        List<MeterData> meterMonthDatas = meterDataDaoImpl.getLastMonthData();
        if (meterMonthDatas == null || meterMonthDatas.isEmpty()) {
            return null;
        } else {
            return meterMonthDatas;
        }
    }

    public void addToMeterDataTable(List<MeterData> meterInitialDatas) {
        for (MeterData meterData : meterInitialDatas) {
            addMeterDataToNowTable(meterData);//实时表
            addMeterDataToDailyTable(meterData);//日表
            addMeterDataToMonthTable(meterData);//月表
        }
    }

    private void addMeterDataToMonthTable(MeterData meterData) {
        meterDataDaoImpl.addMeterMonthData(meterData);
    }

    private void addMeterDataToDailyTable(MeterDailyData meterData) {
        meterDataDaoImpl.addMeterDailyData(meterData);
    }

    private void addMeterDataToDailyTable(MeterData meterData) {
        meterDataDaoImpl.addMeterDailyInitialData(meterData);
    }

    private void addMeterDataToNowTable(MeterData meterData) {
        meterDataDaoImpl.addMeterNowData(meterData);
    }

    public boolean isMeterDailyTimeExist(String collectTime) {
        return meterDataDaoImpl.isMeterDailyTimeExist(collectTime);
    }

    public boolean isMeterMonthTimeExist(String collectTime) {
        return meterDataDaoImpl.isMeterMonthTimeExist(collectTime);
    }

    public void updateToMonthTable(List<MeterData> meterDatas) {
        System.out.println("start to update month data----");
        if (meterDatas != null) {
            for (MeterData meterData : meterDatas) {
                meterDataDaoImpl.updateMeterMonthData(meterData);
            }
        } else {
            System.out.println("Month meterDatas is null");
        }
    }

    public void updateToNowTable(List<MeterData> meterDatas) {
        System.out.println("start to update now data----");
        if (meterDatas != null) {
            for (MeterData meterData : meterDatas) {
                meterDataDaoImpl.updateMeterNowData(meterData);
            }
        } else {
            System.out.println("now meterDatas is null");
        }
    }

    public void addToNowTable(List<MeterData> meterDatas) {
        System.out.println("start to add now data----");
        if (meterDatas != null) {
            for (MeterData meterData : meterDatas) {
                meterDataDaoImpl.addMeterNowData(meterData);
            }
        } else {
            System.out.println("now meterDatas is null");
        }
    }

    public void addToDailyTable(List<MeterDailyData> meterDatas) {
        System.out.println("start to add daily data----");
        if (meterDatas != null && !meterDatas.isEmpty()) {
            for (MeterDailyData meterData : meterDatas) {
                meterDataDaoImpl.addMeterDailyData(meterData);
            }
        } else {
            System.out.println("daily meterDatas is null");
        }
    }

    public void addToMonthTable(List<MeterData> meterDatas) {
        System.out.println("start to add month data----");
        if (meterDatas != null && !meterDatas.isEmpty()) {
            for (MeterData meterData : meterDatas) {
                meterDataDaoImpl.addMeterMonthData(meterData);
            }
        } else {
            System.out.println("month meterDatas is null");
        }
    }

    public List<Energyzl> getEnergyzlListFromMonthData(List<MeterData> monthDataList) {
        List<Energyzl> energyzlList = new ArrayList<Energyzl>();

        Energyzl energyzlElec = new Energyzl();
        Energyzl energyzlWater = new Energyzl();

        Energyzl energyzlGas = new Energyzl();
        Energyzl energyzlWarm = new Energyzl();
        Energyzl energyzlOil = new Energyzl();

        //获取采集时间
        String collectTime = monthDataList.get(0).getCollecttime();

        // 总消耗量
        double elecTotalConsume = 0;
        double waterTotalConsume = 0;

        double gasTotalConsume = 0;
        double warmTotalConsume = 0;
        double oilTotalConsume = 0;

        // 总费用
        String elecTotalCost = null;
        String waterTotalCost = null;

        String gasTotalCost = null;
        String warmTotalCost = null;
        String oilTotalCost = null;

        for (MeterData monthData : monthDataList) {
            if (monthData.getMetertype().contains("电表")) {
                elecTotalConsume = Double.parseDouble(DataUtil.getStrSum(elecTotalConsume + "", monthData.getConsume()));
            } else if (monthData.getMetertype().contains("水表")) {
                waterTotalConsume = Double.parseDouble(DataUtil.getStrSum(waterTotalConsume + "", monthData.getConsume()));
            } else if (monthData.getMetertype().contains("气")) {
                gasTotalConsume = Double.parseDouble(DataUtil.getStrSum(gasTotalConsume + "", monthData.getConsume()));
            } else if (monthData.getMetertype().contains("热")) {
                warmTotalConsume = Double.parseDouble(DataUtil.getStrSum(warmTotalConsume + "", monthData.getConsume()));
            } else if (monthData.getMetertype().contains("油")) {
                oilTotalConsume = Double.parseDouble(DataUtil.getStrSum(oilTotalConsume + "", monthData.getConsume()));
            }
        }

        if (elecTotalConsume != 0) {
            elecTotalCost = DataUtil.getStrProduct(elecTotalConsume + "", meterDataDaoImpl.getElecPrice());
            energyzlElec.setId(DataUtil.getUUID());
            energyzlElec.setXiangmu("电");
            energyzlElec.setJianzhu("A座");
            energyzlElec.setShijian(DataUtil.getYearMonthDayDateToEnergyzl((collectTime)));
            energyzlElec.setXiaohao(Double.parseDouble(elecTotalConsume+""));
            energyzlElec.setFeiyong(Double.parseDouble(elecTotalCost));

            energyzlList.add(energyzlElec);
        }
        if (waterTotalConsume != 0) {
            waterTotalCost = DataUtil.getStrProduct(waterTotalConsume + "",	meterDataDaoImpl.getWaterPrice());

            energyzlWater.setId(DataUtil.getUUID());
            energyzlWater.setXiangmu("水");
            energyzlWater.setJianzhu("A座");
            energyzlWater.setShijian(DataUtil.getYearMonthDayDateToEnergyzl((collectTime)));
            energyzlWater.setXiaohao(Double.parseDouble(waterTotalConsume+""));
            energyzlWater.setFeiyong(Double.parseDouble(waterTotalCost));

            energyzlList.add(energyzlWater);
        }
        if (gasTotalConsume != 0) {
            gasTotalCost = DataUtil.getStrProduct(gasTotalConsume + "",	meterDataDaoImpl.getGasPrice());

            energyzlGas.setId(DataUtil.getUUID());
            energyzlGas.setXiangmu("天然气");
            energyzlGas.setJianzhu("A座");
            energyzlGas.setShijian(DataUtil.getYearMonthDayDateToEnergyzl((collectTime)));
            energyzlGas.setXiaohao(Double.parseDouble(gasTotalConsume+""));
            energyzlGas.setFeiyong(Double.parseDouble(gasTotalCost));

            energyzlList.add(energyzlGas);
        }
        if (warmTotalConsume != 0) {
            warmTotalCost = DataUtil.getStrProduct(warmTotalConsume + "",	meterDataDaoImpl.getWarmPrice());

            energyzlWarm.setId(DataUtil.getUUID());
            energyzlWarm.setXiangmu("热力");
            energyzlWarm.setJianzhu("A座");
            energyzlWarm.setShijian(DataUtil.getYearMonthDayDateToEnergyzl((collectTime)));
            energyzlWarm.setXiaohao(Double.parseDouble(warmTotalConsume+""));
            energyzlWarm.setFeiyong(Double.parseDouble(warmTotalCost));

            energyzlList.add(energyzlWarm);
        }
        if (oilTotalConsume != 0) {
            oilTotalCost = DataUtil.getStrProduct(oilTotalConsume + "",	meterDataDaoImpl.getOilPrice());

            energyzlOil.setId(DataUtil.getUUID());
            energyzlOil.setXiangmu("汽油");
            energyzlOil.setJianzhu("A座");
            energyzlOil.setShijian(DataUtil.getYearMonthDayDateToEnergyzl((collectTime)));
            energyzlOil.setXiaohao(Double.parseDouble(oilTotalConsume+""));
            energyzlOil.setFeiyong(Double.parseDouble(oilTotalCost));

            energyzlList.add(energyzlOil);
        }

        if (energyzlList == null || energyzlList.isEmpty()) {
            return null;
        } else {
            return energyzlList;
        }
    }

    public void addEnergyzl(List<Energyzl> energyzlList) {
        System.out.println("start to-----------------dataentryBizc.addEnergyzl");
        if (energyzlList != null && !energyzlList.isEmpty()) {
            for (Energyzl energyzl : energyzlList) {
                if (meterDataDaoImpl.isEnergyzlExist(DataUtil.getYearMonthDayStr(energyzl.getShijian()), energyzl.getXiangmu())) {
                    meterDataDaoImpl.updateEnergyzl(energyzl);
                } else {
                    meterDataDaoImpl.addEnergyzl(energyzl);
                }
            }
        } else {
            System.out.println("energyzl list is empty-----------");
        }
    }

    public void updateToDailyTable(List<MeterDailyData> updateDataList, String timeType) {
        System.out.println("start to update daily data----");
        if (updateDataList != null) {
            for (MeterDailyData meterData : updateDataList) {
                meterDataDaoImpl.updateMeterDailyData(meterData, timeType);
            }
        } else {
            System.out.println("daily meterDatas is null");
        }
    }
}
