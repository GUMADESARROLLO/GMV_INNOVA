package com.guma.desarrollo.gmv.models;

import com.guma.desarrollo.core.Historial;
import com.guma.desarrollo.core.Imv;

import java.util.ArrayList;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Respuesta_imv {
    private ArrayList<Imv> results;
    private int count;
    public int getCount() {
        return count = results.size();
    }
    public ArrayList<Imv> getResults() {
        return results;
    }
}
