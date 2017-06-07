package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 07/03/2017.
 */

public class Cobros_Leads extends ArrayAdapter<Cobros> {
    public Cobros_Leads(Context context, List<Cobros> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_cobros,parent,false);
         }

         TextView Fecha = (TextView) convertView.findViewById(R.id.lst_fecha);
         TextView Factura = (TextView) convertView.findViewById(R.id.lst_factura);
         TextView Remanente = (TextView) convertView.findViewById(R.id.lst_puntos);
        TextView mMonto = (TextView) convertView.findViewById(R.id.lst_monto);

        Cobros lead = getItem(position);

         Factura.setText(lead.getmIdCobro());
         Fecha.setText(lead.getmFecha());
         Remanente.setText(lead.getmCliente());
         mMonto.setText("C$ " + lead.getmImporte());
         return convertView;
    }
}
