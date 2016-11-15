package com.example.troywhite.logintest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TroyWhite on 2016/11/13.
 */

public class MultipartUtility_v2 {
    private HttpURLConnection httpConn;
    private OutputStream outputStream;

    MultipartUtility_v2(String requestURL) throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setReadTimeout(5000);// 设置读取超时为5秒
        httpConn.setConnectTimeout(10000);// 设置连接网络超时为10秒
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
    }

    public void postActPw(String act, String pw) throws IOException {
        httpConn.setRequestMethod("POST");// 设置请求方法为post
        outputStream = httpConn.getOutputStream();
        String data = "username=" + act + "&password=" + pw;
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void sendCookie(String cookie) {
        httpConn.setRequestProperty("Cookie", cookie);
    }

    public String getCookie() {
        return httpConn.getHeaderField("Set-Cookie");
    }

    public List<String> getHtml() throws IOException {
        List<String> response = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();
        return response;
    }

    public void disconnect() {
        httpConn.disconnect();
    }
}
