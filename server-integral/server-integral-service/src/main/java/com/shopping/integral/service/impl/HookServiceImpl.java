package com.shopping.integral.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.date.DateUtil;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.ConsConfig;
import com.shopping.integral.dao.IntegralOrderHookDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralOrderHook;
import com.shopping.integral.sdk.CreditTool;
import com.shopping.integral.sdk.HttpUtil;
import com.shopping.integral.sdk.entity.CreditConsumeParams;
import com.shopping.integral.sdk.entity.CreditNotifyParams;
import com.shopping.integral.sdk.entity.VirtualParams;
import com.shopping.integral.service.IHookService;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralInterfaceSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class HookServiceImpl implements IHookService {

    @Autowired
    private IIntegralAcApplyService integralAcApplyService;

    @Autowired
    private IIntegralInterfaceSettingService integralInterfaceSettingService;

    @Autowired
    private IntegralOrderHookDAO integralOrderHookDAO;

    @Override
    public BaseResult integralDeductionPost(CreditConsumeParams params) throws Exception {
        String appKey = params.getAppKey();
        IntegralAcApply acApply = this.integralAcApplyService.getOneObjByProperty("appKey", appKey);
        if (acApply != null) {
            //实体bean转map
            Map post = params.toRequestMap(acApply.getAppSecret());
            String dev_url = this.integralInterfaceSettingService.getByAcAndType(acApply, ConsConfig.ITF_CHARGE);
            if (!"".equals(dev_url)) {
                CreditTool tool = new CreditTool(acApply.getAppKey(), acApply.getAppSecret());
                String url = tool.buildUrlWithSign(dev_url + "?", post);
                //请求开发者服务器处理扣除积分操作,获取返回值
                String response = HttpUtil.sendGet(url);
                //"{'status':'ok','errorMessage':'','bizId':'"+bizId+"','credits':'"+credits+"'}
                //"{'status':'fail','errorMessage':'"+errorMessage+"','credits':'"+credits+"'}
                if (!"".equals(response)) {
                    JSONObject json = JSON.parseObject(response);
                    if ("ok".equals(json.get("status"))) {

                        String bizId = CommUtils.null2String(json.get("bizId"));
                        String credits = CommUtils.null2String(json.get("credits"));


                        return BaseResult.SUCCESS("成功", json);
                    } else {
                        return BaseResult.ERROR(500, "开发者返回错误信息:" + json.get("errorMessage"));
                    }
                } else {
                    return BaseResult.ERROR(500, "未返回接口数据");
                }
            } else {
                return BaseResult.ERROR(500, "未配置对应的接口地址");
            }
        } else {
            return BaseResult.ERROR(500, "开发者应用异常");
        }
    }

    @Override
    public BaseResult integralCouponPost(VirtualParams params) throws Exception {
        String appKey = params.getAppKey();
        IntegralAcApply acApply = this.integralAcApplyService.getOneObjByProperty("appKey", appKey);
        if (acApply != null) {
            //实体bean转map
            Map post = params.toRequestMap(acApply.getAppSecret());
            String dev_url = this.integralInterfaceSettingService.getByAcAndType(acApply, ConsConfig.ITF_XN_CHARGE);
            if (!"".equals(dev_url)) {
                CreditTool tool = new CreditTool(acApply.getAppKey(), acApply.getAppSecret());
                String url = tool.buildUrlWithSign(dev_url + "?", post);
                //请求开发者服务器处理加积分操作,获取返回值
                String response = HttpUtil.sendGet(url);
                //{'status':'success','credits':'"+credits+"','supplierBizId':'"+supplierBizId+"'}
                //{'status':'fail','errorMessage':'"+errorMessage+"','supplierBizId':'"+supplierBizId+"'}"
                if (!"".equals(response)) {
                    JSONObject json = JSON.parseObject(response);
                    if ("success".equals(json.get("status"))) {

                        String supplierBizId = CommUtils.null2String(json.get("supplierBizId"));
                        String credits = CommUtils.null2String(json.get("credits"));

                        return BaseResult.SUCCESS("成功", json);
                    } else {
                        return BaseResult.ERROR(500, "开发者返回错误信息:" + json.get("errorMessage"));
                    }
                } else {
                    return BaseResult.ERROR(500, "未返回接口数据");
                }
            } else {
                return BaseResult.ERROR(500, "未配置对应的接口地址");
            }
        } else {
            return BaseResult.ERROR(500, "开发者应用异常");
        }
    }

    @Override
    public BaseResult integralAddPost(CreditConsumeParams params) throws Exception {
        String appKey = params.getAppKey();
        IntegralAcApply acApply = this.integralAcApplyService.getOneObjByProperty("appKey", appKey);
        if (acApply != null) {
            //实体bean转map
            Map post = params.toRequestMap(acApply.getAppSecret());
            String dev_url = this.integralInterfaceSettingService.getByAcAndType(acApply, ConsConfig.ITF_ADD);
            if (!"".equals(dev_url)) {
                CreditTool tool = new CreditTool(acApply.getAppKey(), acApply.getAppSecret());
                String url = tool.buildUrlWithSign(dev_url + "?", post);
                //请求开发者服务器处理加积分操作,获取返回值
                String response = HttpUtil.sendGet(url);
                //"{'status':'ok','errorMessage':'','bizId':'"+bizId+"','credits':'"+credits+"'}
                //"{'status':'fail','errorMessage':'"+errorMessage+"','credits':'"+credits+"'}
                if (!"".equals(response)) {
                    JSONObject json = JSON.parseObject(response);
                    if ("ok".equals(json.get("status"))) {

                        String bizId = CommUtils.null2String(json.get("bizId"));
                        String credits = CommUtils.null2String(json.get("credits"));


                        return BaseResult.SUCCESS("成功", json);
                    } else {
                        return BaseResult.ERROR(500, "开发者返回错误信息:" + json.get("errorMessage"));
                    }
                } else {
                    return BaseResult.ERROR(500, "未返回接口数据");
                }
            } else {
                return BaseResult.ERROR(500, "未配置对应的接口地址");
            }
        } else {
            return BaseResult.ERROR(500, "开发者应用异常");
        }
    }

    @Override
    public BaseResult integralHook(CreditNotifyParams params) throws Exception {
        String appKey = params.getAppKey();
        IntegralAcApply acApply = this.integralAcApplyService.getOneObjByProperty("appKey", appKey);
        if (acApply != null) {
            //实体bean转map
            Map post = params.toRequestMap(acApply.getAppSecret());
            String dev_url = this.integralInterfaceSettingService.getByAcAndType(acApply, ConsConfig.ITF_HOOK);
            if (!"".equals(dev_url)) {
                CreditTool tool = new CreditTool(acApply.getAppKey(), acApply.getAppSecret());
                String url = tool.buildUrlWithSign(dev_url + "?", post);

                //生成订单请求数据
                IntegralOrderHook hook = new IntegralOrderHook();
                hook.setAdd_time(new Date());
                hook.setApp_key(appKey);
                hook.setBiz_id(params.getBizId());
                hook.setOid(params.getOrderNum());
                hook.setHook_url(url);
                hook.setHook_num(7);
                hook.setHook_status(3);
                this.integralOrderHookDAO.save(hook);

                //请求开发者服务器处理加积分操作,获取返回值
                String response = HttpUtil.sendGet(url);
                //结果: ok  fail
                if (!"".equals(response)) {
                    if ("ok".equalsIgnoreCase(response)) {
                        //更新通知表
                        hook.setHook_num(0);
                        hook.setHook_status(1);
                        this.integralOrderHookDAO.update(hook);
                        return BaseResult.SUCCESS("成功", response);
                    } else {
                        //更新通知状态 让任务调度中心进行再次推送
                        /**
                         *通知时间间隔为：2m、10m、20m、1h、2h、6h、15h。
                         */
                        ArrayList hooktimes = new ArrayList();
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 15 * 60)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 6 * 60)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 2 * 60)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 1 * 60)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 20)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 10)));
                        hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 2)));
                        String times = JSON.toJSONString(hooktimes);
                        hook.setHook_times(times);
                        hook.setHook_status(0);
                        this.integralOrderHookDAO.update(hook);
                        return BaseResult.ERROR(500, "开发者返回通知失败信息");
                    }
                } else {
                    //更新通知状态 让任务调度中心进行再次推送
                    /**
                     *通知时间间隔为：2m、10m、20m、1h、2h、6h、15h。
                     */
                    ArrayList hooktimes = new ArrayList();
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 15 * 60)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 6 * 60)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 2 * 60)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 1 * 60)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 20)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 10)));
                    hooktimes.add(DateUtil.formatDate(DateUtil.getDateAddMinutes(new Date(), 2)));
                    String times = JSON.toJSONString(hooktimes);
                    hook.setHook_times(times);
                    hook.setHook_status(0);
                    this.integralOrderHookDAO.update(hook);
                    return BaseResult.ERROR(500, "未返回接口数据");
                }
            } else {
                return BaseResult.ERROR(500, "未配置对应的接口地址");
            }
        } else {
            return BaseResult.ERROR(500, "开发者应用异常");
        }


    }

}