package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.Imv;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Ventas_Leads extends ArrayAdapter<Imv> {
    public Ventas_Leads(Context context, List<Imv> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         if (null == convertView) {
             convertView = inflater.inflate(R.layout.list_ventas,parent,false);
         }

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.artists_list_backgroundcolor);
        } else {
            convertView.setBackgroundResource(R.drawable.artists_list_background_alternate);
        }

         TextView Artic = (TextView) convertView.findViewById(R.id.idArticulo);
         TextView Descr = (TextView) convertView.findViewById(R.id.idDescrp);
         TextView venta = (TextView) convertView.findViewById(R.id.idVentas);
         TextView factu = (TextView) convertView.findViewById(R.id.idFacturas);

         Imv lead = getItem(position);

        Artic.setText(lead.getmArticulo());
        Descr.setText(lead.getmDescripcion());
        venta.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(lead.getmTotalVenta1())));
        factu.setText(lead.getmFactura());
         return convertView;
    }
}
