package com.shopping.integral.dao;

import com.shopping.integral.dao.model.*;
import com.shopping.integral.util.GeneratorUtil;

/**
 * Created by gfj on 2018/3/29.
 */
public class Generator {

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) throws Exception {
        GeneratorUtil.generator(IntegralGoodsCategory.class,"server-integral","com.shopping.integral");
    }
}
