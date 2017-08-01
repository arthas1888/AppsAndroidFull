package com.example.a68.httpapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 68 on 15/07/2017.
 */

public class MyPreferences {

    public static final String MY_PREFERENCES = "my_pref";
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public MyPreferences(Context context) {
        sharedPreferences = context
                .getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void setUsername(String username){
        editor.putString("username", username);
        editor.apply();
    }

    public String getUsername(){
        return sharedPreferences.getString("username", "");
    }
}
