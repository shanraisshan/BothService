package com.bothservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Shayan Rais (http://shanraisshan.com)
 * created on 5/2/2016
 */
public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        doBindService(); //binding at onCreate
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService(); //UnBinding at onDestroy
    }

//__________________________________________________________________________________________________
    public void onClickBtnMethod(View view) {
        ((TextView)findViewById(R.id.txtView)).setText(mBoundService.callServiceMethod());
        Toast.makeText(ActivityB.this, "Service method called from Activity-B", Toast.LENGTH_SHORT).show();
    }


//__________________________________________________________________________________________________
    private LocalService mBoundService;
    boolean mIsBound = true;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((LocalService.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            //Toast.makeText(ActivityB.this, "Service connected", Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            //Toast.makeText(ActivityB.this, "Service dis-connected", Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(ActivityB.this, LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
        Toast.makeText(ActivityB.this, "Service is binded with Activity B", Toast.LENGTH_SHORT).show();
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            Toast.makeText(ActivityB.this, "Service is un-binded with Activity B", Toast.LENGTH_SHORT).show();
        }
    }

}
