package com.bothservice;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Shayan.Rais on 5/2/2016.
 */
public class Utility {

    //@link http://stackoverflow.com/a/5921190/4754141
    //utility method to check whether service is running or not
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context ctx) {
        ActivityManager manager = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
