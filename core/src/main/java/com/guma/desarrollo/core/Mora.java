package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Mora {
    String mCliente;
    String mNombre;
    String mVencidos;
    String mD30;
    String mD60;
    String mD90;
    String mD120;
    String mMd120;

    public Mora(String mCliente, String mNombre, String mVencidos, String mD30, String mD60, String mD90, String mD120, String mMd120) {
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mVencidos = mVencidos;
        this.mD30 = mD30;
        this.mD60 = mD60;
        this.mD90 = mD90;
        this.mD120 = mD120;
        this.mMd120 = mMd120;
    }
    public Mora(){

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

    public String getmVencidos() {
        return mVencidos;
    }

    public void setmVencidos(String mVencidos) {
        this.mVencidos = mVencidos;
    }

    public String getmD30() {
        return mD30;
    }

    public void setmD30(String mD30) {
        this.mD30 = mD30;
    }

    public String getmD60() {
        return mD60;
    }

    public void setmD60(String mD60) {
        this.mD60 = mD60;
    }

    public String getmD90() {
        return mD90;
    }

    public void setmD90(String mD90) {
        this.mD90 = mD90;
    }

    public String getmD120() {
        return mD120;
    }

    public void setmD120(String mD120) {
        this.mD120 = mD120;
    }

    public String getmMd120() {
        return mMd120;
    }

    public void setmMd120(String mMd120) {
        this.mMd120 = mMd120;
    }

}
