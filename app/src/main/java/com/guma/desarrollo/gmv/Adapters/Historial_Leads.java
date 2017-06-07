package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.Historial;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Historial_Leads extends ArrayAdapter<Historial> {
    private AssetManager assetMgr;
    public Historial_Leads(Context context, List<Historial> objects) {
        super(context, 0, objects);
        assetMgr = context.getResources().getAssets();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_articulos_comprados,parent,false);
        }

         TextView Nombre = (TextView) convertView.findViewById(R.id.lst_Nombre);
         TextView Codigo = (TextView) convertView.findViewById(R.id.lst_Codigo);
         TextView Cantidad = (TextView) convertView.findViewById(R.id.lst_Cantidad);
         TextView Fecha = (TextView) convertView.findViewById(R.id.lst_Fecha);



        Historial lead = getItem(position);

         Codigo.setText("Codigo: " + lead.getmArticulo());
         Nombre.setText(lead.getmNombre());
         Cantidad.setText("Cantidad: " + lead.getmCantidad());
         Fecha.setText(lead.getmFecha());

        Nombre.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        Codigo.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));
        Cantidad.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));
        Fecha.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));
         return convertView;
    }

}
