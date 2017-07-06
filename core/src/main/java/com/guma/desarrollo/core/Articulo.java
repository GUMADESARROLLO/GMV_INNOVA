package com.guma.desarrollo.core;

import java.util.UUID;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Articulo {
    private String mCodigo;
    private String mName;
    private String mExistencia;
    private String mUnidad;
    private String mPrecio;
    private String mReglas;
    private String mDescuento;
    private String mFecha;
    private String mClasificacion;
    private String mIva;
    private String mMinimo;
    private String mMaximo;
    private String mNlp1;
    private String mNlp2;
    private String mNlp3;
    private String mNlp4;


    public Articulo(String mCodigo, String mName, String mExistencia, String mUnidad, String mPrecio, String mReglas, String mDescuento, String mFecha, String mClasificacion, String mIva, String mMinimo, String mMaximo, String mNlp1, String mNlp2, String mNlp3, String mNlp4) {
        this.mCodigo = mCodigo;
        this.mName = mName;
        this.mExistencia = mExistencia;
        this.mUnidad = mUnidad;
        this.mPrecio = mPrecio;
        this.mReglas = mReglas;
        this.mDescuento = mDescuento;
        this.mFecha = mFecha;
        this.mClasificacion = mClasificacion;
        this.mIva = mIva;
        this.mMinimo = mMinimo;
        this.mMaximo = mMaximo;
        this.mNlp1 = mNlp1;
        this.mNlp2 = mNlp2;
        this.mNlp3 = mNlp3;
        this.mNlp4 = mNlp4;
    }

    public Articulo(){

    }

    public String getmCodigo() {
        return mCodigo;
    }

    public void setmCodigo(String mCodigo) {
        this.mCodigo = mCodigo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmExistencia() {
        return mExistencia;
    }

    public void setmExistencia(String mExistencia) {
        this.mExistencia = mExistencia;
    }

    public String getmUnidad() {
        return mUnidad;
    }

    public void setmUnidad(String mUnidad) {
        this.mUnidad = mUnidad;
    }

    public String getmPrecio() {
        return mPrecio;
    }

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }

    public String getmReglas() {
        return mReglas;
    }

    public void setmReglas(String mReglas) {
        this.mReglas = mReglas;
    }

    public String getmFecha() {
        return mFecha;
    }

    public void setmFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getmClasificacion() {
        return mClasificacion;
    }

    public void setmClasificacion(String mClasificacion) {
        this.mClasificacion = mClasificacion;
    }

    public String getmIva() {
        return mIva;
    }

    public void setmIva(String mIva) {
        this.mIva = mIva;
    }

    public String getmMinimo() {
        return mMinimo;
    }

    public void setmMinimo(String mMinimo) {
        this.mMinimo = mMinimo;
    }

    public String getmMaximo() {
        return mMaximo;
    }

    public void setmMaximo(String mMaximo) {
        this.mMaximo = mMaximo;
    }

    public String getmNlp1() {
        return mNlp1;
    }

    public void setmNlp1(String mNlp1) {
        this.mNlp1 = mNlp1;
    }

    public String getmNlp2() {
        return mNlp2;
    }

    public void setmNlp2(String mNlp2) {
        this.mNlp2 = mNlp2;
    }

    public String getmNlp3() {
        return mNlp3;
    }

    public void setmNlp3(String mNlp3) {
        this.mNlp3 = mNlp3;
    }

    public String getmNlp4() {
        return mNlp4;
    }

    public void setmNlp4(String mNlp4) {
        this.mNlp4 = mNlp4;
    }

    public String getmDescuento() {
        return mDescuento;
    }

    public void setmDescuento(String mDescuento) {
        this.mDescuento = mDescuento;
    }
}
