package com.shopping.integral.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.domain.user.User;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.config.ResultStatus;
import com.shopping.integral.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityFilter implements Filter {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static Set<String> GreenUrlSet = new HashSet<String>();

	private static Set<String> NeedAccountSet = new HashSet<String>();

	private RedisService getRedisService(HttpServletRequest request){
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return (RedisService)factory.getBean("redisServiceImpl");
	};


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		GreenUrlSet.add("/demo");
		GreenUrlSet.add("/autoLogin");
		GreenUrlSet.add("/goods");
		GreenUrlSet.add("/app/pay/paySuccessHooks.htm");
		GreenUrlSet.add("/app/pay/WXpaySuccessHooks.htm");
		GreenUrlSet.add("/app/order/get_order_details_by_VerifyCode");
		GreenUrlSet.add("/app/order/verify_order");
		GreenUrlSet.add("/order/store_order_export");
		// 核销码
		GreenUrlSet.add("/app/index/verifyDetai");

		NeedAccountSet.add("/account");
		NeedAccountSet.add("/hbRecharge");
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) srequest;
		String uri = request.getRequestURI();
		if (containsSuffix(uri)  || greenUrlCheck(uri) || containsKey(uri) || containsSwagger(uri)) {
			logger.info("don't check url , " + request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
			return;
		}else{
			String authHeader = request.getHeader("Token");
			if (StringUtils.isEmpty(authHeader)) {
				logger.info("Signature verification does not exist.");
				print(sresponse, ResultStatus.JWT_ERRCODE_NULL.getCode(),ResultStatus.JWT_ERRCODE_NULL.getEgMessage());
			}else{
				//验证JWT的签名，返回CheckResult对象
				CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
				if (checkResult.isSuccess()) {
					String getKey = checkResult.getClaims().getId();
					String appKey = checkResult.getClaims().getSubject();
					User user = null;
					try {
						//user = Utils.isNotEmpty(login_user.get("id")) ? getUserService(request).getObjById(CommUtils.null2Long(login_user.get("id"))) : new User();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(needAccountUrlCheck(uri) && user.getAccount()==null){
						//跳转到开通账户页面
						print(sresponse,"401","need account");
					}else{
						filterChain.doFilter(srequest, sresponse);
					}
				} else {
					ResultStatus resultStatus = ResultStatus.getEnumByCode(checkResult.getErrCode());
					if(Utils.isNotEmpty(resultStatus)){
						print(sresponse,checkResult.getErrCode(),resultStatus.getEgMessage());
					}
				}
			}
		}
	}


	public void print(ServletResponse servletResponse,Object errorCode,String errorMesg) throws IOException{
		JSONObject json = new JSONObject();
		json.put("error",errorCode);
		json.put("error_msg",errorMesg);
		servletResponse.getWriter().write(JSON.toJSONString(json));
	}

	/**
	 * 检查无需登录前缀
	 * @param url
	 * @return
	 */
	private boolean greenUrlCheck(String url){
		for(String greenUrl : GreenUrlSet){
			if(url.startsWith(greenUrl)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 需要账户登录
	 * @param url
	 * @return
	 */
	private boolean needAccountUrlCheck(String url){
		for(String greenUrl : NeedAccountSet){
			if(url.startsWith(greenUrl)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param url
	 * @return
	 * @author neo
	 */
	private boolean containsSuffix(String url) {
		if (url.endsWith(".js")
				|| url.endsWith(".css")
				|| url.endsWith(".jpg")
				|| url.endsWith(".gif")
				|| url.endsWith(".png")
				|| url.endsWith(".html")
				|| url.endsWith(".eot")
				|| url.endsWith(".svg")
				|| url.endsWith(".ttf")
				|| url.endsWith(".woff")
				|| url.endsWith(".ico")
				|| url.endsWith(".woff2")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param url
	 * @return
	 * @author neo
	 */
	private boolean containsKey(String url) {
		if (url.contains("/media/")){
			return true;
		} else {
			return false;
		}
	}

	private boolean containsSwagger(String url) {
		if (url.contains("swagger") || url.contains("/v2/api-docs")){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}