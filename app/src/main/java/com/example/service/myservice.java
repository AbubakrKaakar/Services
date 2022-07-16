package com.example.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class myservice extends Service {
    String message="Default";
    Looper looper;
    myHand handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread=new HandlerThread("servicehandlerthread", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        looper=thread.getLooper();
        handler=new myHand(looper);
//        Thread th=new Thread();
//        th.run();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //message=intent.getStringExtra("m1");
        String m1= intent.getStringExtra("m1");
        Message message=handler.obtainMessage(0,m1);
        message.arg1=startId;
        handler.sendMessage(message);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed!", Toast.LENGTH_SHORT).show();
    }
    public class myHand extends Handler{
        public myHand(Looper looper) {
          super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(myservice.this, "This is "+msg.obj, Toast.LENGTH_SHORT).show();

            }
            stopSelf(msg.arg1);
        }
    }
//    public class Thread extends java.lang.Thread {
//
//        @Override
//        public void run() {
//            Toast.makeText(myservice.this, message, Toast.LENGTH_SHORT).show();
//            stopSelf();
//        }
//    }
}
