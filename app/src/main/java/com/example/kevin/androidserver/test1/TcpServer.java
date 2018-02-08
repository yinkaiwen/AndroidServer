package com.example.kevin.androidserver.test1;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by kevin on 2018/2/8.
 * https://github.com/yinkaiwen
 */

public class TcpServer extends IntentService{

    public TcpServer() {
        super(TcpServer.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
