package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Agenda;
import com.guma.desarrollo.core.Articulo;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 21/04/2017.
 */

public class Respuesta_agenda {
    private ArrayList<Agenda> results;

    public int getCount() {
        return results.size();
    }
    public ArrayList<Agenda> getResults() {
        return results;
    }
}
