package com.shopping.integral.job.jobhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.base.entity.SqlMap;
import com.shopping.base.utils.CommUtils;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralReportDailydata;
import com.shopping.integral.dao.model.IntegralReportGoodsDailydata;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralReportGoodsDailydataService;
import com.shopping.integral.service.IntegralReportDailydataService;
import com.shopping.integral.service.VistDateService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 日报表统计
 * @author txl
 */
@Log4j2
@Component
public class ReportDailyJobHandler{

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    IntegralReportDailydataService integralReportDailydataService;

    @Autowired
    IIntegralReportGoodsDailydataService integralReportGoodsDailydataService;

	@Autowired
    VistDateService vistDateService;

	@XxlJob("reportDailyJobHandler")
	public ReturnT<String> execute(String param) throws Exception {
	    try{

            // sql map
            SqlMap sqlmap = SqlMap.load("sqls/report.xml");

	        Map map = new HashedMap();
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减1
            ca.add(Calendar.DATE, -1);
            //结果
            Date lastMonth = ca.getTime();
            String sql="SELECT acp.* from integral_account acc,integral_ac_apply acp where acp.ac_id = acc.id and acc.ac_status = 0";
	        List<IntegralAcApply> acs = this.integralAcApplyService.queryBySql(sql,null);
	        Map parges = new HashedMap();
	        for (IntegralAcApply ac : acs){

                //<editor-fold desc="应用日常数据">
                BigDecimal conversion_ratio = BigDecimal.ZERO;
                BigDecimal visitor_per = BigDecimal.ZERO;
                //人均积分
                BigDecimal average_integral = BigDecimal.ZERO;

                IntegralReportDailydata integral = new IntegralReportDailydata();
                IntegralAcApply integralAcApply = this.integralAcApplyService.getObjById(ac.getId());
                String appkey = integralAcApply.getAppKey();
                int uv = this.vistDateService.GET_VIST_UV(appkey);
                int pv = this.vistDateService.GET_VIST_PV(appkey);
                integral.setApp_apply(integralAcApply);
                integral.setReport_date(lastMonth);

                // 查询新增用户数和用户积分
                parges.put("app_key",ac.getAppKey());
                parges.put("app_id",ac.getId());
                parges.put("searchType","getAverageIntegral");
                List<Map<String,Object>> getAverageIntegral = this.integralReportDailydataService.queryBySqlReturnMap(parges,sqlmap);

                // 查询订单数量,订单消耗积分,订单使用金额
                parges.put("searchType","getOrderReport");
                List<Map<String,Object>> getOrderReport = this.integralReportDailydataService.queryBySqlReturnMap(parges,sqlmap);

                //查询积分区间的用户
                parges.put("searchType","getIntegralUser");
                List<Map<String,Object>> getIntegralUser = this.integralReportDailydataService.queryBySqlReturnMap(parges,sqlmap);

                //查询下单数 group by 用户
                parges.put("searchType","getUserOrderCountByDay");
                List<Map<String,Object>> count_user = this.integralReportDailydataService.queryBySqlReturnMap(parges,sqlmap);

                //访客数 ( 进来但是没有下单的人数 )  计算方式  UV - 订单数
                int visitor_user = uv;
                if(count_user!=null) {
                    visitor_user = uv - count_user.size();
                }
                if(uv != 0){
                    BigDecimal bd1 = new BigDecimal(visitor_user);
                    BigDecimal bd2 = new BigDecimal(CommUtils.null2String(uv));

                    if(bd2.compareTo(BigDecimal.ZERO) ==1 ) {
                        BigDecimal bd3 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP);
                        //访客占比
                        visitor_per = bd3.multiply(CommUtils.null2BigDecimal(100));
                    }else{
                        visitor_per = BigDecimal.ZERO;
                    }
                }

                if(uv != 0){
                    BigDecimal bd1 = new BigDecimal(getOrderReport.get(0).get("order_total").toString());
                    BigDecimal bd2 = new BigDecimal(CommUtils.null2String(uv));
                    BigDecimal bd3 = bd1.divide(bd2,2,BigDecimal.ROUND_HALF_UP);
                    //转化率
                    conversion_ratio = bd3.multiply(CommUtils.null2BigDecimal(100));
                }
                if(CommUtils.isNotNull(getAverageIntegral.get(0).get("average_integral"))){
                    average_integral = CommUtils.null2BigDecimal(getAverageIntegral.get(0).get("average_integral"));
                }
                for (int i = 0; i < getIntegralUser.size(); i++) {
                    String[] h = CommUtils.null2String(getIntegralUser.get(i).get("h")).split("-");
                    JSONObject dis_integral_obj = new JSONObject();
                    dis_integral_obj.put(CommUtils.null2String(getIntegralUser.get(i).get("h")),getIntegralUser.get(i).get("num"));
                    map.put(h[0],dis_integral_obj);
                }
                map = sortByKey(map);
                //遍历value 重组数据集合
                Iterator<Map.Entry<String, JSONObject>> it_value = map.entrySet().iterator();
                JSONArray jsons = new JSONArray();
                while (it_value.hasNext()) {
                    Map.Entry<String, JSONObject> entry = it_value.next();
                    JSONObject json = entry.getValue();
                    jsons.add(json);
                }
                //积分分布区间JSON数据
                integral.setDis_integral(JSON.toJSONString(jsons));
                integral.setPv(pv);
                integral.setUv(uv);
                //新增用户
                integral.setAdd_user(CommUtils.null2Int(getAverageIntegral.get(0).get("add_user")));
                //订单数
                integral.setOrder_count(CommUtils.null2Int(getOrderReport.get(0).get("order_total")));

                //消耗积分
                if(getOrderReport.get(0).get("integral_total")!=null) {
                    integral.setUsed_integral(Double.valueOf(getOrderReport.get(0).get("integral_total").toString()).intValue());
                }else{
                    integral.setUsed_integral(0);
                }
                //消耗金额
                integral.setUsed_money(CommUtils.null2BigDecimal(getOrderReport.get(0).get("price_total")));
                //转化率
                integral.setConversion_ratio(conversion_ratio);
                //人均积分
                integral.setAverage_integral(average_integral);
                //访客数
                integral.setVisitor_user(visitor_user);
                //访客占比计算方式  UV 减去 日下单用户数（group by user）= 访客数 ， 访客数 / UV = 访客占比
                integral.setVisitor_per(visitor_per);
                this.integralReportDailydataService.save(integral);
                //</editor-fold>

                //<editor-fold desc="应用商品日报表统计">
                Map<String,JSONObject> uv_map = this.vistDateService.GET_VIST_GOODS_UV(appkey);
                Map<String,JSONObject> pv_map = this.vistDateService.GET_VIST_GOODS_PV(appkey);

                if(CommUtils.isNotNull(uv_map)){
                    Iterator<Map.Entry<String, JSONObject>> it = uv_map.entrySet().iterator();
                    IntegralReportGoodsDailydata inre = null;
                    while (it.hasNext()) {
                        inre = new IntegralReportGoodsDailydata();
                        pv = 0;
                        uv = 0;
                        conversion_ratio = new BigDecimal(0);
                        Map.Entry<String, JSONObject> entry = it.next();
                        String goods_id = entry.getKey();
                        //查询订单数量,订单消耗积分,订单使用金额
                        parges.put("searchType","getOrderReport");
                        parges.put("goods_id",goods_id);
                        //查询商品订单数量和消耗金额,积分
                        getOrderReport = this.integralReportDailydataService.queryBySqlReturnMap(parges,sqlmap);

                        JSONObject obj = entry.getValue();
                        HashSet set = (HashSet)obj.get("uv");
                        uv = set != null ? set.size() : 0;
                        if(pv_map.get(goods_id)!=null) {
                            pv = CommUtils.null2Int(pv_map.get(goods_id).get("pv"));
                        }

                        inre.setApp_apply(integralAcApply);
                        inre.setUv(uv);
                        inre.setPv(pv);
                        inre.setGoods_id(CommUtils.null2Long(goods_id));
                        inre.setGoods_name(obj.get("goodsName").toString());
                        //消耗积分
                        if(getOrderReport.get(0).get("integral_total")!=null) {
                            inre.setUsed_integral(Double.valueOf(getOrderReport.get(0).get("integral_total").toString()).intValue());
                        }else{
                            inre.setUsed_integral(0);
                        }
                        //消耗金额
                        inre.setUsed_money(CommUtils.null2Int(getOrderReport.get(0).get("price_total")));
                        //下单次数
                        inre.setOrder_count(CommUtils.null2Int(getOrderReport.get(0).get("order_total")));
                        if(uv != 0){
                            BigDecimal bd1 = new BigDecimal(CommUtils.null2String(uv));
                            BigDecimal bd2 = BigDecimal.ZERO;
                            if(getOrderReport!=null) {
                                bd2 = CommUtils.null2BigDecimal(getOrderReport.get(0).get("order_total"));
                            }
                            //防止除数为0情况
                            if(bd1.compareTo(BigDecimal.ZERO) == 1) {
                                BigDecimal bd3 = bd2.divide(bd1, 2, BigDecimal.ROUND_HALF_UP);
                                //订单转化率,订单数量 / uv * 100
                                conversion_ratio = bd3.multiply(CommUtils.null2BigDecimal(100));
                            }else{
                                conversion_ratio = BigDecimal.ZERO;
                            }
                        }
                        //订单转化率
                        inre.setConversion_ratio(conversion_ratio);

                        inre.setReport_date(lastMonth);
                        this.integralReportGoodsDailydataService.save(inre);

                    }
                }
                //</editor-fold>

                // 清空redis数据
                this.vistDateService.clean_VSIT_DATA(appkey);
            }

        }catch (Exception e){
	        e.printStackTrace();
            log.error("日报表统计出错",e);
        }

		return ReturnT.SUCCESS;
	}

    public static Map<String, Object> sortByKey(Map<String, Object> map) {
        if(map==null||map.isEmpty()) {
            return null;
        }
        //创建一个TreeMap，传入一个自定义的比较器
        Map<String,Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        //把未排序的map全部添加到TreeMap中，产生有序的结构
        sortMap.putAll(map);
        return sortMap;
    }

}




//自定义的比较器
class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        //比较的是key，转换成整型后比较大小，
        Integer a1 = Integer.parseInt(s1);
        Integer a2 = Integer.parseInt(s2);
        //返回结果0表示相等，负数表示a1小于a2，正数表示a1大于a2，这样排序的结果是升序的
        return a1.compareTo(a2);
    }
}


