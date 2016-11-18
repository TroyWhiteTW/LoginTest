package com.example.troywhite.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }

    public void login() {
        new Thread() {
            @Override
            public void run() {
                doLogin2();
            }
        }.start();
    }

    public void doLogin() {
        try {
            MultipartUtility_v2 mu = new MultipartUtility_v2("http://mb.sm2.xyz");
//            mu.postActPw("luby7", "aa1111");
            mu.postKeyValue("username", "luby7");
            mu.postKeyValue("password", "111111");
            mu.postKeyValue("useragent", "mozilla/5.0 (windows nt 6.1; wow64; trident/7.0; slcc2; .net clr 2.0.50727; .net clr 3.5.30729; .net clr 3.0.30729; .net4.0c; .net4.0e; media center pc 6.0; infopath.3; rv:11.0) like gecko");
            String a = mu.getCookie();
            Log.i("troy", a);
            String[] b = a.split("; ");
            Log.i("troy", b[0]);
            mu.disconnect();

            MultipartUtility_v2 mu_2 = new MultipartUtility_v2("http://mb.sm2.xyz/mobile/test_cookie.php");
            mu_2.sendCookie(b[0]);
            List<String> ret = mu_2.getHtml();
            for (String line : ret) {
                Log.i("troy", line);
            }
        } catch (IOException e) {
            Log.i("troy", e.toString());
        }
    }

    public void doLogin2() {
        try {
            MultipartUtility_v2 mu = new MultipartUtility_v2("http://mb.sm2.xyz/ajax_login.php?action=LogApp");
//            mu.postActPw("luby7", "111111");
            mu.postKeyValue("username", "luby7");
            mu.postKeyValue("password", "111111");
            mu.postKeyValue("useragent", "mozilla/5.0 (windows nt 6.1; wow64; trident/7.0; slcc2; .net clr 2.0.50727; .net clr 3.5.30729; .net clr 3.0.30729; .net4.0c; .net4.0e; media center pc 6.0; infopath.3; rv:11.0) like gecko");
            String a = mu.getCookie();
            Log.i("troy", a);
            String[] b = a.split("; ");
            Log.i("troy", b[0]);
            List<String> ret = mu.getHtml();
            for (String line : ret) {
                Log.i("troy", line);
            }

            MultipartUtility_v2 mu_2 = new MultipartUtility_v2("http://mb.sm2.xyz/mobile/wap_ajax.php?action=app_head_data");
            mu_2.sendCookie(b[0]);
            List<String> ret_2 = mu_2.getHtml();
            for (String line : ret_2) {
                Log.i("troy", line);
            }
            String line = ret_2.get(0);
            JSONObject jo = new JSONObject(line);
            String v1 = jo.getString("username");
            Log.i("troy", v1);

            MultipartUtility_v2 mu_3 = new MultipartUtility_v2("http://mb.sm2.xyz/mobile/test_wagers_fail.php?type=3");
            mu_3.sendCookie(b[0]);
            JSONObject aa = mu_3.getJsonData();
            String v2 = aa.getJSONObject("list").getString("2738");
            Log.i("troy", v2);
        } catch (Exception e) {
            Log.i("troy", e.toString());
        }
    }

}
