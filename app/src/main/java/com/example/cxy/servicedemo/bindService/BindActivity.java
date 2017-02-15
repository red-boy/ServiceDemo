package com.example.cxy.servicedemo.bindService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cxy.servicedemo.R;

public class BindActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView numberText;
    private Button bindService, unbindService;

    private ServiceConnection mServiceConnection;
    private MybindService myService;
    private Intent intent;

    private ServiceCountBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;
    private int num;


    public class ServiceCountBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            num = bundle.getInt("serviceCount");
            Log.d("广播中接收到的数值", ":" + num);

            if (myService != null) {
                numberText.setText("数值是：" + num);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);

        numberText = (TextView) findViewById(R.id.bindServicenum);
        bindService = (Button) findViewById(R.id.bindService);
        unbindService = (Button) findViewById(R.id.unbindService);

        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        intent = new Intent(this, MybindService.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("onServiceConnected", "服务连接成功");

                MybindService.MyBinder binder = (MybindService.MyBinder) service;
                myService = binder.getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myService = null;
            }
        };

        //注册广播
        myBroadcastReceiver = new ServiceCountBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.cxy.servicedemo");
        registerReceiver(myBroadcastReceiver, intentFilter);

    }

    public void jumpToBindActivity(Context context) {
        Intent intent = new Intent(context, BindActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bindService:
                bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);//第三个参数标志位，flags则是指定绑定时是否自动创建Service。0代表不自动创建、BIND_AUTO_CREATE则代表自动创建。
                break;
            case R.id.unbindService:
                if (myService != null) {
                    myService = null;
                    unbindService(mServiceConnection);
                }
                break;
        }

    }
}
