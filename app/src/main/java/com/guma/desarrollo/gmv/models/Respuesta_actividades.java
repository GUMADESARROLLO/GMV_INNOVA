package com.guma.desarrollo.gmv.models;


import com.guma.desarrollo.core.Actividad;

import java.util.ArrayList;

/**
 * Created by luis.perez on 23/03/2017.
 */

public class Respuesta_actividades {
    private ArrayList<Actividad> results;
    private int count;
    public int getCount(){
        return count = results.size();
    }
    public ArrayList<Actividad> getResults(){
        return results;
    }
}
