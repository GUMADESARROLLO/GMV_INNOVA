package com.guma.desarrollo.core;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos {
    String mIdPedido,mVendedor,mCliente,mNombre,mFecha,mArticulo,mDescripcion,mCantidad,mPrecio,mBonificado,mEstado,mComentario,mAnulacion,mConfirmacion;

    JSONObject detalles = new JSONObject();
    ArrayList<HashMap<String, String>> contactList = null;


    public Pedidos(String mIdPedido, String mVendedor, String mCliente, String mNombre, String mFecha, String mArticulo, String mDescripcion, String mCantidad, String mPrecio, String mBonificado, String mEstado, String mComentario, String mAnulacion, String mConfirmacion, JSONObject detalles, ArrayList<HashMap<String, String>> contactList) {
        this.mIdPedido = mIdPedido;
        this.mVendedor = mVendedor;
        this.mCliente = mCliente;
        this.mNombre = mNombre;
        this.mFecha = mFecha;
        this.mArticulo = mArticulo;
        this.mDescripcion = mDescripcion;
        this.mCantidad = mCantidad;
        this.mPrecio = mPrecio;
        this.mBonificado = mBonificado;
        this.mEstado = mEstado;
        this.mComentario = mComentario;
        this.mAnulacion = mAnulacion;
        this.mConfirmacion = mConfirmacion;
        this.detalles = detalles;
        this.contactList = contactList;
    }

    public Pedidos(){ }

    public String getmIdPedido() {
        return mIdPedido;
    }

    public void setmIdPedido(String mIdPedido) {
        this.mIdPedido = mIdPedido;
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

    public String getmArticulo() {
        return mArticulo;
    }

    public void setmArticulo(String mArticulo) {
        this.mArticulo = mArticulo;
    }

    public String getmDescripcion() {
        return mDescripcion;
    }

    public void setmDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getmCantidad() {
        return mCantidad;
    }

    public void setmCantidad(String mCantidad) {
        this.mCantidad = mCantidad;
    }

    public String getmPrecio() {
        return mPrecio;
    }

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }

    public String getmBonificado() {
        return mBonificado;
    }

    public void setmBonificado(String mBonificado) {
        this.mBonificado = mBonificado;
    }

    public String getmEstado() {
        return mEstado;
    }

    public void setmEstado(String mEstado) {
        this.mEstado = mEstado;
    }

    public String getmComentario() {
        return mComentario;
    }

    public void setmComentario(String mComentario) {
        this.mComentario = mComentario;
    }

    public JSONObject getDetalles() {
        return detalles;
    }

    public void setDetalles(JSONObject detalles) {
        this.detalles = detalles;
    }

    public ArrayList<HashMap<String, String>> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<HashMap<String, String>> contactList) {
        this.contactList = contactList;
    }

    public String getmAnulacion() {
        return mAnulacion;
    }

    public void setmAnulacion(String mAnulacion) {
        this.mAnulacion = mAnulacion;
    }

    public String getmConfirmacion() {
        return mConfirmacion;
    }

    public void setmConfirmacion(String mConfirmacion) {
        this.mConfirmacion = mConfirmacion;
    }
}
