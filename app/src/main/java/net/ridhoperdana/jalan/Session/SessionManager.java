package net.ridhoperdana.jalan.Session;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import net.ridhoperdana.jalan.activity.LogInActivity;
import net.ridhoperdana.jalan.activity.MainActivity;

import java.util.HashMap;

/**
 * Created by Luffi on 27/11/2016.
 */

public class SessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "mypreference";
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONGT = "longt";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(String email, String nama, String lat, String longt){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_NAME,nama);
        editor.putString(KEY_LAT, lat);
        editor.putString(KEY_LONGT, longt);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN,false);
    }

    public void setIsLogin(){
        editor.putBoolean(IS_LOGIN,true);
        editor.commit();
    }

    public void checkLogin(){
        if (this.isLoggedIn()){
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void getUserDetail(){
        HashMap<String ,String> user = new HashMap<>();
        user.put(KEY_NAME,preferences.getString(KEY_NAME,null));
        user.put(KEY_EMAIL,preferences.getString(KEY_EMAIL,null));
    }

    public Double getLatitude()
    {
        return Double.parseDouble(preferences.getString(KEY_LAT, null));
    }

    public Double getLongitude()
    {
        return Double.parseDouble(preferences.getString(KEY_LONGT, null));
    }

    public void setLatLongt(String lat, String longt)
    {
        editor.putString(KEY_LAT, lat);
        editor.putString(KEY_LONGT, longt);
        editor.commit();
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //go to halaman login
    }

}
