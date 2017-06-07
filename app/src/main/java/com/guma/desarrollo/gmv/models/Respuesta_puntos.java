package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Facturas;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Respuesta_puntos {
    private ArrayList<Facturas> results;
    public int getCount() {
        return results.size();
    }
    public ArrayList<Facturas> getResults() {
        return results;
    }
}
