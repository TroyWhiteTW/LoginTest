package com.example.troywhite.logintest;

import org.json.JSONException;
import org.json.JSONObject;

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
        httpConn.setReadTimeout(5000);// 設置讀取超時為5秒
        httpConn.setConnectTimeout(10000);// 設置連接網路超時為10秒
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("POST");// 設置請求方法為POST
    }

    public void postActPw(String act, String pw) throws IOException {
        String data = "username=" + act +
                "&password=" + pw +
                "&useragent=mozilla/5.0 (windows nt 6.1; wow64; trident/7.0; slcc2; .net clr 2.0.50727; .net clr 3.5.30729; .net clr 3.0.30729; .net4.0c; .net4.0e; media center pc 6.0; infopath.3; rv:11.0) like gecko";
        outputStream = httpConn.getOutputStream();
        outputStream.write(data.getBytes());
        outputStream.flush();
    }

    public void postKeyValue(String key, String value) throws IOException {
        String data = "&" + key + "=" + value;
        outputStream = httpConn.getOutputStream();
        outputStream.write(data.getBytes());
        outputStream.flush();
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

    public JSONObject getJsonData() throws IOException, JSONException {
        String line = getHtml().get(0);
        JSONObject jo = new JSONObject(line);
        return jo;
    }

    public void disconnect() throws IOException {
        outputStream.close();
        httpConn.disconnect();
    }


}
