package com.youngwu.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView tv_text;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_test = (Button) findViewById(R.id.btn_test);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_test.setOnClickListener(this);

        okHttpClient = new OkHttpClient();
    }

    @Override
    public void onClick(View v) {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            click1("https://www.hao123.com/");
        }
    };

    private void click1(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                final String result = body.string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText(result);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void click2(String url) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    final String result = body.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_text.setText(result);
                        }
                    });
                }
            }
        });
    }

    private void click3(String url, String json) {
        try {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                final String result = responseBody.string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText(result);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void click4(String url, String json) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    final String result = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_text.setText(result);
                        }
                    });
                }
            }
        });
    }
}
