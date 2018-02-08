package com.example.kevin.androidserver.test1;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kevin.androidserver.otherutils.StringFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by kevin on 2018/2/8.
 * https://github.com/yinkaiwen
 */

public class TcpServer extends IntentService {
    public static final int PORT = 8989;
    public static final int TIME_OUT = 15 * 1000;
    private static final String tag = TcpServer.class.getSimpleName();

    public TcpServer() {
        super(tag);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        startServer();
    }

    private void startServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(TIME_OUT);
            Socket client = serverSocket.accept();

            client.setSoTimeout(TIME_OUT);
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

            byte[] buf = new byte[1024];
            int len = inputStream.read(buf);
            String msgFromClient = new String(buf, 0, len, Charset.forName("UTF-8"));
            Log.d(tag, String.format("msg from client : %s", msgFromClient));

            String answer = "Server got this msg.";
            writeStringOutputStream(outputStream, answer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    serverSocket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeStringOutputStream(OutputStream outputStream, String str) {
        try {
            outputStream.write(str.getBytes(Charset.forName("UTF-8")));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
