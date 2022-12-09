package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.dao.model.IntegralTemplate;
import com.shopping.integral.service.IIntegralTemplateService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 类描述：IntegralTemplateService 实现
*
* @author：GuoFuJun
* @date：2018年07月20日 21:51:33.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralTemplateServiceImpl extends BaseServiceImpl<IntegralTemplate,Long> implements IIntegralTemplateService {


}