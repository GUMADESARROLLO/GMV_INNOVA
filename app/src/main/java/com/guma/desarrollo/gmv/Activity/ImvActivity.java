package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.Imv;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.VentasCL_Leads;
import com.guma.desarrollo.gmv.Adapters.Ventas_Leads;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.List;

public class ImvActivity extends AppCompatActivity {
    TextView Clientes,TotalVenta,PromItem,PromFactura;
    ArrayList<Imv> ArryVentas,ArryClientes;
    ListView listView,lstClientes;
    float ttArticulos,ttClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("INDICADORES MOVIL");
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        Resources res = getResources();
        listView = (ListView) findViewById(R.id.lstVentas);
        lstClientes = (ListView) findViewById(R.id.lstClientes);
        ArryVentas = new ArrayList<>();
        ArryClientes = new ArrayList<>();

        Clientes = (TextView) findViewById(R.id.tcls);
        TotalVenta = (TextView) findViewById(R.id.tVnts);
        PromItem = (TextView) findViewById(R.id.pItem);
        PromFactura = (TextView) findViewById(R.id.pFactura);

        List<Imv> obj = Clientes_model.getImvVendedor(ManagerURI.getDirDb(), ImvActivity.this);
        List<Imv> obVentas = Clientes_model.getVntsArticulos(ManagerURI.getDirDb(), ImvActivity.this);
        List<Imv> obClientes = Clientes_model.getVntsClientes(ManagerURI.getDirDb(), ImvActivity.this);
        for (Imv bj:obVentas){
            ArryVentas.add(bj);
            ttArticulos += Float.parseFloat(bj.getmTotalVenta1());
        }
        for (Imv objCl:obClientes){
            ArryClientes.add(objCl);
            ttClientes+=Float.parseFloat(objCl.getmTotalVenta2());
        }


        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("TB1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("VENTAS POR ARTICULO",res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("TB2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("VENTAS POR CLIENTE ",res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);



        if (obj.size()>0){
            Clientes.setText(obj.get(0).getmNumCliente());
            TotalVenta.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmTotalVenta1())));
            PromItem.setText(obj.get(0).getmPromItem());
            PromFactura.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmPRomedioFactura())));
        }


        listView.setAdapter(new Ventas_Leads(this, ArryVentas));
        lstClientes.setAdapter(new VentasCL_Leads(this, ArryClientes));


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
