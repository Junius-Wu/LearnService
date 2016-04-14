package com.example.jun.learnservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.btn_star_service)
    Button btnStarService;
    @InjectView(R.id.btn_stop_service)
    Button btnStopService;
    @InjectView(R.id.btn_bind_server)
    Button btnBindServer;
    @InjectView(R.id.btn_unbind_service)
    Button btnUnbindService;
    //ServiceConnection的匿名类 重写两个方法
    private ServiceConnection connection = new ServiceConnection() {
        private MyService.MyBinder myBinder;

        //当绑定服务时调用
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("connected");
            myBinder = (MyService.MyBinder) iBinder;
            myBinder.startDownload();//运行 create 并且还有自定义的download方法
        }

        //注意 只有在service因异常而断开连接的时候，这个方法才会用到 而不是解除绑定时 关闭程序也不会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("disConnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.btn_star_service, R.id.btn_stop_service, R.id.btn_bind_server, R.id.btn_unbind_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_star_service://开始服务
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.btn_stop_service://停止服务
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.btn_bind_server://绑定服务
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service://解除绑定
                unbindService(connection);
                break;
        }
    }

}
