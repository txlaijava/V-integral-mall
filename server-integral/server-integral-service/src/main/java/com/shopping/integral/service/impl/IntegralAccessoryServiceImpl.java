package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.service.IIntegralAccessoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAccessoryServiceImpl extends BaseServiceImpl<IntegralAccessory,Long> implements IIntegralAccessoryService {
}
