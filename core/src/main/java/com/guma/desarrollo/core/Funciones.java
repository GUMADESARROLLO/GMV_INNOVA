package com.guma.desarrollo.core;


import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by maryan.espinoza on 08/05/2017.
 */

public class Funciones {
    public static String NumberFormat(float Number){
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return  formatter.format(Number);
    }
}
