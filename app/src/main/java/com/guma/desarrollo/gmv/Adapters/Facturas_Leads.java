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
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Facturas_Leads extends ArrayAdapter<Facturas> {
    private AssetManager assetMgr;
    public Facturas_Leads(Context context, List<Facturas> objects) {
        super(context, 0, objects);
        assetMgr = context.getResources().getAssets();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_facturas_puntos,parent,false);
         }

         TextView Fecha = (TextView) convertView.findViewById(R.id.lst_fecha);
         TextView Factura = (TextView) convertView.findViewById(R.id.lst_factura);
         TextView Remanente = (TextView) convertView.findViewById(R.id.lst_puntos);
        Fecha.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        Factura.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        Remanente.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));

         Facturas lead = getItem(position);

         Factura.setText(lead.getmFactura());
         Fecha.setText(lead.getmFecha());
         Remanente.setText(Funciones.NumberFormat(Float.parseFloat(lead.getmRemanente())));
         return convertView;
    }
}
