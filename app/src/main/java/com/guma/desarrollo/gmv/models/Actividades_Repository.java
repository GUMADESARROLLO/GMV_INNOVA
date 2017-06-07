package com.guma.desarrollo.gmv.models;

import android.util.Log;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Activity.ReferenciasContexto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
/**
 * Created by luis.perez on 23/03/2017.
 */

public class Actividades_Repository {
    private static Actividades_Repository repository = new Actividades_Repository();
    private HashMap<String, Actividad> actividades = new HashMap<>();

    public static Actividades_Repository getInstance(){
        return repository;
    }

    private Actividades_Repository(){
        for (Actividad obj: Actividades_model.getActividades(ManagerURI.getDirDb(),ReferenciasContexto.getContextActividad())){
            saveActividad(new Actividad(obj.getmIdAE(), obj.getmCategoria(),obj.getmActividad()));
        }
//      private void saveLead(Articulo articulo) {articulos.put(articulo.getmCodigo(), articulo);   }

    }
    private void saveActividad(Actividad actividad){ actividades.put(actividad.getmIdAE(), actividad); }

}
