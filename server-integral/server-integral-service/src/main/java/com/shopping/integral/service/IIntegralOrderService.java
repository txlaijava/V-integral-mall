package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.queryform.IntegralOrderAddForm;
import com.shopping.integral.queryform.IntegralOrderQueryForm;

import java.math.BigDecimal;


/**
 * 类描述：IntegralOrderService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface IIntegralOrderService extends IBaseService<IntegralOrder, Long> {

    /**
     * 创建兑换商品订单
     *
     * @param applyId              应用编号
     * @param userId               兑换商品用户编号
     * @param integralOrderAddForm 提交的订单信息
     * @return
     */
    BaseResult setIntegralOrder(Long applyId, Long userId, IntegralOrderAddForm integralOrderAddForm);

    /**
     * 获取订单详情
     *
     * @param userId  所属用户编号
     * @param oid     要查询的订单号
     * @param applyId 所属应用编号
     * @return
     */
    BaseResult getIntegralOrderDetails(Long userId, String oid, Long applyId);

    /**
     * 通过核销码获取订单详情
     *
     * @param verifyCode     核销码
     * @param applyId 所属应用编号
     * @return
     */
    BaseResult getIntegralOrderDetailsByVerifyCode(String verifyCode, Long applyId);

    /**
     * 获取订单详情
     *
     * @param integralOrderQueryForm 查询条件
     * @return
     */
    BaseResult getIntegralOrderList(IntegralOrderQueryForm integralOrderQueryForm);

    /**
     * 导出订单列表
     *
     * @param integralOrderQueryForm 查询条件
     * @return
     */
    void ExOrderList(IntegralOrderQueryForm integralOrderQueryForm);

    /**
     * 订单上门自提生成核销码
     *
     * @param oid 订单编号
     * @return
     * @throws Exception
     */
    BaseResult createOrderVerifyCode(String oid) throws Exception;

    /**
     * 获取指定条件下的订单数量（该接口只返回兑换成功的订单）
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param goodsId   下单商品编号
     * @param userId    下单用户
     * @return 对应的商品数据
     * @throws Exception
     */
    BaseResult getByGoodsIdOrderCount(String startDate, String endDate, Long goodsId, Long userId) throws Exception;

    /**
     * 支付成功回调处理订单
     *
     * @param chargeId 支付渠道唯一ID
     * @param amount   支付金额
     * @param oid      订单号
     * @return
     * @throws Exception
     */
    BaseResult dispostDepartmentSuccess(String chargeId, BigDecimal amount, String oid) throws Exception;

    /**
     * 取消订单操作
     *
     * @param oid          取消的订单号
     * @param cancelReason 取消理由
     * @param isUserCancel 是否是用户手动取消
     * @return
     */
    BaseResult cancelOrder(String oid, String cancelReason, Boolean isUserCancel);

    /**
     * 核销订单
     *
     * @param oid          核销的订单号
     * @return
     */
    BaseResult verifyOrder(String oid);

    /**
     * 审核订单
     * @param oid
     * @return
     */
    BaseResult auditOrder(String oid,String status);

    /**
     * 进行订单锁修改
     *
     * @param oid  订单号
     * @param lock 锁
     */
    void setOrderPayLock(String oid, Boolean lock);
}