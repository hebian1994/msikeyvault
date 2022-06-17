package com.example.msikeyvault.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Slf4j
public class HttpUtil {
    /**
     * http get请求
     *
     * @param httpUrl 链接
     * @return 响应数据
     */
    public static String doGet(String httpUrl, Map<String, String> headers) {
        log.info("key vault请求参数{}{}", httpUrl, headers);
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer result = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setConnectTimeout(15000);
            //设置读取超时时间
            connection.setReadTimeout(15000);
            //请求azure token 必须
            if (!headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (is != null) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        result.append(temp);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();// 关闭远程连接
        }

        log.info("key vault响应内容{}", result);
        return result.toString();
    }
}
