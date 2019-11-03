package com.otopreneur.otopreneur_customer;

import android.app.Application;

import com.otopreneur.otopreneur_customer.Data.AppState;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppState.getInstance().initSharedPrefs(this);
    }
}
