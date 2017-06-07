package com.guma.desarrollo.gmv.Activity;

import android.content.Context;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public class ReferenciasContexto {
    private static Context ContextArticulo,ContextClienteMora,ContextActividad;

    public static Context getContextClienteMora() {
        return ContextClienteMora;
    }
    public static void setContextClienteMora(Context contextClienteMora) {
        ContextClienteMora = contextClienteMora;
    }

    public static Context getContextArticulo() {
        return ContextArticulo;
    }

    public static void setContextArticulo(Context contextArticulo) {
        ContextArticulo = contextArticulo;
    }

    public static Context getContextActividad(){
        return ContextActividad;
    }
    public static void setContextActividad(Context contextActividad){
        ContextActividad=contextActividad;
    }
}
