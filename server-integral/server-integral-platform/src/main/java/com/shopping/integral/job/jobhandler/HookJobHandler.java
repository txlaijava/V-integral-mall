package com.shopping.integral.job.jobhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.date.DateUtil;
import com.shopping.integral.dao.IntegralOrderHookDAO;
import com.shopping.integral.dao.model.IntegralOrderHook;
import com.shopping.integral.repository.OrderHookRepository;
import com.shopping.integral.sdk.HttpUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 积分通知定时器
 */
@Log4j2
@Component
public class HookJobHandler {

	@Autowired
	private IntegralOrderHookDAO integralOrderHookDAO;

	@Autowired
	private OrderHookRepository orderHookRepository;

	@XxlJob("hookJobHandler")
	public ReturnT<String> execute(String param) throws Exception {

		Map pa = new HashMap<>();
		pa.put("hook_status",0);
		List<IntegralOrderHook> hooks = this.integralOrderHookDAO.findByMap(pa);

		for(IntegralOrderHook hook : hooks){
			if(!"".equals(CommUtils.null2String(hook.getHook_times())) && hook.getHook_num()>0){
				try{
					JSONArray times = JSON.parseArray(hook.getHook_times());
					if(hook.getHook_num() >= times.size()) {
						//每次都取最后一个时间 这样保证时间都用完
						String time = times.get(times.size()-1).toString();
						//去掉最后一个通知时间
						times.remove(times.size()-1);
						if(DateUtil.compareDate(DateUtil.formatDate(new Date()),time,"yyyy-MM-dd HH:mm:ss")==1){
							String appKey = hook.getApp_key();
							String hookUrl = hook.getHook_url();
							String response = HttpUtil.sendGet(hookUrl);
							if(!"".equals(response)){
								if("ok".equalsIgnoreCase(response)){
									hook.setHook_num(0);
									hook.setHook_status(1);
									this.orderHookRepository.saveAndFlush(hook);
									log.info("执行成功,hook id" + hook.getId());
								}else{
									hook.setHook_times(JSON.toJSONString(times));
									//通知次数减少
									hook.setHook_num(hook.getHook_num() - 1);
									hook.setHook_status(0);
									this.orderHookRepository.saveAndFlush(hook);
									log.info("执行错误,hook id" + hook.getId(),"开发者返回通知失败信息");
								}
							}else{
								hook.setHook_times(JSON.toJSONString(times));
								hook.setHook_num(hook.getHook_num() - 1);
								hook.setHook_status(0);
								this.orderHookRepository.saveAndFlush(hook);
								log.info("执行错误,hook id" + hook.getId(),"未返回接口数据");
							}
						}
					}
				}catch (Exception ex){
					log.error("执行错误,hook id" + hook.getId(),ex);
				}
			}else{
				hook.setHook_status(2);
				this.orderHookRepository.saveAndFlush(hook);
				log.info("执行失败,找不到执行日期,hook id" + hook.getId());
			}
		}
		return ReturnT.SUCCESS;
	}

}
