package com.mystreetprayer.app;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;


public class RunFirstInstance extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
