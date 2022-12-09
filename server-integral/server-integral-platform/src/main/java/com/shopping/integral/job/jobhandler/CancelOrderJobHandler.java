package com.shopping.integral.job.jobhandler;

import com.shopping.base.foundation.util.DateUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.dao.IntegralOrderDAO;
import com.shopping.integral.service.IIntegralOrderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类描述：CancelOrderJobHandler 订单支付超时取消订单
 *
 * @author：GuoFuJun
 * @date：2018年08月02日 17:38:24.
 */
@Component
@Log4j2
public class CancelOrderJobHandler {

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IntegralOrderDAO integralOrderDAO;

    @XxlJob("cancelOrderJobHandler")
    public ReturnT<String> execute(String param) throws Exception {
        Map params = new HashMap<>(2);
        params.put("order_state", OrderCons.WAIT_PAY);
        params.put("pay_lock", false);
        StringBuffer sql = new StringBuffer("SELECT a.oid,a.pay_time FROM integral_order a ");
        sql.append(" WHERE a.biz_id IS NOT NULL AND a.charge_id IS NULL");
        sql.append(" AND a.order_state = :order_state AND (a.pay_lock = :pay_lock or a.pay_lock is null)");
        /*sql.append("AND date_format(a.pay_time,'%Y-%m-%d %H:%i:%s') <= date_format(:curr_time,'%Y-%m-%d %H:%i:%s')");*/
        List<Map<String,Object>> orderList = integralOrderDAO.findBySqlPage(sql.toString(),-1,-1,params);
        if(orderList != null){
            log.info("支付超时数据:" + orderList.size());
        }
        orderList.forEach(item ->{
            Date payTime = CommUtils.formatDate(item.get("pay_time").toString(),"yyyy-MM-dd HH:mm:ss");
            Date curreTime = DateUtils.addMinutes(payTime, 30);
            log.info("支付时间:{},当前时间:{}",curreTime.getTime(),System.currentTimeMillis());
            if (curreTime.getTime() <= System.currentTimeMillis()) {
                log.info("订单号:{}",item.get("oid"));
                integralOrderService.cancelOrder(item.get("oid").toString(),"支付超时，自动取消订单",false);
            }
        });
        return ReturnT.SUCCESS;
    }
}
