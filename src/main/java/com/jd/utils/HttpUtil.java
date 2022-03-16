package com.jd.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 模拟浏览器请求任何一个网址
 * @author 
 */
@Component("httpUtil")
public class HttpUtil
{
	
	public class TrustAnyHostnameVerifier implements HostnameVerifier
	{
		public boolean verify(String hostname, SSLSession session)
		{
			// 直接返回true
			return true;
		}
	}
	/**
	 * 输入一个url,
	 * 返回这个url对应的html代码
	 * 
	 * @param urlStr:网址
	 * @return 返回网址对应的html代码
	 */
	public static String methodGet(String urlStr)
	{
		//ConstatFinalUtil.SYS_LOGGER.info("--methodGet--url:{}",urlStr);
		StringBuffer sb = new StringBuffer() ; 
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			
			/* 获取网址对应的输入流和输出流 */
			InputStream is = connection.getInputStream() ;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String line = "" ; 
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString() ; 
	}
	
	/**
	 * 输入一个url,
	 * 返回这个url对应的html代码
	 * 
	 * @param urlStr:网址
	 * @param paramsMap	提交参数;键=值
	 * @return 返回网址对应的html代码
	 */
	public String methodPost(String urlStr,Map<String, String> paramsMap)
	{
		//ConstatFinalUtil.SYS_LOGGER.info("--methodGet--url:{}",urlStr);
		StringBuffer sb = new StringBuffer() ; 
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection() ; 
			
			/* Post请求,必须加以下操作 */
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			
			/* 获取网址对应的输入流和输出流 */
			OutputStream os = connection.getOutputStream() ;
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			/* 先向服务器写数据
			 * 如何将我们的参数写到输出流呢???
			 * 得遵循HTTP协议,看浏览器怎么做,我们也怎么做
			 * 
			 * URL get中的字符串
			 * method=submit&email=aaa&password=bbbb
			 *  */
			StringBuffer paramSb = new StringBuffer() ; 
			for (Iterator iterator = paramsMap.entrySet().iterator(); iterator.hasNext();)
			{
				Entry me = (Entry) iterator.next();
				String key = me.getKey() + ""; 
				String value = me.getValue() + "" ;
				paramSb.append(key + "=" + value + "&");
			}
			/*
			 * method=submit&email=aaa&password=bbbb
			 * email=bb&method=submit&password=cc
			 * */
			bw.write(paramSb.toString());
			bw.flush();
			bw.close();
			
			InputStream is = connection.getInputStream() ;
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String line = "" ; 
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}
			
			br.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString() ; 
	}
	
	public static void main(String[] args)
	{
		HttpUtil httpUtil = new HttpUtil() ;
		
		/*String url = "http://c.v.qq.com/vchannelinfo?otype=json&uin=599568d32c9189aaabe3c6c6aff3832c&qm=1&pagenum=3&num=10&sorttype=0&orderflag=0&callback=jQuery191013239971445429688_1498793173745&low_login=1&_=1498793173756" ; 
		String res = httpUtil.methodGet(url);
		System.out.println(res);*/
		String ceshString ="{json={\"method\":\"verifyAdminsAuth\",\"data\":{\"currentUrl\":\"/carConAll-web-back/back/vehicle/vehHistoryList.htm?from=home\",\"adminsId\":2},\"encrypt\":\"e12299c068c5e8dd3a1e30ed9780b82721940a7779abfeaecbb409a9466fd673\",\"version\":\"1\",\"pubKey\":\"\"}}";
		JSONObject parse = (JSONObject) JSON.parse(ceshString);
		/*
		 * String url = "http://localhost:10001/shop-web-head/insert.jsp" ;
		 * Map<String,String> paramsMap = new HashMap<String, String>();
		 * paramsMap.put("email", "bb"); paramsMap.put("password", "cc");
		 * paramsMap.put("method", "submit"); String res =
		 * httpUtil.methodPost(url,paramsMap);
		 */
		System.out.println(parse);
	}

	
}