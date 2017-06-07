package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Articulo_Leads extends ArrayAdapter<Articulo> {
    public Articulo_Leads(Context context, List<Articulo> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item_articulos,parent,false);
        }

        TextView nombre = (TextView) convertView.findViewById(R.id.lst_nombre);
        TextView precio = (TextView) convertView.findViewById(R.id.lst_precio);
        TextView codigo = (TextView) convertView.findViewById(R.id.lst_codigo);

        Articulo lead = getItem(position);

        nombre.setText(lead.getmName());
        precio.setText(lead.getmPrecio());
        codigo.setText(lead.getmCodigo());


        return convertView;
    }
}
