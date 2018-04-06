package com.quicksuntech.barcodedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Vishal on 6/23/2017.
 */
public class SharedPrefQRCode {

    SharedPreferences sharepreferences;
    public static SharedPrefQRCode instance = null;

    public static SharedPrefQRCode getInstance()
    {
        if (instance == null) {
            synchronized (SharedPrefQRCode.class) {
                instance = new SharedPrefQRCode();
            }
        }
        return instance;
    }


    public void saveISLogged_IN(Context context, Boolean isLoggedin) {
        sharepreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharepreferences.edit();
        editor.putBoolean("IS_LOGIN", isLoggedin);
        editor.commit();
    }

    public boolean getISLogged_IN(Context context) {
        sharepreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharepreferences.getBoolean("IS_LOGIN", false);
    }


    public void saveUserName(Context context, String username){
        sharepreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharepreferences.edit();
        editor.putString("User_Name", username);
        editor.commit();

    }

    public String getUserName(Context context){
        sharepreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharepreferences.getString("User_Name","");
    }

    public void savePassword(Context context, String pass){
        sharepreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharepreferences.edit();
        editor.putString("Password", pass);
        editor.commit();

    }

    public String getPassword(Context context){
        sharepreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharepreferences.getString("Password","");
    }


}
