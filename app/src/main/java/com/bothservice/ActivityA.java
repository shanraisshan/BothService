package com.bothservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityA extends AppCompatActivity {

    Button btnStartService,btnBindService,btnMethod,btnUnBind,btnChangeActivity;
    TextView outputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        //initialization
        btnStartService  = (Button)findViewById(R.id.btnStartService);
        btnBindService   = (Button)findViewById(R.id.btnBindService);
        btnMethod        = (Button)findViewById(R.id.btnMethod);
        btnUnBind        = (Button)findViewById(R.id.btnUnBind);
        btnChangeActivity= (Button)findViewById(R.id.btnChangeActivity);
        outputView       = (TextView)findViewById(R.id.txtView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Utility.isMyServiceRunning(LocalService.class, this)) {
            btnStartService.setEnabled(false);
            btnBindService.setEnabled(true);
        } else {
            btnStartService.setEnabled(true);
            btnBindService.setEnabled(false);
        }
        btnMethod.setEnabled(false);
        btnUnBind.setEnabled(false);
        btnChangeActivity.setEnabled(false);
    }

    //__________________________________________________________________________________________________ buttons on click listeners
    public void onClickBtnStartService(View view) {
        startService(new Intent(this, LocalService.class));
        btnStartService.setEnabled(false);
        btnBindService.setEnabled(true);
        Toast.makeText(ActivityA.this, "Service started indefinitely", Toast.LENGTH_SHORT).show();
    }

    public void onClickBtnBindService(View view) {
        doBindService();
        btnBindService.setEnabled(false);
        btnMethod.setEnabled(true);
    }

    public void onClickBtnMethod(View view) {
        //calling service method
        String output = mBoundService.callServiceMethod();
        outputView.setText(output);
        btnMethod.setEnabled(false);
        btnUnBind.setEnabled(true);
        Toast.makeText(ActivityA.this, "Service method called from Activity-A", Toast.LENGTH_SHORT).show();
    }

    public void onClickBtnUnBindService(View view) {
        doUnbindService();
        btnUnBind.setEnabled(false);
        btnChangeActivity.setEnabled(true);
    }

    public void onClickBtnChangeActivity(View view) {
        startActivity(new Intent(this, ActivityB.class));
        btnChangeActivity.setEnabled(false);
        btnBindService.setEnabled(true);
        outputView.setText("");
    }

//__________________________________________________________________________________________________
    private LocalService mBoundService;
    boolean mIsBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((LocalService.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            //Toast.makeText(ActivityA.this, "Service connected", Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            //Toast.makeText(ActivityA.this, "Service dis-connected", Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(ActivityA.this, LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
        Toast.makeText(ActivityA.this, "Service is binded with Activity A", Toast.LENGTH_SHORT).show();
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            Toast.makeText(ActivityA.this, "Service is un-binded with Activity A", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ActivityA", "OnDestroy");
        //if user try to clear the app without un-bind, activity instance will be leaked, therefore
        if(mIsBound)
            doUnbindService();
    }
}
