package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Indicadores;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Respuesta_indicadores {
    private ArrayList<Indicadores> results;
    private int count;
    public  int getCount() {
        return count = results.size();
    }
    public ArrayList<Indicadores> getResults() {
        return results;
    }
}
