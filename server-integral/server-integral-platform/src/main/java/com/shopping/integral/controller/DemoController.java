package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.util.DateUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.dao.IntegralOrderDAO;
import com.shopping.integral.service.IIntegralOrderService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：测试
 */
@Log4j2
@Api(value = "公交操作", tags = {"common"})
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IntegralOrderDAO integralOrderDAO;


    @PostMapping(value = "/testCancel")
    public ActionResult testCancel() {
        try{
            Map params = new HashMap<>(2);
            params.put("order_state", OrderCons.WAIT_PAY);
            params.put("pay_lock", false);
            StringBuffer sql = new StringBuffer("SELECT a.oid,a.pay_time FROM integral_order a ");
            sql.append(" WHERE a.biz_id IS NOT NULL AND a.charge_id IS NULL");
            sql.append(" AND a.order_state = :order_state AND (a.pay_lock = :pay_lock or a.pay_lock is null)");
            /*sql.append("AND date_format(a.pay_time,'%Y-%m-%d %H:%i:%s') <= date_format(:curr_time,'%Y-%m-%d %H:%i:%s')");*/
            List<Map<String,Object>> orderList = integralOrderDAO.findBySqlPage(sql.toString(), -1, -1, params);
            if(orderList != null){
                log.info("支付超时数据:" + orderList.size());
            }
            orderList.forEach(item ->{
                Date payTime = CommUtils.formatDate(item.get("pay_time").toString(),"yyyy-MM-dd HH:mm:ss");
                Date curreTime = DateUtils.addMinutes(payTime, 30);
                log.info("支付时间:{},当前时间:{}",curreTime.getTime(),System.currentTimeMillis());
                if (curreTime.getTime() <= System.currentTimeMillis()) {
                    log.info("订单号:{}",item.get("oid"));
                    integralOrderService.cancelOrder(item.get("oid").toString(),"支付超时，自动取消订单",true);
                }
            });

        }catch (Exception e){
            log.error("上传图片异常",e);
        }
        return ActionResult.error("服务器异常");
    }
}
