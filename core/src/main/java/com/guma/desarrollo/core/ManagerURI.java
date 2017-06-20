package com.guma.desarrollo.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by maryan.espinoza on 29/12/2016.
 */

public class ManagerURI {



    //private static String SERVER = "192.168.1.94:8448";
    //private static String SERVER = "192.168.1.78:8448";
    private static String SERVER = "10.0.3.2:8080";
    //private static String SERVER = "165.98.75.219:8448";//SERVIDOR
    //private static String SERVER = "10.0.3.2:8080";
    //private static String SERVER = "165.98.75.219:8448";//SERVIDOR
    //private static String SERVER = "165.98.75.45:80";//SERVIDOR TIPITAPA
    //private static String SERVER = "192.168.1.155:8080";//LOCAL LP

    private static String URL_Base= "http://"+ SERVER + "/gmv_rest_innova/index.php/";
    private static String  DIR_DB = "/data/data/com.guma.desarrollo.gmvinnova/";
    public static String getURL_Base() {
        return URL_Base;
    }
    public static String getDirDb() {
        return DIR_DB;
    }
    public static OkHttpClient ConfigTimeOut(){
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        return okHttpClient;
    }

    public static boolean isOnlinea(Context cxt){
        ConnectivityManager cm = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

}
