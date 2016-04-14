package com.example.jun.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    public  volatile boolean exit = false;//标识


    //自定义内部类Binder 可以调用startDownload方法
    class MyBinder extends Binder{
        public void startDownload () {
            System.out.println("DOWNLOADing.....");
        }
    }
    private MyBinder myBinder = new MyBinder();

    //返回自定义Binder
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    //启动或绑定服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate");
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (!exit) {//标识
                    System.out.println("服务正在运行");


                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();//线程开始

    }

    //每次启动服务时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //停止服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");
        exit = true;//标识来终止线程
    }


}
