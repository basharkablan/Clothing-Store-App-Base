package com.basharkablan.finalproject;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class BroadcastService extends Service {

    private String LOG_TAG = null;
    private ArrayList<Item> data;

    public BroadcastService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LOG_TAG = this.getClass().getSimpleName();
        Log.i(LOG_TAG, "In onCreate");

        data = ItemXMLParser.parseItems(this,ItemXMLParser.MEN);
        data.addAll(ItemXMLParser.parseItems(this,ItemXMLParser.WOMEN));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "In onStartCommand");
        new Thread(new Runnable() {
            public void run() {
                for(Item item : data) {
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(MainActivity.mBroadcastItemAction);
                    broadcastIntent.putExtra("Data", item);
                    sendBroadcast(broadcastIntent);
                    Log.i(LOG_TAG, "Broadcast Sent");
                }
            }
        }).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Wont be called as service is not bound
        Log.i(LOG_TAG, "In onBind");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(LOG_TAG, "In onTaskRemoved");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }
}
