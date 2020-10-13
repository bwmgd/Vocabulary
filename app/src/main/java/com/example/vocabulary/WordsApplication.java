package com.example.vocabulary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class WordsApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
