package com.osh.chatting_bar_android;

import android.app.Application;
import android.content.SharedPreferences;

public class User extends Application {
    private static User instance;
    public static User getInstance()
    {
        if (instance == null)
        {
            synchronized(User.class)
            {
                instance = new User();
                instance.preferences = instance.getSharedPreferences("user", MODE_PRIVATE);
            }
        }
        return instance;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    private SharedPreferences preferences;
}
