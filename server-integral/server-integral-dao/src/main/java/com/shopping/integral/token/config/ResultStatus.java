package com.shopping.integral.token.config;

import com.shopping.base.utils.CommUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义请求状态码
 *
 * @author ScienJus
 */
public enum ResultStatus {
    JWT_ERRCODE_NULL(400, "Token不存在", "Token does not exist"),
    JWT_ERRCODE_EXPIRE(410, "Token过期", "Token expired"),
    JWT_ERRCODE_FAIL(420, "验证不通过", "Validate against");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String zhMessage;

    private String egMessage;

    ResultStatus(int code, String zhMessage, String egMessage) {
        this.code = code;
        this.zhMessage = zhMessage;
        this.egMessage = egMessage;
    }

    public static ResultStatus getEnumByCode(Integer code) {
        for (ResultStatus stateEnum : ResultStatus.values()) {
            if (StringUtils.equals(CommUtils.null2String(code), CommUtils.null2String(stateEnum.getCode()))) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getZhMessage() {
        return zhMessage;
    }

    public void setZhMessage(String zhMessage) {
        this.zhMessage = zhMessage;
    }

    public String getEgMessage() {
        return egMessage;
    }

    public void setEgMessage(String egMessage) {
        this.egMessage = egMessage;
    }
}
