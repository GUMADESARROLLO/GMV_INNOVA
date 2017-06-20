package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Cobros;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 17/03/2017.
 */

public class Respuesta_cobros {
    private ArrayList<Cobros> results;
    private int count;
    public  int getCount() {return count = results.size();}
    public ArrayList<Cobros> getResults() {
        return results;
    }
    public void setResults(ArrayList<Cobros> results) {
        this.results = results;
    }

    public  Cobros getpedido() {return results.get(0);}
}
