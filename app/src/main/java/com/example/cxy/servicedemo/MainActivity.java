package com.example.cxy.servicedemo;
/**
 * Service两种方式：开启服务、绑定服务
 * Service与BroadcastReceiver结合运用
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cxy.servicedemo.bindService.BindActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button starService, stoService, jumpActivity;
    private BindActivity mBindActivity = new BindActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starService = (Button) findViewById(R.id.startService);
        stoService = (Button) findViewById(R.id.stopService);
        jumpActivity = (Button) findViewById(R.id.jumpActivity);

        starService.setOnClickListener(this);
        stoService.setOnClickListener(this);
        jumpActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
            case R.id.startService:
                startService(intent);
                break;
            case R.id.stopService:
                stopService(intent);
                break;
            case R.id.jumpActivity:
                mBindActivity.jumpToBindActivity(MainActivity.this);
                break;
        }

    }
}
