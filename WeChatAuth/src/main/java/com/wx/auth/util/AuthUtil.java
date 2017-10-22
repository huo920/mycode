package com.wx.auth.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class AuthUtil {
	public static final String APPID = "wx6ea98f3b86580085";
	public static final String APPSECRET = "2804fbc74f1b1e7e1700c48f66fcf7d5";
	
	public static JSONObject doGetJson(String url) throws IOException{
		JSONObject jsonObject = null;
		DefaultHttpClient  client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet();
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"utf-8");
			jsonObject = JSONObject.fromObject(result);
		}
		httpGet.releaseConnection();
		return jsonObject;
		
	}

}
