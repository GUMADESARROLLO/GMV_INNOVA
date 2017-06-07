package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Clientes {
    String mCliente,mNombre,mDireccion,mRuc,mPuntos,mMoroso,mCredito,mSaldo,mDisponible,mCumple;
    int mMes;

    public Clientes(){

    }

    public Clientes(String mCliente, String mNombre, String mDireccion, String mRuc, String mPuntos, String mMoroso, String mCredito, String mSaldo, String mDisponible, String mCumple, int mMes) {
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mDireccion = mDireccion;
        this.mRuc = mRuc;
        this.mPuntos = mPuntos;
        this.mMoroso = mMoroso;
        this.mCredito = mCredito;
        this.mSaldo = mSaldo;
        this.mDisponible = mDisponible;
        this.mCumple = mCumple;
        this.mMes = mMes;
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

    public String getmDireccion() {
        return mDireccion;
    }

    public void setmDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public String getmRuc() {
        return mRuc;
    }

    public void setmRuc(String mRuc) {
        this.mRuc = mRuc;
    }

    public String getmPuntos() {
        return mPuntos;
    }

    public void setmPuntos(String mPuntos) {
        this.mPuntos = mPuntos;
    }

    public String getmMoroso() {
        return mMoroso;
    }

    public void setmMoroso(String mMoroso) {
        this.mMoroso = mMoroso;
    }

    public String getmCredito() {
        return mCredito;
    }

    public void setmCredito(String mCredito) {
        this.mCredito = mCredito;
    }

    public String getmSaldo() {
        return mSaldo;
    }

    public void setmSaldo(String mSaldo) {
        this.mSaldo = mSaldo;
    }

    public String getmDisponible() {
        return mDisponible;
    }

    public void setmDisponible(String mDisponible) {
        this.mDisponible = mDisponible;
    }

    public String getmCumple() {
        return mCumple;
    }

    public void setmCumple(String mCumple) {
        this.mCumple = mCumple;
    }

    public int getmMes() {
        return mMes;
    }

    public void setmMes(int mMes) {
        this.mMes = mMes;
    }
}
