package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 27/04/2017.
 */

public class Historial {
    String mArticulo,mNombre,mCantidad,mFecha,mCliente,mVendedor;

    public Historial(String mArticulo, String mNombre, String mCantidad, String mFecha, String mCliente, String mVendedor) {
        this.mArticulo = mArticulo;
        this.mNombre = mNombre;
        this.mCantidad = mCantidad;
        this.mFecha = mFecha;
        this.mCliente = mCliente;
        this.mVendedor = mVendedor;
    }
    public Historial(){

    }

    public String getmArticulo() {
        return mArticulo;
    }

    public void setmArticulo(String mArticulo) {
        this.mArticulo = mArticulo;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmCantidad() {
        return mCantidad;
    }

    public void setmCantidad(String mCantidad) {
        this.mCantidad = mCantidad;
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

    public String getmVendedor() {
        return mVendedor;
    }

    public void setmVendedor(String mVendedor) {
        this.mVendedor = mVendedor;
    }
}
