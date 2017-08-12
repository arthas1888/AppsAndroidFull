package com.example.a68.servicioapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.a68.servicioapplication.services.MyService;

/**
 * Created by 68 on 12/08/2017.
 **/
public class MyBroadcast extends BroadcastReceiver {

    public interface BroadcastListener{
        void onMemmoryResult(String mem);
    }

    private BroadcastListener listener;
    public MyBroadcast(BroadcastListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(MyService.MY_ACTION)) {
            String mem = intent.getStringExtra("mem");
            if (listener != null){
                listener.onMemmoryResult(mem);
            }
            //
        }
    }
}