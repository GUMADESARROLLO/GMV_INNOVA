package com.guma.desarrollo.core;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luis.perez on 29/03/2017.
 */

public class RazonDetalle {

    String mIdRazon, mIdAE, mActividad, mCategoria;

    public RazonDetalle(){}
    public RazonDetalle(String IdRazon, String IdAE, String Actividad, String Categoria){
        mIdRazon=IdRazon;
        mIdAE=IdAE;
        mActividad=Actividad;
        mCategoria=Categoria;
    }
    public String getmIdRazon() {
        return mIdRazon;
    }

    public void setmIdRazon(String mIdRazon) {
        this.mIdRazon = mIdRazon;
    }

    public String getmIdAE() {
        return mIdAE;
    }

    public void setmIdAE(String mIdAE) {
        this.mIdAE = mIdAE;
    }

    public String getmActividad() {
        return mActividad;
    }

    public void setmActividad(String mActividad) {
        this.mActividad = mActividad;
    }

    public String getmCategoria() {
        return mCategoria;
    }

    public void setmCategoria(String mCategoria) {
        this.mCategoria = mCategoria;
    }

}
