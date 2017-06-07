package com.guma.desarrollo.gmv.api;

import com.guma.desarrollo.core.ManagerURI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class Class_retrofit {
    public static Retrofit Objfit() {
        return  new Retrofit.Builder().baseUrl(ManagerURI.getURL_Base()).client(ManagerURI.ConfigTimeOut()).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
