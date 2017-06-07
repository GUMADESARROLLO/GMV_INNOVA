package com.guma.desarrollo.core;

/**
 * Created by maryan.espinoza on 05/04/2017.
 */

public class Agenda {
    String mIdPlan,mVendedor,mRuta,mInicia,mTermina,mZona,mLunes,mMartes,mMiercoles,mJueves,mViernes;

    public Agenda(String mIdPlan, String mVendedor, String mRuta, String mInicia, String mTermina, String mZona, String mLunes, String mMartes, String mMiercoles, String mJueves, String mViernes) {
        this.mIdPlan = mIdPlan;
        this.mVendedor = mVendedor;
        this.mRuta = mRuta;
        this.mInicia = mInicia;
        this.mTermina = mTermina;
        this.mZona = mZona;
        this.mLunes = mLunes;
        this.mMartes = mMartes;
        this.mMiercoles = mMiercoles;
        this.mJueves = mJueves;
        this.mViernes = mViernes;
    }
    public Agenda(){

    }

    public String getmIdPlan() {
        return mIdPlan;
    }

    public void setmIdPlan(String mIdPlan) {
        this.mIdPlan = mIdPlan;
    }

    public String getmVendedor() {
        return mVendedor;
    }

    public void setmVendedor(String mVendedor) {
        this.mVendedor = mVendedor;
    }

    public String getmRuta() {
        return mRuta;
    }

    public void setmRuta(String mRuta) {
        this.mRuta = mRuta;
    }

    public String getmInicia() {
        return mInicia;
    }

    public void setmInicia(String mInicia) {
        this.mInicia = mInicia;
    }

    public String getmTermina() {
        return mTermina;
    }

    public void setmTermina(String mTermina) {
        this.mTermina = mTermina;
    }

    public String getmZona() {
        return mZona;
    }

    public void setmZona(String mZona) {
        this.mZona = mZona;
    }

    public String getmLunes() {
        return mLunes;
    }

    public void setmLunes(String mLunes) {
        this.mLunes = mLunes;
    }

    public String getmMartes() {
        return mMartes;
    }

    public void setmMartes(String mMartes) {
        this.mMartes = mMartes;
    }

    public String getmMiercoles() {
        return mMiercoles;
    }

    public void setmMiercoles(String mMiercoles) {
        this.mMiercoles = mMiercoles;
    }

    public String getmJueves() {
        return mJueves;
    }

    public void setmJueves(String mJueves) {
        this.mJueves = mJueves;
    }

    public String getmViernes() {
        return mViernes;
    }

    public void setmViernes(String mViernes) {
        this.mViernes = mViernes;
    }
}
