package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.dao.model.IntegralGoodsSpec;
import com.shopping.integral.service.IIntegralGoodsSpecService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 类描述：IntegralGoodsSpecService 实现
*
* @author：GuoFuJun
* @date：2018年05月03日 21:21:24.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralGoodsSpecServiceImpl extends BaseServiceImpl<IntegralGoodsSpec,Long> implements IIntegralGoodsSpecService {


}