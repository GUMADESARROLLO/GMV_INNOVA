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
    private String mPuntos;
    private String mReglas;
    private String mUnidadMedida;

    public Articulo(String mCodigo, String mName, String mExistencia, String mUnidad, String mPrecio, String mPuntos, String mReglas, String mUnidadMedida) {
        this.mCodigo = mCodigo;
        this.mName = mName;
        this.mExistencia = mExistencia;
        this.mUnidad = mUnidad;
        this.mPrecio = mPrecio;
        this.mPuntos = mPuntos;
        this.mReglas = mReglas;
        this.mUnidadMedida = mUnidadMedida;
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

    public String getmPuntos() {
        return mPuntos;
    }

    public void setmPuntos(String mPuntos) {
        this.mPuntos = mPuntos;
    }

    public String getmReglas() {
        return mReglas;
    }

    public void setmReglas(String mReglas) {
        this.mReglas = mReglas;
    }

    public String getmUnidadMedida() {
        return mUnidadMedida;
    }

    public void setmUnidadMedida(String mUnidadMedida) {
        this.mUnidadMedida = mUnidadMedida;
    }
}
