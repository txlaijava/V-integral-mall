package com.shopping.integral.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.base.utils.excel.ExportExcel;
import com.shopping.framework.context.SpringContextUtil;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.GoodsCons;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.convert.IntegralGoodsConvert;
import com.shopping.integral.dao.IntegralAcApplyDAO;
import com.shopping.integral.dao.IntegralOrderDAO;
import com.shopping.integral.dao.model.*;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.queryform.IntegralOrderAddForm;
import com.shopping.integral.queryform.IntegralOrderQueryForm;
import com.shopping.integral.sdk.entity.CreditConsumeParams;
import com.shopping.integral.sdk.entity.CreditNotifyParams;
import com.shopping.integral.sdk.entity.VirtualParams;
import com.shopping.integral.service.*;
import com.shopping.integral.util.BigDecimalUtils;
import com.shopping.integral.util.UniqueIDentifiUtil;
import com.shopping.integral.view.IntegralOrderView;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类描述：IntegralOrderService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralOrderServiceImpl extends BaseServiceImpl<IntegralOrder, Long> implements IIntegralOrderService {

    @Autowired
    IntegralOrderDAO integralOrderDAO;

    @Autowired
    IIntegralGoodsService integralGoodsService;

    @Autowired
    IntegralAcApplyDAO integralAcApplyDAO;

    @Autowired
    IIntegralUserService integralUserService;

    @Autowired
    IIntegralOrderGoodsService integralOrderGoodsService;

    @Autowired
    IIntegralExchangeRulesService integralExchangeRulesService;

    @Autowired
    IIntegralAddressService integralAddressService;

    @Autowired
    IHookService hookService;

    @Override
    public BaseResult setIntegralOrder(Long applyId, Long userId, IntegralOrderAddForm integralOrderAddForm) {
        try {
            IntegralOrder setOrder = new IntegralOrder();
            // 校验订单信息
            BaseResult verify = paramsVerify(applyId, userId, integralOrderAddForm, setOrder);
            if (!verify.getSuccess()) {
                return verify;
            }
            // <editor-fold desc="验证收货地址">
            IntegralAddress integralAddress = new IntegralAddress();
            if (integralOrderAddForm.getGearCode() == null || integralOrderAddForm.getGearCode() == "") {
                if (OrderCons.SEND_TYPE_SEND == integralOrderAddForm.getOrderStateType() && Utils.isEmpty(integralOrderAddForm.getAddressId())) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ADDRESS_ID_NULL_ERROR);
                }
                if (OrderCons.SEND_TYPE_SEND == integralOrderAddForm.getOrderStateType()) {
                    integralAddress = integralAddressService.getObjById(integralOrderAddForm.getAddressId());
                } else {
                    if (Utils.isNotEmpty(integralOrderAddForm.getAddressId())) {
                        integralAddress = integralAddressService.getObjById(integralOrderAddForm.getAddressId());
                    } else {
                        integralAddress = new IntegralAddress();
                    }
                }
            }
            // </editor-fold>
            setOrder.setOrderGoodsCount(integralOrderAddForm.getOrderGoodsCount());
            setOrder.setLeaveMessage(integralOrderAddForm.getLeaveMessage());
            setOrder.setOrderStateType(integralOrderAddForm.getOrderStateType());
            setOrder.setChannel(integralOrderAddForm.getChannel());
            // 调用保存订单接口
            BaseResult result = saveIntegralOrder(setOrder, integralAddress, integralOrderAddForm.getGearCode());
            return result;
        } catch (Exception e) {
            log.error("生成订单异常：" + e);
            e.printStackTrace();
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult getIntegralOrderDetails(Long userId, String oid, Long applyId) {
        IntegralOrder integralOrder = super.getOneObjByProperty("oid", oid);
        if (integralOrder != null) {
            IntegralOrderView integralOrderView = BeanViewUtils.getView(integralOrder, IntegralOrderView.class);
            //验证本人订单
            if (integralOrderView.getOrderUserId().equals(userId)) {
                return BaseResult.SUCCESS("成功！", integralOrderView);
            } else {
                return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
            }
        } else {
            return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
        }
    }

    @Override
    public BaseResult getIntegralOrderDetailsByVerifyCode(String verifyCode, Long applyId) {
        IntegralOrder integralOrder = super.getOneObjByProperty("verifyCode", verifyCode);
        if (integralOrder != null) {
            IntegralOrderView integralOrderView = BeanViewUtils.getView(integralOrder, IntegralOrderView.class);
            if (integralOrder.getAcApply().getId().equals(applyId)) {
                return BaseResult.SUCCESS("成功！", integralOrderView);
            } else {
                return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
            }
        } else {
            return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
        }
    }

    @Override
    public BaseResult getIntegralOrderList(IntegralOrderQueryForm integralOrderQueryForm) {
        PaginationResult<IntegralOrder> item = integralOrderDAO.paging(integralOrderQueryForm);
        return BaseResult.SUCCESS("获取成功!", item.getView(IntegralOrderView.class));
    }

    @Override
    public void ExOrderList(IntegralOrderQueryForm integralOrderQueryForm) {
        PaginationResult<IntegralOrder> item = integralOrderDAO.paging(integralOrderQueryForm);
        List list = this.exportOrder(item.getData());
        String[] headers = new String[]{"用户ID","devUserId","支付渠道", "channel","下单时间", "addTime", "订单号", "oid", "商品名称", "goodsName", "价格", "price", "成本", "cost", "订单状态", "orderStatus", "开发者备注", "integralUserRemark"};
        int[] width = new int[]{25, 10, 18, 18, 18, 18};
        String fileName = "积分商城订单统计";
        HttpServletResponse response = SpringContextUtil.getResponse();
        ExportExcel.exportExcel(response, fileName, headers, list, width);
    }

    List exportOrder(List data) {
        List<IntegralOrder> list = data;
        List arrayList = new ArrayList();
        for (IntegralOrder integralOrder : list) {
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("devUserId", integralOrder.getDevUserId());
            maps.put("integralUserRemark", Utils.isEmpty(integralOrder.getIntegralUserRemark()) ? "暂无" : integralOrder.getIntegralUserRemark());

            maps.put("addTime", integralOrder.getAddTime());
            maps.put("oid", integralOrder.getOid());
            maps.put("goodsName", integralOrder.getOrderGoods().getOrderGoodsName());

            //价格
            if (CommUtils.null2Int(integralOrder.getOrderGoods().getOrderGoodsPrice()) > 0) {
                maps.put("price", integralOrder.getOrderGoods().getOrderGoodsIntegralPrice() + " 积分" + integralOrder.getOrderGoods().getOrderGoodsPrice() + " 元");
            } else {
                maps.put("price", integralOrder.getOrderGoods().getOrderGoodsIntegralPrice() + " 积分");
            }

            //成本
            if (integralOrder.getOrderGoods().getGoods().getExchangeRules().getGoodsPriceUnit() == 0) {
                maps.put("cost", integralOrder.getOrderGoods().getGoods().getExchangeRules().getGoodsPrice() + " 元");
            } else {
                maps.put("cost", integralOrder.getOrderGoods().getGoods().getExchangeRules().getGoodsPrice() + " 积分");
            }
            String orderStatus = "";
            switch (integralOrder.getOrderState()) {
                case 10:
                    orderStatus = "已取消";
                    break;
                case 20:
                    orderStatus = "待审核";
                    break;
                case 30:
                    orderStatus = "待发货";
                    break;
                case 40:
                    orderStatus = "已发货";
                    break;
                case 50:
                    orderStatus = "已完成";
                    break;
                case 60:
                    orderStatus = "兑换失败";
                    break;
                case 70:
                    orderStatus = "订单异常";
                    break;
                default:
            }
            maps.put("orderStatus", orderStatus);
            if(Utils.isEmpty(integralOrder.getChannel())){
                maps.put("channel", "未知");
            }else{
                switch (integralOrder.getChannel()){
                    case "WXAPP":
                        maps.put("channel", "小程序");
                        break;
                    case "APP":
                        maps.put("channel", "APP");
                        break;
                    case "WX":
                        maps.put("channel", "微信H5");
                        break;
                }
            }

            arrayList.add(maps);
        }
        return arrayList;
    }

    @Override
    public BaseResult createOrderVerifyCode(String oid) throws Exception {
        try {
            IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
            String verifyCode = UniqueIDentifiUtil.getVerifyCode();
            integralOrder.setVerifyCode(verifyCode);
            integralOrder.setVerifyStatus(false);
            super.update(integralOrder);
            return BaseResult.SUCCESS("生成核销码成功！", verifyCode);
        } catch (Exception e) {
            log.error("生成核销码异常！", e);
            throw e;
        }
    }

    @Override
    public BaseResult getByGoodsIdOrderCount(String startDate, String endDate, Long goodsId, Long userId) throws Exception {

        if (Utils.isEmpty(goodsId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_GOODS_ID_NULL_ERROR);
        }
        if (Utils.isEmpty(userId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_USER_ID_NULL_ERROR);
        }

        Map params = new HashMap(1);
        StringBuffer sql = new StringBuffer("SELECT count(*) AS singular FROM integral_order a LEFT JOIN integral_order_goods b ON a.oid = b.order_oid ");
        sql.append(" WHERE a.order_state NOT IN(" + OrderCons.WAIT_PAY + " , " + OrderCons.FAILURE + ") ");
        sql.append(" AND a.pay_time IS NOT NULL AND b.goods_id = " + goodsId + " AND a.order_user_id = " + userId);
        if (Utils.isNotEmpty(startDate) && Utils.isEmpty(endDate)) {
            sql.append(" AND DATE_FORMAT(a.pay_time , '%Y-%m-%d') = DATE_FORMAT(:startDate , '%Y-%m-%d') ");
            params.put("startDate", startDate);
        }

        if (Utils.isNotEmpty(startDate) && Utils.isNotEmpty(endDate)) {
            sql.append(" AND DATE_FORMAT(a.pay_time , '%Y-%m-%d') >= DATE_FORMAT(:startDate , '%Y-%m-%d') ");
            sql.append(" AND DATE_FORMAT(a.pay_time , '%Y-%m-%d') <= DATE_FORMAT(:endDate , '%Y-%m-%d') ");
            params.put("startDate", startDate);
            params.put("endDate", endDate);
        }

        List<Map<String, Object>> mapList = integralOrderDAO.queryByNativeSQL(sql.toString(), params);
        int singular = 0;
        if (Utils.isNotEmpty(mapList)) {
            Map obj = mapList.get(0);
            singular = CommUtils.null2Int(obj.get("singular"));
        }
        return BaseResult.SUCCESS("获取成功！", singular);
    }

    @Override
    public BaseResult dispostDepartmentSuccess(String chargeId, BigDecimal amount, String oid) throws Exception {
        IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
        //可能没有这个订单
        if (integralOrder == null) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_HOOKS_ORDER_ERROR);
        }
        BigDecimal totalPrice = integralOrder.getOrderTotalPrice().setScale(2, 4);
        Boolean compare = amount.compareTo(totalPrice) == 0 ? true : false;
        // 验证支付金额和订单需要支付的金额是否一致
        if (compare) {
            integralOrder.setChargeId(chargeId);
            integralOrder.setOrderState(OrderCons.WAIT_SEND);
            integralOrder.setPayTime(new Date());
            // 当扣除积分成功时，如果订单为上门自提，则生成核销码
            if (OrderCons.SEND_TYPE_RECEIVE == integralOrder.getOrderStateType()) {
                createOrderVerifyCode(integralOrder.getOid());
            }
            // 订单处理成功 减去库存
            BaseResult baseResult = integralGoodsService.reduceGoodsInventory(integralOrder.getAcApply().getId(),
                    integralOrder.getOrderGoods().getGoods().getId(), integralOrder.getOrderGoodsCount());
            log.info("调用修改库存接口：" + (baseResult.getSuccess() ? "成功" : "失败") +
                    (baseResult.getSuccess() ? "" : "错误信息：" + baseResult.getMessage()));
            // 异步任务，通知开发平台
            asynHookDeduction(integralOrder.getAcApply().getAppKey(), integralOrder, true);
        } else {
            log.error("订单号：" + integralOrder.getOid() + ",支付金额与订单金额不符,支付金额:" + amount + ";订单金额:" + integralOrder.getOrderTotalPrice());
            integralOrder.setOrderState(OrderCons.EXCEPTION);
            // 异步任务，通知开发平台
            asynHookDeduction(integralOrder.getAcApply().getAppKey(), integralOrder, false);
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_HOOKS_AMOUNT_ERROR);
        }
        integralOrderDAO.update(integralOrder);
        return BaseResult.SUCCESS("支付回调处理订单完成！", integralOrder.getOid());
    }

    @Override
    public BaseResult cancelOrder(String oid, String cancelReason, Boolean isUserCancel) {
        try {
            IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
            if (Utils.isEmpty(integralOrder)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
            }
            if (integralOrder.getPayLock() != null && integralOrder.getPayLock() && isUserCancel) {
                return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_PAY_LOCK_STATE);
            }
            // 如果是用户端发起的取消的订单状态不为待付款，或者待审核状态的话则不能进行取消
            boolean cancelState = integralOrder.getOrderState() == OrderCons.WAIT_PAY || integralOrder.getOrderState() == OrderCons.WAIT_AUDIT;
            if (isUserCancel && !cancelState) {
                return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_CANCEL_STATE);
            }
            integralOrder.setCancelReason(cancelReason);
            integralOrder.setOrderState(OrderCons.CANCEL);
            // 如果订单已有开发者平台编号，则需要进行通知
            if (Utils.isNotEmpty(integralOrder.getBizId())) {
                asynHookDeduction(integralOrder.getAcApply().getAppKey(), integralOrder, false);
            }
            integralOrderDAO.update(integralOrder);
        } catch (Exception e) {
            log.error("取消订单异常！", e);
        }
        return BaseResult.SUCCESS("操作成功", "");
    }

    @Override
    public BaseResult verifyOrder(String oid) {
        try {
            IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
            if (Utils.isEmpty(integralOrder)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
            }
            // 如果订单状态不为待发货则不能进行核销
            if (integralOrder.getOrderState() != OrderCons.WAIT_SEND) {
                return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_VERIFY_STATE);
            }
            integralOrder.setOrderState(OrderCons.ALREADY_SEND);
            // 如果订单已有开发者平台编号，则需要进行通知
            /*if (Utils.isNotEmpty(integralOrder.getBizId())) {
                asynHookDeduction(integralOrder.getAcApply().getAppKey(), integralOrder, false);
            }*/
            integralOrderDAO.update(integralOrder);
            return BaseResult.SUCCESS("核销成功!", "");
        } catch (Exception e) {
            log.error("取消订单异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult auditOrder(String oid, String status) {
        try {
            IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
            if (Utils.isEmpty(integralOrder)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
            }
            // 如果订单状态不为待审核则不能进行审核
            if (integralOrder.getOrderState() != OrderCons.WAIT_AUDIT) {
                return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_AUDIT_STATE);
            }
            //审核通过
            if (status.equals("agree")) {
                integralOrder.setOrderState(OrderCons.WAIT_SEND);
            } else {
                integralOrder.setOrderState(OrderCons.REJECT);
                // 如果订单已有开发者平台编号，则需要进行通知
                if (Utils.isNotEmpty(integralOrder.getBizId())) {
                    asynHookDeduction(integralOrder.getAcApply().getAppKey(), integralOrder, false);
                }
            }

            integralOrderDAO.update(integralOrder);
            return BaseResult.SUCCESS("成功!", "");
        } catch (Exception e) {
            log.error("审核订单异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public void setOrderPayLock(String oid, Boolean lock) {
        IntegralOrder integralOrder = integralOrderDAO.findByPropertyOne("oid", oid);
        if (Utils.isNotEmpty(integralOrder)) {
            integralOrder.setPayLock(lock);
            integralOrderDAO.update(integralOrder);
        }
    }

    // ======================================= 私有方法 =======================================

    /**
     * 保存订单
     *
     * @param setOrder        设置的一些订单信息
     * @param integralAddress 用户的收货地址
     * @param gearCode        用户选择的栏目编号
     * @return
     * @throws Exception
     */
    BaseResult saveIntegralOrder(IntegralOrder setOrder, IntegralAddress integralAddress, String gearCode) throws Exception {
        // 获取订单商品
        IntegralGoods goods = setOrder.getOrderGoods().getGoods();
        // 获取订单商品兑换规则
        IntegralExchangeRules integralExchangeRules = goods.getExchangeRules();
        this.integralExchangeRulesService.detachObj(integralExchangeRules);

        // 选择的栏位
        Map gear = null;
        if (GoodsCons.VIRTUAL_GOODS == goods.getGoodsType()) {
            if (GoodsCons.GEAR_SINGLE.equals(goods.getGearType())) {
                gear = new HashMap(1);
                gear.put("gearVal", goods.getGearValue());
            } else {
                if (Utils.isEmpty(gearCode)) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.PARM_GEAR_CODE_NULL_ERROR);
                }
                JSONArray gearList = JSONArray.parseArray(goods.getGearValue());
                Iterator<Object> it = gearList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> obj = (Map<String, Object>) it.next();
                    if (CommUtils.null2String(obj.get("gearVal")).equalsIgnoreCase(gearCode)) {
                        gear = obj;
                        integralExchangeRules.setExchangeIntegralPrice(new BigDecimal(gear.get("gearPrice").toString()));
                        goods.setGoodsName(goods.getGoodsName() + " [" + (gear.get("gearTitle").toString()) + "] ");
                    }
                }
                if (Utils.isEmpty(gear)) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.PARM_GEAR_NULL_ERROR);
                }
            }
        }
        String oid = goods.getGoodsType() + UniqueIDentifiUtil.getRandomid();

        BigDecimal integralPrice = integralExchangeRules.getExchangeIntegralPrice();
        BigDecimal exchangePrice = integralExchangeRules.getExchangePrice();
        BigDecimal freightPrice = integralExchangeRules.getFreightPrice();
        IntegralAcApply acApply = goods.getAcApply();

        IntegralUser integralUser = integralUserService.getObjById(setOrder.getOrderUserId());
        IntegralOrder order = new IntegralOrder(oid, OrderCons.WAIT_PAY, integralPrice, exchangePrice,
                setOrder.getOrderGoodsCount(), setOrder.getOrderUserId(), setOrder.getDevUserId(), freightPrice, setOrder.getLeaveMessage(),
                acApply, integralAddress, setOrder.getOrderStateType(), setOrder.getChannel(), integralUser.getRemark());
        super.save(order);
        BaseResult baseResult = integralOrderGoodsService.setOrderGoods(order, goods, "");
        if (baseResult.getSuccess()) {
            // 这个地方，会出现 OrderGoods 保存成功后，Order 里面获取不到所以根据订单重新获取一遍
            if (Utils.isEmpty(order.getOrderGoods())) {
                IntegralOrderGoods integralOrderGoods = integralOrderGoodsService.getOneObjByProperty("order.oid", order.getOid());
                order.setOrderGoods(integralOrderGoods);
            }
            // isMoneyPay 是否需要现金支付
            Boolean isMoneyPay = BigDecimalUtils.compareToZero(order.getOrderTotalPrice()) || BigDecimalUtils.compareToZero(order.getLogisticsPrice());
            // 向开发者平台发送扣除积分请求
            baseResult = callDeductionPost(goods.getAcApply().getAppKey(), order, isMoneyPay, gear);
            if (isMoneyPay && baseResult.getSuccess()) {
                baseResult = BaseResult.SUCCESS("积分支付成功,现金待付款", order.getOid());
            } else if (baseResult.getSuccess()) {
                baseResult = BaseResult.SUCCESS("支付成功", order.getOid());
            }
        }
        return baseResult;
    }

    /**
     * 请求开发者平台
     *
     * @param appKey        应用Key
     * @param integralOrder 订单
     * @param isMoneyPay    是否需要现金支付
     * @param gear          虚拟商品（栏位值）
     * @return
     * @throws Exception
     */
    BaseResult callDeductionPost(String appKey, IntegralOrder integralOrder, Boolean isMoneyPay, Map gear) throws Exception {
        try {
            IntegralGoods goods = integralOrder.getOrderGoods().getGoods();
            CreditConsumeParams params = new CreditConsumeParams(appKey, integralOrder.getDevUserId(), integralOrder.getOrderTotalIntegralPrice().longValue(),
                    IntegralGoodsConvert.goodsTypeCodeConvert(goods.getGoodsType()), integralOrder.getOid(), goods.getGoodsName());
            BaseResult result = hookService.integralDeductionPost(params);
            log.info("调用开发者平台状态：" + result.getMessage());
            // 判断商品是否为多栏位
            if (result.getSuccess() && GoodsCons.VIRTUAL_GOODS == goods.getGoodsType()) {
                VirtualParams virtualParams = new VirtualParams(appKey, new Date(), integralOrder.getOrderTotalIntegralPrice().longValue(),
                        integralOrder.getDevUserId(), goods.getGoodsName(), integralOrder.getOid(), gear.get("gearVal").toString());
                result = hookService.integralCouponPost(virtualParams);
                if (!result.getSuccess()) {
                    integralOrder.setOrderState(OrderCons.FAILURE);
                }
            }
            // 判断开发者平台是否调用成功
            if (result.getSuccess()) {
                // <editor-fold desc="获取开发者平台返回的数据">
                JSONObject refund = (JSONObject) result.getData();
                // 开发平台订单号
                String bizId = GoodsCons.VIRTUAL_GOODS == goods.getGoodsType() ?
                        CommUtils.null2String(refund.get("supplierBizId")) : CommUtils.null2String(refund.get("bizId"));
                // 需要更新最新的积分数
                Long credits = CommUtils.null2Long(refund.get("credits"));
                // 更新用户剩余积分
                integralUserService.updateUserCredits(integralOrder.getOrderUserId(), credits);
                // 订单不需要现金支付并且商品不要审核标记待发货
                if (!isMoneyPay && !goods.getAuditStatus()) {
                    integralOrder.setOrderState(GoodsCons.VIRTUAL_GOODS == goods.getGoodsType() ? OrderCons.OVER : OrderCons.WAIT_SEND);
                } else if (!isMoneyPay && goods.getAuditStatus()) {
                    integralOrder.setOrderState(OrderCons.WAIT_AUDIT);
                }
                // 更新订单记入开发者平台订单号
                integralOrder.setBizId(bizId);
                integralOrder.setPayTime(new Date());
                // </editor-fold>
            } else {
                // 开发者平台扣除积分异常
                integralOrder.setOrderState(OrderCons.FAILURE);
            }
            super.update(integralOrder);

            // 当订单状态为，待发货状态且开发平台订单号不为空（第三方开发平台已扣除扣除积分）通知开发平台订单已扣除积分
            Boolean state = OrderCons.WAIT_SEND == integralOrder.getOrderState() || OrderCons.OVER == integralOrder.getOrderState();
            if (state && Utils.isNotEmpty(integralOrder.getBizId())) {
                // 当扣除积分成功时，如果订单为上门自提，则生成核销码
                if (OrderCons.SEND_TYPE_RECEIVE == integralOrder.getOrderStateType()) {
                    createOrderVerifyCode(integralOrder.getOid());
                }
                // 订单处理成功 减去库存
                BaseResult baseResult = integralGoodsService.reduceGoodsInventory(integralOrder.getAcApply().getId(), goods.getId(), integralOrder.getOrderGoodsCount());
                log.info("调用修改库存接口：" + (baseResult.getSuccess() ? "成功" : "失败") +
                        (baseResult.getSuccess() ? "" : "错误信息：" + baseResult.getMessage()));
                // 异步任务，通知开发平台
                asynHookDeduction(appKey, integralOrder, true);
                // 当订单状态不为待发货状态且开发平台订单号不为空（第三方开发平台已扣除扣除积分）通知开发平台订单扣除积分异常！
            } else if (Utils.isNotEmpty(integralOrder.getBizId()) && !isMoneyPay && !goods.getAuditStatus()) {
                asynHookDeduction(appKey, integralOrder, false);
            } else if (isMoneyPay && Utils.isNotEmpty(integralOrder.getBizId())) {
                return BaseResult.SUCCESS("积分扣除成功！", "");
                // 待审核状态下 需要库存库存
            } else if (OrderCons.WAIT_AUDIT == integralOrder.getOrderState() && Utils.isNotEmpty(integralOrder.getBizId())) {
                // 订单处理成功 减去库存
                BaseResult baseResult = integralGoodsService.reduceGoodsInventory(integralOrder.getAcApply().getId(), goods.getId(), integralOrder.getOrderGoodsCount());
                log.info("调用修改库存接口：" + (baseResult.getSuccess() ? "成功" : "失败") +
                        (baseResult.getSuccess() ? "" : "错误信息：" + baseResult.getMessage()));
            }
            return result;
        } catch (Exception e) {
            if (Utils.isNotEmpty(integralOrder.getBizId())) {
                asynHookDeduction(appKey, integralOrder, false);
            }
            log.error("请求开发者平台扣除积分异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    /**
     * 异步任务：通知开发平台
     *
     * @param appKey        应用key
     * @param integralOrder 订单对象
     * @param success       是否更新订单成功
     * @throws Exception
     */
    @Async
    void asynHookDeduction(String appKey, IntegralOrder integralOrder, Boolean success) throws Exception {
        CreditNotifyParams params = new CreditNotifyParams();
        params.setAppKey(appKey);
        params.setUid(integralOrder.getDevUserId());
        params.setTimestamp(new Date());
        params.setBizId(integralOrder.getBizId());
        params.setOrderNum(integralOrder.getOid());
        params.setSuccess(success);
        BaseResult result = this.hookService.integralHook(params);
    }

    /**
     * 生成订单验证器
     *
     * @param applyId              应用编号
     * @param userId               下单用户编号
     * @param integralOrderAddForm 下单信息
     * @param setOrder             保存订单信息
     * @return
     */
    BaseResult paramsVerify(Long applyId, Long userId, IntegralOrderAddForm integralOrderAddForm, IntegralOrder setOrder) throws Exception {
        if (Utils.isEmpty(userId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_USER_ID_NULL_ERROR);
        }

        if (Utils.isEmpty(applyId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }

        if (Utils.isEmpty(integralOrderAddForm.getGoodsId())) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_GOODS_ID_NULL_ERROR);
        }

        // <editor-fold desc="验证兑换商品用户">
        IntegralUser integralUser = integralUserService.getObjById(userId);
        if (Utils.isEmpty(integralUser)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGE_USER_NULL_ERROR);
        }
        setOrder.setDevUserId(integralUser.getUid());
        setOrder.setOrderUserId(integralUser.getId());
        // </editor-fold>

        // <editor-fold desc="验证应用">
        IntegralAcApply integralAcApply = integralAcApplyDAO.findById(applyId);
        if (Utils.isEmpty(integralAcApply)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.AC_APPLY_NULL_ERROR);
        }
        // </editor-fold>

        // <editor-fold desc="验证兑换商品">
        // 验证商品兑换规则
        BaseResult baseResult = integralExchangeRulesService.getValiExchangeRules(integralOrderAddForm.getGoodsId(), userId);
        if (!baseResult.getSuccess()) {
            return baseResult;
        }
        IntegralGoods goods = integralGoodsService.getObjById(integralOrderAddForm.getGoodsId());
        integralGoodsService.detachObj(goods);
        if (Utils.isEmpty(goods)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.GOODS_NULL_ERROR);
        }
        IntegralOrderGoods integralOrderGoods = new IntegralOrderGoods();
        integralOrderGoods.setGoods(goods);
        setOrder.setOrderGoods(integralOrderGoods);
        // 验证商品剩余的库存
        if (integralOrderAddForm.getOrderGoodsCount() > goods.getGoodsInventory()) {
            return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_GOODS_INVENTORY_INSUFFICIENT);
        }
        // </editor-fold>
        return BaseResult.SUCCESS("验证完成！", "");
    }

}