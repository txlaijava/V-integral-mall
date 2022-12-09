package com.shopping.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.base.entity.SqlMap;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.queryform.IntegralAccountIncomeQueryForm;
import com.shopping.integral.service.*;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 类描述：报表Action
 *
 * @author：YJ
 * @date：2018年7月31日 上午11:13:46
 */
@Log4j2
@Api(value = "报表Action", tags = {"report"})
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IIntegralOrderGoodsService iIntegralOrderGoodsService;

    @Autowired
    IntegralReportDailydataService integralReportDailydataService;

    @Autowired
    VistDateService vistDateService;

    @Autowired
    IIntegralAccountIncomeService accountIncomeService;


    @ApiOperation(value = "内容数据", tags = {"contentData"}, notes = "")
    @RequestMapping("/contentData")
    public ActionResult<JSONArray> contentData(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String beginTime,String endTime,String goodsName,String currentPage,String sortType,boolean sortTypeValue) {
        try {
            JSONArray list = new JSONArray();
            currentPage = Utils.isEmpty(currentPage) || currentPage.equals("0") ? "1" : currentPage;
            Map pares = new HashedMap();
            pares.put("beginTime",beginTime);
            pares.put("app_apply_id",integralAcApply.getId());
            pares.put("endTime",endTime);
            pares.put("goodsName",goodsName);
            pares.put("sortType",sortType);

            List order_list = this.integralReportDailydataService.querySQLPage(pares, sortTypeValue,CommUtils.null2Int(currentPage),10,null);
            pares.put("type","total");
            List total = this.integralReportDailydataService.querySQLPage(pares,sortTypeValue,-1,-1, null);//总页数
            for (int i = 0; i < order_list.size(); i++) {
                Map map = (Map) order_list.get(i);
                JSONObject obj = new JSONObject();
                obj.put("commodityTitle",map.get("commodityTitle"));
                obj.put("consumptionIntegral",map.get("consumptionIntegral"));
                obj.put("numberOfOrdersCompleted",map.get("numberOfOrdersCompleted"));
                obj.put("consumptionAmount",map.get("consumptionAmount"));
                obj.put("UV",map.get("UV"));
                obj.put("PV",map.get("PV"));
                obj.put("total_pages",(int) Math.ceil((double) total.size() / 10));
                obj.put("currentPage",currentPage);
                list.add(obj);
            }
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("内容数据——商品明细异常",e);
        }
        return null;
    }

    @ApiOperation(value = "流量数据", tags = {"flowData"}, notes = "")
    @RequestMapping("/flowData")
    public ActionResult<Map> FlowDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String beginTime,String endTime,String goods_type,String goodsBeginTime,String goodsEndTime,String goods_time_type) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(beginTime)){
                map.put("beginTime",beginTime);
                map.put("endTime",endTime);
            }else{
                map.put("beginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("endTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            if(CommUtils.isNotNull(goodsBeginTime)){
                map.put("goodsBeginTime",goodsBeginTime);
                map.put("goodsEndTime",goodsEndTime);
            }
            else{
                map.put("goodsBeginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("goodsEndTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            map.put("goods_type",goods_type);
            map.put("goods_time_type",goods_time_type);


            //查询所有流量
            List<Map<String,Object>> data = this.integralReportDailydataService.query("total",map);
            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            list.put("fullFlow",data);

            //查询商品流量
            List<Map<String,Object>> data1 = this.integralReportDailydataService.query("",map);
            if(data1.size() == 0){
                Map map1 = new HashedMap();
                map1.put("goodsName","无数据");
                data1.add(0,map1);
            }
            list.put("goodsChart",data1);


            list.put("beginTime",map.get("beginTime"));
            list.put("endTime",map.get("endTime"));
            list.put("goodsBeginTime",map.get("goodsBeginTime"));
            list.put("goodsEndTime",map.get("goodsEndTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("流量数据异常",e);
        }
        return null;
    }

    @ApiOperation(value = "用户数据-新增用户", tags = {"addUserData"}, notes = "")
    @RequestMapping("/addUserData")
    public ActionResult<Map> addUserDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String addUserBeginTime,String addUserEndTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(addUserBeginTime)){
                map.put("addUserBeginTime",addUserBeginTime);
                map.put("addUserEndTime",addUserEndTime);
            }else{
                map.put("addUserBeginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("addUserEndTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询新增用户
            List<Map<String,Object>> data = this.integralReportDailydataService.addUserQuery(map);
            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            list.put("adduser",data);
            list.put("addUserBeginTime",map.get("addUserBeginTime"));
            list.put("addUserEndTime",map.get("addUserEndTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("用户数据-新增用户查询异常",e);
        }
        return null;
    }

    @ApiOperation(value = "用户数据-访客占比", tags = {"visitorPerData"}, notes = "")
    @RequestMapping("/visitorPerData")
    public ActionResult<Map> visitorPerDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String visitorPerBeginTime,String visitorPerEndTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(visitorPerBeginTime)){
                map.put("visitorPerBeginTime",visitorPerBeginTime);
                map.put("visitorPerEndTime",visitorPerEndTime);
            }else{
                map.put("visitorPerBeginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("visitorPerEndTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询访客占比
            List<Map<String,Object>> data = this.integralReportDailydataService.visitorPerQuery(map);
            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            list.put("userproportion",data);
            list.put("visitorPerBeginTime",map.get("visitorPerBeginTime"));
            list.put("visitorPerEndTime",map.get("visitorPerEndTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("用户数据-访客占比",e);
        }
        return null;
    }

    @ApiOperation(value = "用户数据-人均积分", tags = {"averageIntegralData"}, notes = "")
    @RequestMapping("/averageIntegralData")
    public ActionResult<Map> averageIntegralDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String averageIntegralBeginTime,String averageIntegralEndTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(averageIntegralBeginTime)){
                map.put("averageIntegralBeginTime",averageIntegralBeginTime);
                map.put("averageIntegralEndTime",averageIntegralEndTime);
            }else{
                map.put("averageIntegralBeginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("averageIntegralEndTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询访客占比
            List<Map<String,Object>> data = this.integralReportDailydataService.averageIntegralQuery(map);
            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            list.put("preintegral",data);
            list.put("averageIntegralBeginTime",map.get("averageIntegralBeginTime"));
            list.put("averageIntegralEndTime",map.get("averageIntegralEndTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("用户数据-访客占比",e);
        }
        return null;
    }

    @ApiOperation(value = "用户数据-积分分布", tags = {"disIntegralData"}, notes = "")
    @RequestMapping("/disIntegralData")
    public ActionResult<Map> disIntegralDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String disIntegralTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();

            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();

            if(CommUtils.isNotNull(disIntegralTime)){
                map.put("disIntegralTime",disIntegralTime);
            }else{
                map.put("disIntegralTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询访客占比
            List<Map<String,Object>> data = this.integralReportDailydataService.disIntegralQuery(map);
            if(data!=null && data.size() > 0){
                JSONArray usersection = (JSONArray) JSON.parseArray(data.get(0).get("dis_integral").toString());
                list.put("usersection",usersection);
            }

            list.put("disIntegralTime",map.get("disIntegralTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("用户数据-访客占比",e);
        }
        return null;
    }

    @ApiOperation(value = "日常数据", tags = {"dailyData"}, notes = "")
    @RequestMapping("/dailyData")
    public ActionResult<Map> dailyDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String dailyTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();

            map.put("appid",integralAcApply.getId());

            if(CommUtils.isNotNull(dailyTime)){
                map.put("dailyTime",dailyTime+"-01");
                map.put("yearMonth",dailyTime+"-");
            }else{
                map.put("dailyTime",CommUtils.formatDate(new Date(),"yyyy-MM-dd"));
                map.put("yearMonth",CommUtils.formatDate(new Date(),"yyyy-MM-"));
                map.put("day",CommUtils.formatDate(new Date(),"dd"));
            }
            //查询日常数据
            List<Map<String,Object>> data = this.integralReportDailydataService.dailyDataQuery(map);
            list.put("dailyData",data);
            list.put("yearMonth",map.get("yearMonth"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("日常数据",e);
        }
        return null;
    }

    @ApiOperation(value = "活跃数据", tags = {"activityDataList"}, notes = "")
    @RequestMapping("/activityDataList")
    public ActionResult<Map> activeDataController(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String beginTime,String endTime,String active_type,String goodsBeginTime,String goodsEndTime) {
        try {
            Map map = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            map.put("appid",integralAcApply.getId());
            map.put("active_type",active_type);
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(beginTime)){
                map.put("beginTime",beginTime);
                map.put("endTime",endTime);
            }else{
                map.put("beginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("endTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            if(CommUtils.isNotNull(goodsBeginTime)){
                map.put("goodsBeginTime",goodsBeginTime);
                map.put("goodsEndTime",goodsEndTime);
            }else{
                map.put("goodsBeginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("goodsEndTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询折线图数据
            List<Map<String,Object>> data = this.integralReportDailydataService.activeDataQuery(map,true);

            //查询饼状图数据
            List<Map<String,Object>> data1 = this.integralReportDailydataService.activeDataQuery(map,false);

            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            if(data1.size() == 0){
                Map map1 = new HashedMap();
                map1.put("goodsName","无数据");
                data1.add(0,map1);
            }
            list.put("lineData",data);
            list.put("cakeData",data1);
            list.put("beginTime",map.get("beginTime"));
            list.put("endTime",map.get("endTime"));
            list.put("goodsBeginTime",map.get("goodsBeginTime"));
            list.put("goodsEndTime",map.get("goodsEndTime"));
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("活跃数据",e);
        }
        return null;
    }

    @ApiOperation(value = "支付渠道占比", tags = {"channelRatio"}, notes = "")
    @RequestMapping("/channelRatio")
    public ActionResult<Map> channelRatio(@CurrentIntegralAcApply IntegralAcApply integralAcApply,String beginTime,String endTime) {
        try {
            Map map = new HashedMap();
            Map params_sqlMap = new HashedMap();
            Map<String,Object> list=new HashMap<>();
            SqlMap sqlmap = SqlMap.load("sqls/report_activitydata.xml");
            map.put("appid",integralAcApply.getId());
            //得到一个Calendar的实例
            Calendar ca = Calendar.getInstance();
            //设置时间为当前时间
            ca.setTime(new Date());
            //天数减一
            ca.add(Calendar.DATE, -1);
            //结果
            Date endDate = ca.getTime();
            ca.add(Calendar.DATE, -31);
            //结果
            Date beginDate = ca.getTime();

            if(CommUtils.isNotNull(beginTime)){
                map.put("beginTime",beginTime);
                map.put("endTime",endTime);
            }else{
                map.put("beginTime",CommUtils.formatDate(beginDate,"yyyy-MM-dd"));
                map.put("endTime",CommUtils.formatDate(endDate,"yyyy-MM-dd"));
            }
            //查询饼状图数据
            List<Map<String,Object>> data = this.integralReportDailydataService.queryByNativeSQL(sqlmap.getSql("channelRatioData",params_sqlMap),map);

            if(data.size() == 0){
                Map map1 = new HashedMap();
                map1.put("data","无数据");
                data.add(0,map1);
            }
            list.put("data",data);
            return ActionResult.ok(list);
        } catch (Exception e) {
            log.error("活跃数据",e);
        }
        return null;
    }

    @ApiOperation(value = "首页数据", tags = {"basicFactsData"}, notes = "")
    @RequestMapping("/basicFactsData")
    public ActionResult<Map> basicFactsData(@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try {
            Map parges = new HashedMap();
            Map<String,Object> map = new HashMap<>();


            parges.put("app_key",integralAcApply.getAppKey());
            parges.put("app_id",integralAcApply.getId());

            //昨日数据
            parges.put("searchType","yestday");
            List<Map<String,Object>> yestday = this.integralReportDailydataService.basicFactsDataQuery(parges);

            if(yestday!=null && yestday.size()>0) {
                map.put("uv_day", yestday.get(0).get("uv"));
                map.put("pv_day", yestday.get(0).get("pv"));
                //昨天新增用户数
                map.put("add_user_day", yestday.get(0).get("add_user"));
                //昨天订单数
                map.put("order_total_day", yestday.get(0).get("order_count"));
                //昨天消耗积分
                map.put("integral_total_day", yestday.get(0).get("used_integral"));
                //昨天消耗金额
                map.put("price_total_day", yestday.get(0).get("used_money"));
                //昨天订单转化率
                map.put("conversion_ratio_day", yestday.get(0).get("conversion_ratio"));
                //昨天数据 end
            }else {
                map.put("uv_day", 0);
                map.put("pv_day", 0);
                //昨天新增用户数
                map.put("add_user_day", 0);
                //昨天订单数
                map.put("order_total_day", 0);
                //昨天消耗积分
                map.put("integral_total_day", 0);
                //昨天消耗金额
                map.put("price_total_day", 0);
                //昨天订单转化率
                map.put("conversion_ratio_day", 0);
            }


            //前日数据
            parges.put("searchType","before_yestday");
            List<Map<String,Object>> before_yestday = this.integralReportDailydataService.basicFactsDataQuery(parges);
            if(before_yestday!=null && before_yestday.size()>0) {
                map.put("uv", before_yestday.get(0).get("uv"));
                map.put("pv", before_yestday.get(0).get("pv"));
                //前天新增用户数
                map.put("add_user", before_yestday.get(0).get("add_user"));
                //前天订单数
                map.put("order_total", before_yestday.get(0).get("order_count"));
                //前天消耗积分
                map.put("integral_total", before_yestday.get(0).get("used_integral"));
                //前天消耗金额
                map.put("price_total", before_yestday.get(0).get("used_money"));
                //前天订单转化率
                map.put("conversion_ratio", before_yestday.get(0).get("conversion_ratio"));
                //前日数据end
            }else {
                map.put("uv", 0);
                map.put("pv",0);
                //前天新增用户数
                map.put("add_user", 0);
                //前天订单数
                map.put("order_total",0);
                //前天消耗积分
                map.put("integral_total",0);
                //前天消耗金额
                map.put("price_total", 0);
                //前天订单转化率
                map.put("conversion_ratio", 0);
            }
            //商品提醒
            parges.put("searchType","goods_remind");
            List<Map<String,Object>> goods_remind = this.integralReportDailydataService.basicFactsDataQuery(parges);
            if(goods_remind!=null && goods_remind.size()>0) {
                //售完商品
                map.put("sell_out_goods", goods_remind.get(0).get("sell_out_goods"));
                //新增商品
                map.put("add_goods", goods_remind.get(0).get("add_goods"));
                //缺货商品
                map.put("laca_goods", goods_remind.get(0).get("laca_goods"));
            }else{
                //售完商品
                map.put("sell_out_goods", 0);
                //新增商品
                map.put("add_goods", 0);
                //缺货商品
                map.put("laca_goods", 0);
            }
            //商品提醒 end

            //订单提醒
            parges.put("searchType","order_remind");
            List<Map<String,Object>> order_remind = this.integralReportDailydataService.basicFactsDataQuery(parges);
            if(order_remind!=null && order_remind.size()>0) {
                //待发货订单
                map.put("delivery_order", order_remind.get(0).get("delivery_order"));
                //待审核订单
                map.put("audited_order", order_remind.get(0).get("audited_order"));
            }else {
                //待发货订单
                map.put("delivery_order", 0);
                //待审核订单
                map.put("audited_order", 0);
            }
            //订单提醒 end
            return ActionResult.ok(map);
        } catch (Exception e) {
            log.error("首页数据查询异常",e);
        }
        return null;
    }

    @ApiOperation(value = "获取收入明细记录", tags = {"income"}, notes = "")
    @GetMapping(value = "/get_income_list")
    public ActionResult<BaseResult> getGoodsList(@CurrentIntegralAcApply IntegralAcApply integralAcApply, IntegralAccountIncomeQueryForm incomeQueryForm,String beginTime,String endTime) {
        if (Utils.isNotEmpty(integralAcApply)) {

            incomeQueryForm.setAcId(integralAcApply.getAc().getId());

            if(CommUtils.isNotNull(beginTime)){
                incomeQueryForm.setBeginAddTime(CommUtils.formatDate(beginTime,"yyyy-MM-dd"));
            }
            if(CommUtils.isNotNull(endTime)){
                incomeQueryForm.setEndAddTime(CommUtils.formatDate(endTime,"yyyy-MM-dd"));
            }

            BaseResult baseResult = this.accountIncomeService.getIncomeList(incomeQueryForm);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }
}
