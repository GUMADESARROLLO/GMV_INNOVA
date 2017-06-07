package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Clientes;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Respuesta_clientes {
    private ArrayList<Clientes> results;
    private int count;
    public int getCount() {
        return count = results.size();
    }
    public ArrayList<Clientes> getResults() {
        return results;
    }
}
