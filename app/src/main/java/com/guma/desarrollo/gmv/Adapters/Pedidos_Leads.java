package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;

import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.Activity.AgendaActivity;
import com.guma.desarrollo.gmv.Activity.BandejaPedidosActivity;
import com.guma.desarrollo.gmv.Activity.PedidoActivity;
import com.guma.desarrollo.gmv.Activity.ResumenActivity;
import com.guma.desarrollo.gmv.R;

import java.util.List;
/**
 * Created by alder.hernandez on 22/03/2017.
 */

public class Pedidos_Leads extends ArrayAdapter<Pedidos>{
    private AssetManager assetMgr;
    private Context ontoy;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public Pedidos_Leads(Context context, List<Pedidos> objects) {
        super(context, 0, objects);
        assetMgr = context.getResources().getAssets();
        ontoy = context;
        editor = PreferenceManager.getDefaultSharedPreferences(ontoy).edit();
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_pedidos,parent,false);
        }

        TextView midPedido = (TextView) convertView.findViewById(R.id.txt_id_pedido);
        TextView mCliente = (TextView) convertView.findViewById(R.id.lst_cliente);
        TextView mFecha = (TextView) convertView.findViewById(R.id.lst_fecha);
        TextView mMonto = (TextView) convertView.findViewById(R.id.lst_total);
        TextView Editar = (TextView) convertView.findViewById(R.id.txtEditar);
        TextView Eliminar= (TextView) convertView.findViewById(R.id.txtEliminar);

        midPedido.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        mCliente.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        mMonto.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        mFecha.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));

        final Pedidos lead = getItem(position);

        mFecha.setText(lead.getmFecha());
        midPedido.setText(lead.getmIdPedido());
        mCliente.setText(lead.getmCliente()+" "+lead.getmNombre());
        mMonto.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(lead.getmPrecio())));

        ImageView img= (ImageView)convertView.findViewById(R.id.img);
        final Integer estado = Integer.valueOf(lead.getmEstado());
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


        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado.equals(0)) {
                    editor.putString("IDPEDIDO",lead.getmIdPedido());
                    editor.putString("CLIENTE",lead.getmNombre());
                    editor.putString("ClsSelected",lead.getmCliente());

                    List<Clientes> obj2 = Clientes_model.getListaGrupo(ManagerURI.getDirDb(),ontoy, lead.getmCliente());
                    List<Pedidos> comen = Pedidos_model.getComentario(ManagerURI.getDirDb(), ontoy,lead.getmIdPedido());
                    for(Pedidos obj1 : comen) {
                        editor.putString("COMENTARIO",obj1.getmComentario()).apply();
                    }
                    Log.d("", "onClick: alder "+obj2.size());
                    if (obj2.size()>0) {
                        if (lead.getmCliente().equals("CL008227")) {
                            editor.putString("LISTA", obj2.get(0).getmLista());
                            editor.putString("GRUPO", "CENTROLAC");
                        } else if (obj2.get(0).getmGrupo().equals("MAYORISTAS")) {
                            editor.putString("LISTA", obj2.get(0).getmLista());
                            editor.putString("GRUPO", obj2.get(0).getmGrupo());
                        } else {
                            editor.putString("LISTA", obj2.get(0).getmLista());
                            editor.putString("GRUPO", obj2.get(0).getmGrupo());
                        }
                    }

                    editor.apply();
                    getContext().startActivities(new Intent[]{new Intent(ontoy, PedidoActivity.class)});
                }
            }
        });
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ontoy);
                builder.setMessage("SE ELIMINARAN LOS DATOS DEL PEDIDO").setTitle("¿ESTA SEGURO?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(ontoy, "SE ELIMINO EL PEDIDO EKISDE", Toast.LENGTH_SHORT).show();
                                SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), ontoy,"DELETE FROM PEDIDO WHERE IDPEDIDO= '"+lead.getmIdPedido()+"'");
                                SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), ontoy,"DELETE FROM PEDIDO_DETALLE WHERE IDPEDIDO= '"+lead.getmIdPedido()+"'+ '"+lead.getmCliente()+"'");
                                Agenda_model.SaveLog(ontoy,"ELIMINACION","ELIMINACION PEDIDO: "+lead.getmIdPedido());
                                getContext().startActivities(new Intent[]{new Intent(ontoy, AgendaActivity.class)});

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).create().show();
            }
        });
        //img.setImageResource(R.drawable.uno2);
        //mEstado.setText("ESTADO:  " + lead.getmEstado());
        return convertView;
    }
}
