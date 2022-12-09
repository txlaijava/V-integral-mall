package com.shopping.integral.sdk.entity;


import com.shopping.integral.sdk.SignTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreditNeedAuditParams {

	private String appKey;
	private String bizId="";
	private Date timestamp;
	
	public CreditNeedAuditParams(){
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public Map<String, String> toRequestMap(String appSecret){
		Map<String, String> map=new HashMap<String, String>();
		map.put("bizId", bizId+"");
		map.put("appKey", appKey+"");
		map.put("appSecret", appSecret+"");
		map.put("timestamp", timestamp.getTime()+"");
		
		String sign= SignTool.sign(map);
		
		map.remove("appSecret");
		map.put("sign", sign);
		return map;
	}
}
