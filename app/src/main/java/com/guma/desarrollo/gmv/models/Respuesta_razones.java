package com.guma.desarrollo.gmv.models;


import com.guma.desarrollo.core.Razon;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 26/05/2017.
 */

public class Respuesta_razones {
    private ArrayList<Razon> results;
    private int count;
    public  int getCount() {return count = results.size();}
    public ArrayList<Razon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Razon> results) {
        this.results = results;
    }

    public  Razon getRazon() {return results.get(0);}
}
