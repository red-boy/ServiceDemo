package com.example.cxy.servicedemo.bindService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by CXY on 2017/2/15.
 * 绑定服务
 */

public class MybindService extends Service {
    private MyBinder mMyBinder = new MyBinder();
    private int count;
    private boolean stop;//控制数值是否自增

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口，来调用Service的公共方法了
     */
    public class MyBinder extends Binder {
        MybindService getService() {
            return MybindService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("绑定Service", "onCreate");

        /**开启子线程进行数值定时自增*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    try {
                        Thread.sleep(1000);//每1秒数字自增
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("绑定Service", "onCreate的子线程中数值：" + count);
                    Intent intentService = new Intent();
                    intentService.putExtra("serviceCount", count);
                    intentService.setAction("com.example.cxy.servicedemo");
                    sendBroadcast(intentService);
                    count++;

                }

            }
        }).start();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void onDestroy() {
        Log.d("绑定Service", "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("绑定Service", "onUnbind");
        return super.onUnbind(intent);
    }
}
