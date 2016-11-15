package com.example.troywhite.logintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
            }
        });

    }

    public void login() {
        new Thread() {
            @Override
            public void run() {
                doLogin();
            }
        }.start();
    }

    public void doLogin() {
        try {
            MultipartUtility_v2 mu = new MultipartUtility_v2("http://mb.sm2.xyz");
            mu.postActPw("luby7", "aa1111");
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

}
