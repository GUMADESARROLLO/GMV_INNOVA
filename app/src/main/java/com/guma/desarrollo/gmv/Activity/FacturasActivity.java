package com.guma.desarrollo.gmv.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.SearchView;

import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Facturas_Leads;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.List;

public class FacturasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {

    private SearchView searchView;
    private MenuItem searchItem;
    private SearchManager searchManager;


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ListView listView;
    ArrayList<Facturas> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        setTitle(preferences.getString("NameClsSelected",""));
        listView = (ListView) findViewById(R.id.lstFacturas);
        fList = new ArrayList<>();
        for (Facturas articulo : Clientes_model.getFacturas(ManagerURI.getDirDb(), FacturasActivity.this,preferences.getString("ClsSelected",""))){
            fList.add(articulo);
        }
        listView.setAdapter(new Facturas_Leads(this, fList));
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
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
        if (query.isEmpty()){
            listView.setAdapter(new Facturas_Leads(this, fList));
        }else{
            ArrayList<Facturas> newList = new ArrayList<>();
            for(Facturas articulo:fList){
                if (articulo.getmFactura().toLowerCase().contains(query)){
                    newList.add(articulo);
                }
            }
            listView.setAdapter(new Facturas_Leads(this, newList));
        }
    }

}
