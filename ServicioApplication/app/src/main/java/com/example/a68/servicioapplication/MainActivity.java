package com.example.a68.servicioapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.a68.servicioapplication.services.MyService;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity
        implements MyBroadcast.BroadcastListener,
        View.OnClickListener {

    private TextView textResView;
    private MyBroadcast myBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonStart = (Button) findViewById(R.id.button_start);
        Button buttonStop = (Button) findViewById(R.id.button_stop);
        Button buttonCount = (Button) findViewById(R.id.button_count);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonCount.setOnClickListener(this);

        MyBroadcast.BroadcastListener broadcastListener = this;
        myBroadcast = new MyBroadcast(broadcastListener);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdMobActivity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //fab.setVisibility(View.GONE);
        textResView = (TextView) findViewById(R.id.text_res);

        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(MyService.MY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcast, intentFilter);

        bindMyService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcast);
        if (MyService.isInstance())
            unbindService(myConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMemmoryResult(String mem) {
        textResView.setText(mem);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        switch (view.getId()) {
            case R.id.button_start:
                if (!MyService.isInstance()) {
                    startService(intent);
                    bindMyService();
                }
                break;
            case R.id.button_stop:
                if (MyService.isInstance()) {
                    unbindService(myConnection);
                    stopService(intent);
                }
                break;
            case R.id.button_count:
                if (myService != null)
                    ((TextView) findViewById(R.id.textViewCount))
                            .setText("Contador: " + myService.getCount());
                break;
        }
    }

    private void bindMyService() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    MyService myService;
    public ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder iBinder) {
            Logger.d("Servicio conectado");
            MyService.MyBinder b = (MyService.MyBinder) iBinder;
            myService = b.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.d("Servicio desconectado");
            myService = null;
        }
    };
}
