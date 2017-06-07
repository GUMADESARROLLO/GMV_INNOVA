package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Facturas {
    String mFecha;
    String mCliente;
    String mFactura;
    String mPuntos;
    String mRemanente;

    public Facturas(String mFecha, String mCliente, String mFactura, String mPuntos, String mRemanente) {
        this.mFecha = mFecha;
        this.mCliente = mCliente;
        this.mFactura = mFactura;
        this.mPuntos = mPuntos;
        this.mRemanente = mRemanente;
    }
    public Facturas(){

    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmCliente(String mCliente) {
        this.mCliente = mCliente;
    }

    public String getmFactura() {
        return mFactura;
    }

    public void setmFactura(String mFactura) {
        this.mFactura = mFactura;
    }

    public String getmPuntos() {
        return mPuntos;
    }

    public void setmPuntos(String mPuntos) {
        this.mPuntos = mPuntos;
    }

    public String getmRemanente() {
        return mRemanente;
    }

    public void setmRemanente(String mRemanente) {
        this.mRemanente = mRemanente;
    }
}
