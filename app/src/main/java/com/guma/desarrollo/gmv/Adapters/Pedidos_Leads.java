package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.Pedidos;

import com.guma.desarrollo.gmv.R;

import java.util.List;
/**
 * Created by alder.hernandez on 22/03/2017.
 */

public class Pedidos_Leads extends ArrayAdapter<Pedidos>{
    public Pedidos_Leads(Context context, List<Pedidos> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_pedidos,parent,false);
        }

        TextView midPedido = (TextView) convertView.findViewById(R.id.txt_id_pedido);
        TextView mFecha = (TextView) convertView.findViewById(R.id.lst_fecha);
        TextView mCliente = (TextView) convertView.findViewById(R.id.lst_cliente);
        TextView mMonto = (TextView) convertView.findViewById(R.id.lst_total);
        TextView mEstado = (TextView) convertView.findViewById(R.id.txt_id_estado);

        Pedidos lead = getItem(position);

        mFecha.setText(lead.getmFecha());
        midPedido.setText(lead.getmIdPedido());
        mCliente.setText(lead.getmCliente()+" "+lead.getmNombre());
        mMonto.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(lead.getmPrecio())));
        Log.d("estado",lead.getmEstado());
        ImageView img= (ImageView)convertView.findViewById(R.id.img);
        Integer estado = Integer.valueOf(lead.getmEstado());
        if (estado.equals(0)){
            img.setImageResource(R.drawable.uno2);
        }else if (estado.equals(1)){
            img.setImageResource(R.drawable.uno1);
        }else if (estado.equals(2)){
            img.setImageResource(R.drawable.doble2);
        }else if (estado.equals(3)){
            img.setImageResource(R.drawable.doble1);
        }else{
            img.setImageResource(R.drawable.icono_anulado_1);
        }
        //img.setImageResource(R.drawable.uno2);
        //mEstado.setText("ESTADO:  " + lead.getmEstado());
        return convertView;
    }
}
