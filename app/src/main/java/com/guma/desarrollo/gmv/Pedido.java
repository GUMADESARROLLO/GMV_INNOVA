package com.guma.desarrollo.gmv;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 24/02/2017.
 */

@IgnoreExtraProperties
public class Pedido {

    public String cliente;
    public String estado;
    public String idvendedor;
    public Pedido() {
    }

    public Pedido(String cliente, String estado, String idvendedor) {
        this.cliente = cliente;
        this.estado = estado;
        this.idvendedor = idvendedor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(String idvendedor) {
        this.idvendedor = idvendedor;
    }
}