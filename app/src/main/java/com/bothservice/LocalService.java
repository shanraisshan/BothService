package com.bothservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class LocalService extends Service {

    public LocalService() {
    }

    //START_STICKY for service to run continuously
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LocalService", "3-onStartCommand");
        Log.e("LocalService", "Received start id " + startId + ": " + intent);
        showNotification();
        return START_STICKY;
    }

    //note that it is just a notification (not a notification of both service)
    private Notification showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("BothService running indefinitely")
        .setSmallIcon(R.drawable.ic_launcher);
        Notification n = builder.build();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, n);
        return n;
    }

    //______________________________________________________________________________________________
    //@link http://developer.android.com/reference/android/app/Service.html#LocalServiceSample
    //binding the service
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();


    //______________________________________________________________________________________________
    //method to be called from multiple activities
    String callServiceMethod() {
        return "Success";
    }

}
