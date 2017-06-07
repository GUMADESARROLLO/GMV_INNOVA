package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Pedidos;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 17/03/2017.
 */

public class Respuesta_pedidos {
    private ArrayList<Pedidos> results;
    private int count;
    public  int getCount() {return count = results.size();}
    public ArrayList<Pedidos> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pedidos> results) {
        this.results = results;
    }

    public  Pedidos getpedido() {return results.get(0);}
}
