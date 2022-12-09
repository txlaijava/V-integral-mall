package com.shopping.integral.sdk;


import com.shopping.integral.sdk.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreditTool {

    private String appKey;
    private String appSecret;

    public CreditTool(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    /**
     * 通用的url生成方法
     *
     * @param url
     * @param params
     * @return
     */
    public String buildUrlWithSign(String url, Map<String, String> params) {
        Map<String, String> newparams = new HashMap<String, String>(params);
        newparams.put("appKey", appKey);
        newparams.put("appSecret", appSecret);
        if (newparams.get("timestamp") == null) {
            newparams.put("timestamp", System.currentTimeMillis() + "");
        }
        String sign = SignTool.sign(newparams);
        newparams.put("sign", sign);

        newparams.remove("appSecret");

        return AssembleTool.assembleUrl(url, newparams);
    }

    public String buildPostWithSign(String url, Map<String, String> params) throws Exception{
        Map<String, String> newparams = new HashMap<String, String>(params);
        newparams.put("appKey", appKey);
        newparams.put("appSecret", appSecret);
        if (newparams.get("timestamp") == null) {
            newparams.put("timestamp", System.currentTimeMillis() + "");
        }
        String sign = SignTool.sign(newparams);
        newparams.put("sign", sign);

        newparams.remove("appSecret");

        return HttpUtil.httpPost(url,newparams,null);
    }

    /**
     * 积分消耗请求的解析方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    public CreditConsumeParams parseCreditConsume(HttpServletRequest request) throws Exception {
        CreditConsumeParams params = new CreditConsumeParams();
        if (!appKey.equals(request.getParameter("appKey"))) {
            params.setSuccess(false);
            params.setMsg("appKey不匹配");
            return params;
        }
        if (request.getParameter("timestamp") == null) {
            params.setSuccess(false);
            params.setMsg("请求中没有带时间戳");
            return params;
        }
        boolean verify = SignTool.signVerify(appSecret, request);
        if (!verify) {
            params.setSuccess(false);
            params.setMsg("签名验证失败");
            return params;
        }
        params.setSuccess(true);
        params.setAppKey(appKey);
        params.setUid(request.getParameter("uid"));
        params.setCredits(Long.valueOf(request.getParameter("credits")));
        params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
        params.setDescription(request.getParameter("description"));
        params.setOrderNum(request.getParameter("orderNum"));
        params.setType(request.getParameter("type"));
        if (request.getParameter("facePrice") != null) {
            params.setFacePrice(Integer.valueOf(request.getParameter("facePrice")));
        }
        if (request.getParameter("actualPrice") != null) {
            params.setActualPrice(Integer.valueOf(request.getParameter("actualPrice")));
        }
        if (request.getParameter("itemCode") != null) {
            params.setItemCode(request.getParameter("itemCode"));
        }
        params.setAlipay(request.getParameter("alipay"));
        params.setPhone(request.getParameter("phone"));
        params.setQq(request.getParameter("qq"));
        if (request.getParameter("waitAudit") != null) {
            params.setWaitAudit(Boolean.valueOf(request.getParameter("waitAudit")));
        }
        params.setIp(request.getParameter("ip"));
        params.setParams(request.getParameter("params"));
        params.setRedirect(request.getParameter("redirect"));
        params.setOpenid(request.getParameter("openid"));
        return params;
    }

    /**
     * 积分消耗结果通知请求  的解析方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    public CreditNotifyParams parseCreditNotify(HttpServletRequest request) throws Exception {
        if (!appKey.equals(request.getParameter("appKey"))) {
            throw new Exception("appKey不匹配");
        }
        if (request.getParameter("timestamp") == null) {
            throw new Exception("请求中没有带时间戳");
        }
        boolean verify = SignTool.signVerify(appSecret, request);
        if (!verify) {
            throw new Exception("签名验证失败");
        }

        CreditNotifyParams params = new CreditNotifyParams();
        params.setSuccess(Boolean.valueOf(request.getParameter("success")));
        params.setErrorMessage(request.getParameter("errorMessage"));
        params.setBizId(request.getParameter("bizId"));
        params.setUid(request.getParameter("uid"));
        params.setAppKey(request.getParameter("appKey"));
        params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
        params.setOrderNum(request.getParameter("orderNum"));
        return params;
    }

    /**
     * 需要审核的兑换 的解析方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    public CreditNeedAuditParams parseCreditAudit(HttpServletRequest request) throws Exception {
        if (!appKey.equals(request.getParameter("appKey"))) {
            throw new Exception("appKey不匹配");
        }
        if (request.getParameter("timestamp") == null) {
            throw new Exception("请求中没有带时间戳");
        }
        boolean verify = SignTool.signVerify(appSecret, request);
        if (!verify) {
            throw new Exception("签名验证失败");
        }

        CreditNeedAuditParams params = new CreditNeedAuditParams();
        params.setAppKey(appKey);
        params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
        params.setBizId(request.getParameter("bizId"));

        return params;
    }


    /**
     * 虚拟商品充值解析
     *
     * @param request
     * @return
     * @throws Exception
     */
    public VirtualParams virtualConsume(HttpServletRequest request) throws Exception {
        if (!appKey.equals(request.getParameter("appKey"))) {
            throw new Exception("appKey不匹配");
        }
        if (request.getParameter("timestamp") == null) {
            throw new Exception("请求中没有带时间戳");
        }
        boolean verify = SignTool.signVerify(appSecret, request);
        if (!verify) {
            throw new Exception("签名验证失败");
        }
        VirtualParams params = new VirtualParams();
        params.setAppKey(appKey);
        params.setUid(request.getParameter("uid"));
        params.setSupplierBizId(request.getParameter("supplierBizId"));
        params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
        params.setDescription(request.getParameter("description"));
        params.setOrderNum(request.getParameter("orderNum"));
        if (request.getParameter("account") != null) {
            params.setAccount(request.getParameter("account"));
        }
        params.setParams(request.getParameter("params"));
        return params;
    }


    /**
     * 加积分接口解析
     *
     * @param request
     * @return
     * @throws Exception
     */

    public AddCreditsParams parseaddCredits(HttpServletRequest request) throws Exception {
        if (!appKey.equals(request.getParameter("appKey"))) {
            throw new Exception("appKey不匹配");
        }
        if (request.getParameter("timestamp") == null) {
            throw new Exception("请求中没有带时间戳");
        }
        boolean verify = SignTool.signVerify(appSecret, request);
        if (!verify) {
            throw new Exception("签名验证失败");
        }
        AddCreditsParams params = new AddCreditsParams();
        params.setAppKey(appKey);
        params.setUid(request.getParameter("uid"));
        params.setCredits(Long.valueOf(request.getParameter("credits")));
        params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
        params.setDescription(request.getParameter("description"));
        params.setOrderNum(request.getParameter("orderNum"));
        params.setType(request.getParameter("type"));
        if (request.getParameter("transfer") != null) {
            params.setTransfer(request.getParameter("transfer"));
        }
        return params;
    }


}
