package com.guma.desarrollo.core;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Usuario {
    private String mIdUser;
    private String mUsuario;
    private String mNombre;
    private String mRol;
    private String mPass;
    private String mCobro;
    private String mPedido;
    private String mRazon;

    public Usuario(String mIdUser, String mUsuario, String mNombre, String mRol, String mPass, String mCobro, String mPedido, String mRazon) {
        this.mIdUser = mIdUser;
        this.mUsuario = mUsuario;
        this.mNombre = mNombre;
        this.mRol = mRol;
        this.mPass = mPass;
        this.mCobro = mCobro;
        this.mPedido = mPedido;
        this.mRazon = mRazon;
    }

    public Usuario() {

    }
    public String getmIdUser() {
        return mIdUser;
    }

    public String getmUsuario() {
        return mUsuario;
    }

    public String getmNombre() {
        return mNombre;
    }

    public String getmRol() {
        return mRol;
    }

    public void setmUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public void setmRol(String mRol) {
        this.mRol = mRol;
    }
    public void setmIdUser(String mIdUser) {
        this.mIdUser = mIdUser;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }

    public String getmCobro() {
        return mCobro;
    }

    public void setmCobro(String mCobro) {
        this.mCobro = mCobro;
    }

    public String getmPedido() {
        return mPedido;
    }

    public void setmPedido(String mPedido) {
        this.mPedido = mPedido;
    }

    public String getmRazon() {
        return mRazon;
    }

    public void setmRazon(String mRazon) {
        this.mRazon = mRazon;
    }
}
