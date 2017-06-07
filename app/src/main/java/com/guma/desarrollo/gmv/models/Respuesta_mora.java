package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Mora;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Respuesta_mora {
    private ArrayList<Mora> results;
    private int count;
    public int getCount() {
        return count = results.size();
    }
    public ArrayList<Mora> getResults() {
        return results;
    }

}
