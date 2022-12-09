package com.shopping.integral.util;


import com.shopping.base.utils.CommUtils;

import java.util.Date;

/**
 * 主键或单号生成工具类
 *
 * @author wanglu
 */
public class UniqueIDentifiUtil {

    /**
     * 获取当前时间，转换yyyyMMddhhmmss格式
     *
     * @return
     */
    public static String getDate() {

        String dateStr = CommUtils.formatTime("yyyyMMddHHmmssSSS", new Date());
        return dateStr;
    }

    /**
     * 2位的随机码生成
     *
     * @return
     */
    public static String getRandom() {
        return String.valueOf(((int) ((Math.random() * 90) + 10)));
    }


    /**
     * 主键或单号ID长度19位，日期：年月日时分秒+随机数：两位
     *
     * @return
     */
    public static String getRandomid() {
        StringBuffer sb = new StringBuffer();
        sb.append(UniqueIDentifiUtil.getDate());
        sb.append(UniqueIDentifiUtil.getRandom());
        return sb.toString();
    }

    /**
     * 主键或单号ID长度16位，日期：年月日时分秒+随机数：两位
     *
     * @return
     */
    public static String getmovieid() {
        String dateStr = CommUtils.formatTime("yyyyMMddHHmmss", new Date());
        StringBuffer sb = new StringBuffer();
        sb.append(dateStr);
        sb.append(UniqueIDentifiUtil.getRandom());
        return sb.toString();
    }

    /**
     * 随机生成 12 为商品核销码
     * @return
     */
    public static String getVerifyCode(){
        StringBuffer sb = new StringBuffer();
        sb.append(CommUtils.randomInt(4) + "-" + CommUtils.randomInt(4) + "-" + CommUtils.randomInt(4));
        return sb.toString();
    }
}
