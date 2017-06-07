package com.guma.desarrollo.gmv.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.Historial;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Facturas_Leads;
import com.guma.desarrollo.gmv.Adapters.Historial_Leads;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;

public class ComprasActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Historial> fList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Compras de Ults. 3 Meses");
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        listView = (ListView) findViewById(R.id.lstCompras);
        fList = new ArrayList<>();
        for (Historial articulo : Clientes_model.getHistorial(ManagerURI.getDirDb(), ComprasActivity.this,preferences.getString("ClsSelected",""))){
            fList.add(articulo);
        }
        listView.setAdapter(new Historial_Leads(this, fList));

    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
