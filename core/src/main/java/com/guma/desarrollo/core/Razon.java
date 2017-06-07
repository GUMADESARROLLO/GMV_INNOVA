package com.guma.desarrollo.core;

import android.view.animation.AccelerateInterpolator;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luis.perez on 28/03/2017.
 */

public class Razon {

    String mIdRazon;
    String mVendedor;
    String mCliente;

    String mNombre;
    String mFecha;
    String mObservacion;
    String mSend;
    ArrayList<RazonDetalle> rdet;

    JSONObject detalles = new JSONObject();
    ArrayList<HashMap<String, String>> contactList = null;

    public ArrayList<RazonDetalle> getRdet() {
        return rdet;
    }

    public void setRdet(ArrayList<RazonDetalle> rdet) {
        this.rdet = rdet;
    }



    public Razon(){}

    public Razon(String mIdRazon, String mVendedor, String mCliente, String mNombre, String mFecha, String mObservacion, String mSend, ArrayList<RazonDetalle> rdet, JSONObject detalles, ArrayList<HashMap<String, String>> contactList) {
        this.mIdRazon = mIdRazon;
        this.mVendedor = mVendedor;
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mFecha = mFecha;
        this.mObservacion = mObservacion;
        this.mSend = mSend;
        this.rdet = rdet;
        this.detalles = detalles;
        this.contactList = contactList;
    }

    public String getmIdRazon() {
        return mIdRazon;
    }

    public void setmIdRazon(String mIdRazon) {
        this.mIdRazon = mIdRazon;
    }

    public String getmVendedor() {
        return mVendedor;
    }

    public void setmVendedor(String mVendedor) {
        this.mVendedor = mVendedor;
    }

    public String getmCliente() {
        return mCliente;
    }

    public void setmCliente(String mCliente) {
        this.mCliente = mCliente;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getmObservacion() {
        return mObservacion;
    }

    public void setmObservacion(String mObservacion) {
        this.mObservacion = mObservacion;
    }

    public JSONObject getDetalles(){
        return detalles;
    }

    public void setDetalles(JSONObject detalles){
        this.detalles=detalles;
    }

    public String getmSend() {
        return mSend;
    }

    public void setmSend(String mSend) {
        this.mSend = mSend;
    }
}
