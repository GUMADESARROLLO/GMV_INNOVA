package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 10/03/2017.
 */

public class Cobros {
    String mIdCobro;
    String mCliente;
    String mRuta;
    String mImporte;
    String mTipo;
    String mObservacion;
    String mFecha;

    public Cobros(){}

    public String getmIdCobro() {
        return mIdCobro;
    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public void setmIdCobro(String mIdCobro) {
        this.mIdCobro = mIdCobro;
    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmCliente(String mCliente) {
        this.mCliente = mCliente;
    }

    public String getmRuta() {
        return mRuta;
    }

    public void setmRuta(String mRuta) {
        this.mRuta = mRuta;
    }

    public String getmImporte() {
        return mImporte;
    }

    public void setmImporte(String mImporte) {
        this.mImporte = mImporte;
    }

    public String getmTipo() {
        return mTipo;
    }

    public void setmTipo(String mTipo) {
        this.mTipo = mTipo;
    }

    public String getmObservacion() {
        return mObservacion;
    }

    public void setmObservacion(String mObservacion) {
        this.mObservacion = mObservacion;
    }
}
