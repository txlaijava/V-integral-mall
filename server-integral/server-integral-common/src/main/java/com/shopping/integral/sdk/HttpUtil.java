package com.shopping.integral.sdk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpUtil {
    public static String httpPost(String postUrl, Map<String, String> postHeaders, String postEntity) throws IOException {
        URL postURL = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        StringBuilder sbStr = new StringBuilder();
        if (postHeaders != null) {
            for (String pKey : postHeaders.keySet()) {
                httpURLConnection.setRequestProperty(pKey, postHeaders.get(pKey));
            }
        }
        if (postEntity != null) {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8"));
            out.println(postEntity);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection
                    .getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sbStr.append(inputLine);
            }
            in.close();
        }
        httpURLConnection.disconnect();
        return new String(sbStr.toString().getBytes(), "utf-8");
    }

    public static String sendGet(String url){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);

            URLConnection connection = realUrl.openConnection();
            //10秒超时
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}