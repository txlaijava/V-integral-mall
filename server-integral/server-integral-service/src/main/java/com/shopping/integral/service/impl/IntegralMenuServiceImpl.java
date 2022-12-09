package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.dao.model.IntegralMenu;
import com.shopping.integral.service.IIntegralMenuService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 类描述：IntegralMenuService 实现
*
* @author：GuoFuJun
* @date：2018年07月20日 16:23:49.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralMenuServiceImpl extends BaseServiceImpl<IntegralMenu,Long> implements IIntegralMenuService {


}