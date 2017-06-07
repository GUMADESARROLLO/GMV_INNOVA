package com.guma.desarrollo.gmv;

import android.app.Application;

import com.guma.desarrollo.gmv.api.ConnectivityReceiver;

/**
 * Created by maryan.espinoza on 22/02/2017.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}