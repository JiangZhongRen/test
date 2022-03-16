package com.jd.utils;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Slf4j
public class GetJson {
    public String getHttpJson(String url, int comefrom) throws Exception {
        try {
            log.info("url: " + url);
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            // 建立实际的连接
            connection.connect();
            //请求成功
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //10MB的缓存
                byte[] buffer = new byte[10485760];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                String jsonString = baos.toString("gb18030");
                baos.close();
                is.close();
                //转换成json数据处理
                // getHttpJson函数的后面的参数1，表示返回的是json数据，2表示http接口的数据在一个（）中的数据
                //JSONObject jsonArray = getJsonString(jsonString, comefrom);
                return jsonString;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public JSONObject getJsonString(String str, int comefrom) throws Exception {
        JSONObject jo = null;
        if (comefrom == 1) {
            return new JSONObject(str);
        } else if (comefrom == 2) {
            int indexStart = 0;
            //字符处理
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    indexStart = i;
                    break;
                }
            }
            String strNew = "";
            //分割字符串
            for (int i = indexStart + 1; i < str.length() - 1; i++) {
                strNew += str.charAt(i);
            }
            return new JSONObject(strNew);
        }
        return jo;
    }

}