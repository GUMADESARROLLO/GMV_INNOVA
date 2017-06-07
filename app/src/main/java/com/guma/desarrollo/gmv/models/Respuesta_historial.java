package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Historial;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Respuesta_historial {
    private ArrayList<Historial> results;
    private int count;
    public int getCount() {
        return count = results.size();
    }
    public ArrayList<Historial> getResults() {
        return results;
    }
}
