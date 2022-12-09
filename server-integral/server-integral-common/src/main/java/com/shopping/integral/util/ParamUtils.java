package com.shopping.integral.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gfj on 2018/5/17.
 */
public class ParamUtils {

    public static Map<String,String> getparams(HttpServletRequest request){
        Enumeration<String> enume = request.getParameterNames();
        Map<String,String> paramMap = new HashMap<String,String>();
        while (enume.hasMoreElements()) {
            String propertyName = enume.nextElement();
            String propertyValue = request.getParameter(propertyName);
            paramMap.put(propertyName, propertyValue);
        }
        return paramMap;
    }
}
