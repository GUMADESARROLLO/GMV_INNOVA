package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Articulo;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public class Respuesta_articulos {
    private ArrayList<Articulo> results;

    public int getCount() {
        return results.size();
    }
    public ArrayList<Articulo> getResults() {
        return results;
    }
}
