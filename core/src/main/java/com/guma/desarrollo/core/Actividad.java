package com.guma.desarrollo.core;

/**
 * Created by luis.perez on 23/03/2017.
 */

public class Actividad {

    private String mIdAE;
    private String mCategoria;
    private String mActividad;

    public Actividad()
    {

    }

    public Actividad(String mIdAE, String mCategoria, String mActividad)
    {
        this.mIdAE=mIdAE;
        this.mCategoria=mCategoria;
        this.mActividad=mActividad;
    }

    public String getmIdAE() {
        return mIdAE;
    }

    public void setmIdAE(String mIdAE) {
        this.mIdAE = mIdAE;
    }

    public String getmCategoria() {
        return mCategoria;
    }

    public void setmCategoria(String mCategoria) {
        this.mCategoria = mCategoria;
    }

    public String getmActividad() {
        return mActividad;
    }

    public void setmActividad(String mActividad) {
        this.mActividad = mActividad;
    }

}
