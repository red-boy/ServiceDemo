package com.example.cxy.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by CXY on 2017/2/15.
 * 启动服务
 * 重写一些回调方法，以处Service理服务生命周期中的问题
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("Service练习","onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("Service练习","onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onDestroy练习","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
