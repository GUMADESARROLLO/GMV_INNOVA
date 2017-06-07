package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Usuario;

import java.util.ArrayList;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Respuesta_usuario {
    private static int count;
    private ArrayList<Usuario> results;

    public ArrayList<Usuario> getResults() {
        return results;
    }
    public  int getCount() {
        return count = results.size();
    }
    public void setResults(ArrayList<Usuario> results) {
        this.results = results;
    }
}
