package com.guma.desarrollo.gmv.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Clientes_Leads;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.R;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ClientesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    private ListView listView;
    private SearchView searchView;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private Clientes_Leads lbs;
    private List<Clientes> objects;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();
    private ArrayList<GroupInfo> deptList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle("CARTERA DE CLIENTES");
        ReferenciasContexto.setContextArticulo(this);
        listView = (ListView) findViewById(R.id.lstClientes);
        objects = Clientes_model.getClientes(ManagerURI.getDirDb(), ClientesActivity.this,"NOMBRE");
        lbs = new Clientes_Leads(this, objects);

        listView.setAdapter(lbs);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final ArrayList<String> strings = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Clientes mCLientes = (Clientes) adapterView.getItemAtPosition(i);
                strings.add(getIntent().getStringExtra("DIA"));
                strings.add(mCLientes.getmNombre());
                strings.add(mCLientes.getmCliente());

                editor.putString("ClsSelected",mCLientes.getmCliente());
                editor.putString("NameClsSelected",mCLientes.getmNombre());
                editor.apply();
                editor.putString("BANDERA", "0").apply();
                startActivity(new Intent(ClientesActivity.this,MarcarRegistroActivity.class));
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            startActivity(new Intent(ClientesActivity.this,AgendaActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }
    @Override
    public boolean onClose() {
        filterData("");
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterData(newText);
        return false;
    }
    public void filterData(String query) {
        query = query.toLowerCase(Locale.getDefault());

        if (query.isEmpty()){
            //lbs.addAll(objects);
        }else{
            ArrayList<Clientes> newList = new ArrayList<>();
            for(Clientes articulo:objects){
                if (articulo.getmNombre().toLowerCase().contains(query)){
                    newList.add(articulo);
                }
            }
            listView.setAdapter(new Clientes_Leads(this, newList));
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ClientesActivity.this,AgendaActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
