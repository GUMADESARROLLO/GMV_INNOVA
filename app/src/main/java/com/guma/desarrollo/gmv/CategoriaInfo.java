package com.guma.desarrollo.gmv;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by luis.perez on 24/03/2017.
 */

public class CategoriaInfo {
    private String Categoria;
    private ArrayList<ActividadInfo> list = new ArrayList<>();

    public String getName() { return Categoria; }
    public void setName(String Categoria) { this.Categoria = Categoria; }

    public ArrayList<ActividadInfo> getActividadList(){
        return list;
    }

    public void setActividadesList(ArrayList<ActividadInfo> actividadesList){
        this.list = actividadesList;
    }

}
