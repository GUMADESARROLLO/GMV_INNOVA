package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.Adapters.Pedidos_Leads;
import com.guma.desarrollo.gmv.Pedido;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.List;

public class BandejaPedidosActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Pedidos> fList;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    TextView mTotal;
    float mCalTotal;

    //AdapterPedidos adapterPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PEDIDOS DEL DIA");
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        mTotal = (TextView) findViewById(R.id.txtTotalPedidos);
        fList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView_pedidos);

        List<Pedidos> lstObj = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(), BandejaPedidosActivity.this,false);
        if (lstObj.size() == 0){
            new Notificaciones().Alert(BandejaPedidosActivity.this,"AVISO","NO HAY PEDIDOS...").setCancelable(false).setPositiveButton("OK", null).show();
        }else{
            for(Pedidos obj : lstObj) {
                fList.add(obj);
                mCalTotal += Float.parseFloat(obj.getmPrecio());
            }
            //Toast.makeText(this, fList.get(0).toString(), Toast.LENGTH_SHORT).show();
            mTotal.setText("C$ " + Funciones.NumberFormat(mCalTotal));
            listView.setAdapter(new Pedidos_Leads(this, fList));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater li = LayoutInflater.from(BandejaPedidosActivity.this);
                final Pedidos pedid = (Pedidos) adapterView.getItemAtPosition(i);
                String idPedido = ((Pedidos) adapterView.getItemAtPosition(i)).getmIdPedido();
                String Cliente = ((Pedidos) adapterView.getItemAtPosition(i)).getmNombre();
                String idCliente = ((Pedidos) adapterView.getItemAtPosition(i)).getmCliente();
                String ESTADO= ((Pedidos) adapterView.getItemAtPosition(i)).getmEstado();

                editor.putString("IDPEDIDO",idPedido);
                editor.putString("CLIENTE",Cliente);
                editor.putString("ClsSelected",idCliente);
                editor.apply();
                if (ESTADO.equals("0")) {
                    startActivity(new Intent(BandejaPedidosActivity.this, PedidoActivity.class));
                    finish();
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
