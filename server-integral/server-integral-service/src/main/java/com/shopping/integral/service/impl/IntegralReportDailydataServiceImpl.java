package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.entity.SqlMap;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.CommUtils;
import com.shopping.integral.dao.IntegralReportDailydataDAO;
import com.shopping.integral.dao.IntegralReportGoodsDailydataDAO;
import com.shopping.integral.dao.model.IntegralReportDailydata;
import com.shopping.integral.service.IntegralReportDailydataService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralReportDailydataServiceImpl extends BaseServiceImpl<IntegralReportDailydata, Long> implements IntegralReportDailydataService {

    @Autowired
    IntegralReportDailydataDAO integralReportDailydataDAO;

    @Autowired
    IntegralReportGoodsDailydataDAO integralReportGoodsDailydataDAO;

    @Override
    public List<Map<String, Object>> queryBySqlReturnMap(Map<String, String> params, SqlMap sqlmap) throws Exception {
        String sql = "";
        String conding = "";
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        //查询条件
        Map param = new HashMap(16);
        switch (params.get("searchType")) {
            // 平均积分
            case "getAverageIntegral":
                param.put("app_key", params.get("app_key"));
                return this.integralReportDailydataDAO.queryByNativeSQL(sqlmap.getSql("getAverageIntegral"), param);
            // 单日用户订单数量
            case "getUserOrderCountByDay":
                param.put("ac_apply_id", params.get("app_id"));
                sql = sqlmap.getSql("getUserOrderCountByDay");
                log.info("SQL:" + sql);
                return this.integralReportDailydataDAO.queryByNativeSQL(sql, param);
            // 查询订单数量,订单消耗积分,订单使用金额
            case "getOrderReport":
                param.put("ac_apply_id", params.get("app_id"));
                if (CommUtils.isNotNull(params.get("goods_id"))) {
                    conding = " and og.goods_id= :goods_id ";
                    param.put("goods_id", params.get("goods_id"));
                }
                return this.integralReportDailydataDAO.queryByNativeSQL(sqlmap.getSql("getOrderReport") + conding, param);
            // 查询积分区间的用户
            case "getIntegralUser":
                param.put("app_key", params.get("app_key"));
                return this.integralReportDailydataDAO.queryByNativeSQL(sqlmap.getSql("getIntegralUser"), param);

            default:
                break;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> query(String isajax, Map map) throws Exception {
        // sql map
        SqlMap sqlmap = SqlMap.load("sqls/report_flowdata.xml");

        String conding = "";
        String con = "";
        String goods_conding = "";
        String sql = "";

        // xmlsql 的参数
        Map params_sqlMap = new HashedMap();

        // sql查询的参数
        Map params_sql = new HashedMap();

        if ("pv".equals(map.get("goods_type"))) {
            params_sqlMap.put("goods_order", "order by e.PV desc");
        } else {
            params_sqlMap.put("goods_order", "order by e.UV desc");
            //昨日
            if ("date".equals(map.get("goods_time_type"))) {
                goods_conding = " and DATEDIFF(NOW(),rg.report_date) =1 ";
                params_sqlMap.put("goods_conding", goods_conding);
            }//本周
            else if ("month".equals(map.get("goods_time_type"))) {
                goods_conding = " and YEARWEEK(date_format(rg.report_date,'%Y-%m-%d')) = YEARWEEK(now()) ";
                params_sqlMap.put("goods_conding", goods_conding);
            } else if ("year".equals(map.get("goods_time_type"))) {
                goods_conding = " and date_format(rg.report_date, '%Y%m') = date_format(curdate(),'%Y%m') ";
                params_sqlMap.put("goods_conding", goods_conding);
            }
        }
        if (CommUtils.isNotNull(map.get("beginTime"))) {
            conding = " and date_format(rd.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("beginTime") + "'" + ",'%Y-%m-%d') " +
                    "and date_format(rd.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("endTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("conding", conding);
        } else {
            params_sqlMap.put("conding", "");
        }
        if (CommUtils.isNotNull(map.get("beginTime"))) {
            con = " and date_format(rg.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("beginTime") + "'" + ",'%Y-%m-%d') " +
                    "and date_format(rg.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("endTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("con", con);
        } else {
            params_sqlMap.put("con", "");
        }
        if (CommUtils.isNotNull(map.get("goodsBeginTime")) && !"uv".equals(map.get("goods_type"))) {
            goods_conding = "and date_format(rg.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("goodsBeginTime") + "'" + ",'%Y-%m-%d') " +
                    "and date_format(rg.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("goodsEndTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("goods_conding", goods_conding);
        }

        //查询总流量
        if ("total".equals(isajax)) {
            params_sql.put("app_apply_id", map.get("appid"));
            sql = sqlmap.getSql("allFlowdata", params_sqlMap);
            List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
            return data;
        } else {//查询商品流量
            params_sql.put("app_apply_id", map.get("appid"));
            sql = sqlmap.getSql("allGoodsFlowdata", params_sqlMap);
            List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
            return data;
        }
    }

    @Override
    public List<Map<String, Object>> addUserQuery(Map map) throws Exception {
        String sql = "SELECT ifnull(sum(rd.add_user),0)as adduser,date_format(rd.report_date,'%m-%d') as data from integral_report_dailydata rd " +
                "where 1=1  " +
                "and date_format(rd.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("addUserBeginTime") + "'" + ",'%Y-%m-%d')  " +
                "and date_format(rd.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("addUserEndTime") + "'" + ",'%Y-%m-%d') " +
                "and rd.app_apply_id= " + map.get("appid") +
                " GROUP BY rd.report_date";
        List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        return data;
    }

    @Override
    public List<Map<String, Object>> visitorPerQuery(Map map) throws Exception {
        String sql = "SELECT ifnull(sum(rd.visitor_per),0)as userproportion,date_format(rd.report_date,'%m-%d') as data from integral_report_dailydata rd " +
                "where 1=1  " +
                "and date_format(rd.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("visitorPerBeginTime") + "'" + ",'%Y-%m-%d')  " +
                "and date_format(rd.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("visitorPerEndTime") + "'" + ",'%Y-%m-%d') " +
                "and rd.app_apply_id= " + map.get("appid") +
                " GROUP BY rd.report_date";
        List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        return data;
    }

    @Override
    public List<Map<String, Object>> averageIntegralQuery(Map map) throws Exception {
        String sql = "SELECT ifnull(sum(rd.average_integral),0)as preintegral,date_format(rd.report_date,'%m-%d') as data from integral_report_dailydata rd " +
                "where 1=1  " +
                "and date_format(rd.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("averageIntegralBeginTime") + "'" + ",'%Y-%m-%d')  " +
                "and date_format(rd.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("averageIntegralEndTime") + "'" + ",'%Y-%m-%d') " +
                "and rd.app_apply_id= " + map.get("appid") +
                " GROUP BY rd.report_date";
        List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        return data;
    }

    @Override
    public List<Map<String, Object>> disIntegralQuery(Map map) throws Exception {
        String sql = "SELECT rd.dis_integral ,date_format(rd.report_date,'%m-%d') as data from integral_report_dailydata rd " +
                "where 1=1  " +
                "and date_format(rd.report_date,'%Y-%m-%d') = date_format(" + "'" + map.get("disIntegralTime") + "'" + ",'%Y-%m-%d')  " +
                "and rd.app_apply_id= " + map.get("appid") +
                " GROUP BY rd.report_date";
        List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        return data;
    }

    @Override
    public List<Map<String, Object>> dailyDataQuery(Map map) throws Exception {
        String limint = "";
        if (CommUtils.isNotNull(map.get("day"))) {
            limint = "LIMIT " + map.get("day");
        }
        String sql = "SELECT c.h, " +
                "case when c.pv is null then 0 else c.pv end as pv, " +
                "case when c.uv is null then 0 else c.uv end as uv, " +
                "case when c.add_user is null then 0 else c.add_user end as add_user, " +
                "case when c.visitor_user is null then 0 else c.visitor_user end as visitor_user, " +
                "case when c.average_integral is null then 0 else c.average_integral end as average_integral, " +
                "case when c.order_count is null then 0 else c.order_count end as order_count, " +
                "case when c.used_integral is null then 0 else c.used_integral end as used_integral, " +
                "case when c.conversion_ratio is null then 0 else c.conversion_ratio end as conversion_ratio " +
                "from( " +
                "SELECT * from v_integral_monthuser a left join (  " +
                "SELECT ifnull(sum(rd.pv),0)as pv,ifnull(sum(rd.uv),0)as uv,ifnull(sum(rd.add_user),0)as add_user, " +
                "ifnull(sum(rd.visitor_user),0)as visitor_user,ifnull(sum(rd.average_integral),0)as average_integral, " +
                "ifnull(sum(rd.order_count),0)as order_count,ifnull(sum(rd.used_integral),0)as used_integral, " +
                "ifnull(sum(rd.conversion_ratio),0)as conversion_ratio,date_format(rd.report_date,'%d')as h1 " +
                "from integral_report_dailydata rd  " +
                "where 1=1 " +
                "and rd.app_apply_id = " + map.get("appid") +
                " and date_format(rd.report_date,'%Y%m') = date_format(" + "'" + map.get("dailyTime") + "'" + ",'%Y%m')" +
                " GROUP BY date_format(rd.report_date,'%Y-%m-%d') " +
                ")b on a.h = b.h1 " + limint + ")c order by c.h desc";
        List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        return data;
    }

    @Override
    public List<Map<String, Object>> activeDataQuery(Map map, boolean isAll) throws Exception {
        // sql map
        SqlMap sqlmap = SqlMap.load("sqls/report_activitydata.xml");

        String sql = "";
        String conding = "";
        String con = "";
        String time = "";
        // xmlsql 的参数
        Map params_sqlMap = new HashedMap();

        // sql查询的参数
        Map params_sql = new HashedMap();
        //订单数
        if ("showOrdernum".equals(map.get("active_type"))) {
            con = "sum(rg.order_count)as order_count";
            conding = "e.order_count";
            params_sqlMap.put("con", con);
            params_sqlMap.put("conding", conding);
            //消耗积分
        } else if ("showConsumptionIntegral".equals(map.get("active_type"))) {
            con = "sum(rg.used_integral)as used_integral";
            conding = "e.used_integral";
            params_sqlMap.put("con", con);
            params_sqlMap.put("conding", conding);
            //消耗金额
        } else if ("showConsumptionAmount".equals(map.get("active_type"))) {
            con = "sum(rg.used_money)as used_money";
            conding = "e.used_money";
            params_sqlMap.put("con", con);
            params_sqlMap.put("conding", conding);
            //订单转化率
        } else if ("showOrderConversionRate".equals(map.get("active_type"))) {
            con = "sum(rg.conversion_ratio)as conversion_ratio";
            conding = "e.conversion_ratio";
            params_sqlMap.put("con", con);
            params_sqlMap.put("conding", conding);
        }
        if ("showOrderConversionRate".equals(map.get("active_type"))) {
            time = " and date_format(rg.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("goodsBeginTime") + "'" + ",'%Y-%m-%d') " +
                    " and date_format(rg.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("goodsEndTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("time", time);
        } else {
            time = " and date_format(rg.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("beginTime") + "'" + ",'%Y-%m-%d') " +
                    " and date_format(rg.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("endTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("time", time);
        }
        //折线图数据
        if (isAll) {
            params_sql.put("app_apply_id", map.get("appid"));
            params_sql.put("beginTime", map.get("beginTime"));
            params_sql.put("endTime", map.get("endTime"));
            sql = sqlmap.getSql("allActivityLinedata", params_sqlMap);
            List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
            return data;
        } else {//饼状图数据
            params_sql.put("app_apply_id", map.get("appid"));
            sql = sqlmap.getSql("allActivityCakedata", params_sqlMap);
            List<Map<String, Object>> data = this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
            return data;
        }
    }

    @Override
    public List<Map<String, Object>> basicFactsDataQuery(Map<String, Object> params) throws Exception {
        // sql map
        SqlMap sqlmap = SqlMap.load("sqls/report.xml");

        // xmlsql 的参数
        Map params_sqlMap = new HashedMap();

        // sql查询的参数
        Map params_sql = new HashedMap();
        params_sql.put("app_apply_id", params.get("app_id"));
        String sql = "";
        if (params.get("searchType").equals("before_yestday")) {

            sql = sqlmap.getSql("getBasicFactsDataBeforeYestday", params_sqlMap);
            return this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
        } else if (params.get("searchType").equals("yestday")) {

            sql = sqlmap.getSql("getBasicFactsDataYestday", params_sqlMap);
            return this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
        } else if (params.get("searchType").equals("goods_remind")) {

            sql = sqlmap.getSql("getBasicFactsDataGoodsRemind", params_sqlMap);
            return this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
        } else if (params.get("searchType").equals("order_remind")) {

            sql = sqlmap.getSql("getBasicFactsDataOrderRemind", params_sqlMap);
            return this.integralReportDailydataDAO.queryByNativeSQL(sql, params_sql);
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> querySQLPage(Map map, boolean sortTypeValue, int currentPage, Object... params) throws Exception {
        // sql map
        SqlMap sqlmap = SqlMap.load("sqls/report.xml");

        String conding = "";
        String orderBy = "";
        // xmlsql 的参数
        Map params_sqlMap = new HashedMap();

        // sql查询的参数
        Map params_sql = new HashedMap();
        if (CommUtils.isNotNull(map.get("beginTime"))) {
            conding = " and date_format(rg.report_date,'%Y-%m-%d') >= date_format(" + "'" + map.get("beginTime") + "'" + ",'%Y-%m-%d') and date_format(rg.report_date,'%Y-%m-%d') <= date_format(" + "'" + map.get("endTime") + "'" + ",'%Y-%m-%d') ";
            params_sqlMap.put("conding", conding);
        }
        if (CommUtils.isNotNull(map.get("goodsName"))) {
            conding += " and rg.goods_name like '%" + map.get("goodsName") + "%' ";
        }
        params_sqlMap.put("conding", conding);
        if (CommUtils.isNotNull(map.get("sortType"))) {
            if (sortTypeValue) {
                if ("pv".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.PV desc";
                } else if ("uv".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.UV desc";
                } else if ("integral".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.consumptionIntegral desc";
                } else if ("money".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.consumptionAmount desc";
                } else if ("orderNum".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.numberOfOrdersCompleted desc";
                }
            } else {
                if ("pv".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.PV asc";
                } else if ("uv".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.UV asc";
                } else if ("integral".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.consumptionIntegral asc";
                } else if ("money".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.consumptionAmount asc";
                } else if ("orderNum".equals(map.get("sortType"))) {
                    orderBy = "ORDER BY a.numberOfOrdersCompleted asc";
                }
            }
        } else {
            orderBy = "ORDER BY a.report_date desc";
        }
        params_sqlMap.put("orderBy", orderBy);
        params_sqlMap.put("app_apply_id", "where rg.app_apply_id = " + map.get("app_apply_id"));
        //params_sql.put("app_apply_id",map.get("app_apply_id"));
        String sql = sqlmap.getSql("getContentGoodsData", params_sqlMap);
        if ("total".equals(map.get("type"))) {
            return this.integralReportDailydataDAO.queryByNativeSQL(sql, null);
        } else {
            return this.integralReportDailydataDAO.queryBySQLPage(sql, currentPage, 10, null);
        }
    }
}
