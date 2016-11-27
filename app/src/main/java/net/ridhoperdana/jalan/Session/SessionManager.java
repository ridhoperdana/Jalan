package net.ridhoperdana.jalan.Session;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();

    }

    public void createLoginSession(String email, String nama){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_NAME,nama);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN,false);
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

    public void logout(){
        editor.clear();
        editor.commit();

        //go to halaman login
    }

}
