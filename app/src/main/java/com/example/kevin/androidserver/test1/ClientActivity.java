package com.example.kevin.androidserver.test1;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kevin.androidserver.R;
import com.example.kevin.androidserver.otherutils.StringFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2018/2/8.
 * https://github.com/yinkaiwen
 */

public class ClientActivity extends Activity implements View.OnClickListener {

    private static final int TIME_OUT = 10 * 1000;
    private static final String tag = ClientActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        bindView();
    }

    private void bindView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startService(new Intent(this, TcpServer.class));
//                codeFromNet();
                break;
            case R.id.btn2:
                startClient();
                break;
        }
    }

    private void startClient() {
        ExecutorService exe = Executors.newSingleThreadExecutor();
        exe.execute(new Runnable() {
            @Override
            public void run() {
                Socket client = null;
                try {
                    client = new Socket(getHost(), TcpServer.PORT);
                    client.setSoTimeout(TIME_OUT);

                    InputStream inputStream = client.getInputStream();
                    OutputStream outputStream = client.getOutputStream();

                    String msg = "Hello,Server.";
                    writeOutputStream(outputStream, msg);

                    byte[] buf = new byte[1024];
                    int len = inputStream.read(buf);
                    String result = new String(buf, 0, len, Charset.forName("UTF-8"));
                    Log.d(tag, String.format("client get result : %s", result));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (client != null) {
                            client.close();
                            client = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void writeOutputStream(OutputStream outputStream, String str) {
        try {
            outputStream.write(str.getBytes(Charset.forName("UTF-8")));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHost() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddressInt = wifiInfo.getIpAddress();
        return String.format(Locale.getDefault(), "%d.%d.%d.%d", (ipAddressInt & 0xff), (ipAddressInt >> 8 & 0xff), (ipAddressInt >> 16 & 0xff), (ipAddressInt >> 24 & 0xff));
    }

}
