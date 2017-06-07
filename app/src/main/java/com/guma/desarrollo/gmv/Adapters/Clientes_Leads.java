package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 27/02/2017.
 */

public class Clientes_Leads extends ArrayAdapter<Clientes> {
    public Clientes_Leads(Context context, List<Clientes> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_clientes,parent,false);
        }

        TextView nombre = (TextView) convertView.findViewById(R.id.lst_nombre);
        TextView precio = (TextView) convertView.findViewById(R.id.lst_precio);
        TextView codigo = (TextView) convertView.findViewById(R.id.lst_codigo);

        Clientes lead = getItem(position);


        if (lead.getmMoroso().equals("S")){
            nombre.setText(lead.getmCliente().concat(" - ").concat(lead.getmNombre().concat(" [Moroso]")));
            nombre.setTextColor(convertView.getResources().getColor(R.color.button_danger));
        }else{
            nombre.setText(lead.getmCliente().concat(" - ").concat(lead.getmNombre()));
            nombre.setTextColor(convertView.getResources().getColor(R.color.black));
        }
        String str = "Limite: " + Funciones.NumberFormat(Float.parseFloat(lead.getmCredito())).concat(" Saldo: ").concat(Funciones.NumberFormat(Float.parseFloat(lead.getmSaldo()))).concat(" Disponible: ").concat(Funciones.NumberFormat(Float.parseFloat(lead.getmDisponible())));

        precio.setText(str);
        codigo.setText(lead.getmDireccion());


        return convertView;
    }
}
