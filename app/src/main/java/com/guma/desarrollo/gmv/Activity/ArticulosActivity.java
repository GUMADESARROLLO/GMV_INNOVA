package com.guma.desarrollo.gmv.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Articulo_Leads;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticulosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    private ListView listView;
    EditText Inputcant,InputExiste,InputPrecio;
    RadioButton radioButton;
    Spinner spinner;
    Float vLinea,SubTotalLinea,TotalFinalLinea;
    private SearchView searchView;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private Articulo_Leads lbs;
    private List<Articulo> objects;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked,checked2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ReferenciasContexto.setContextArticulo(ArticulosActivity.this);

        objects = Articulos_model.getArticulos(ManagerURI.getDirDb(), ReferenciasContexto.getContextArticulo());
        lbs = new Articulo_Leads(this, objects);
        listView.setAdapter(lbs);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        checked = preferences.getBoolean("menu", false);
        checked2 = preferences.getBoolean("mostrar", false);

        String IdPedido = preferences.getString("IDPEDIDO", "");

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        setTitle("ARTICULOS");
        final ArrayList<String> strings = new ArrayList<>();
        if (checked2) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                    final Articulo mnotes = (Articulo) parent.getItemAtPosition(position);
                    final String[] Reglas = mnotes.getmReglas().split(",");
                    //Toast.makeText(ArticulosActivity.this, mnotes.getmUnidadMedida(), Toast.LENGTH_SHORT).show();

                    LayoutInflater li = LayoutInflater.from(ArticulosActivity.this);

                    View promptsView = li.inflate(R.layout.input_cant, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArticulosActivity.this);

                    radioButton = (RadioButton)promptsView.findViewById(R.id.rbPresen);
                    radioButton.setText(mnotes.getmUnidad());
                    //Toast.makeText(ArticulosActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

                    alertDialogBuilder.setView(promptsView);


                    Inputcant = (EditText) promptsView.findViewById(R.id.txtFrmCantidad);
                    InputPrecio = (EditText) promptsView.findViewById(R.id.txtFrmPrecio);
                    InputPrecio.setText(mnotes.getmPrecio());
                    spinner = (Spinner) promptsView.findViewById(R.id.sp_boni);
                    //spinner.setAdapter(new ArrayAdapter<>(ArticulosActivity.this, android.R.layout.simple_spinner_dropdown_item,  Reglas));
                    InputExiste = (EditText) promptsView.findViewById(R.id.txtFrmExistencia);
                    InputExiste.setText(mnotes.getmExistencia() + " [ " + mnotes.getmUnidad() + " ]");

                    List<String> mStrings = new ArrayList<>();
                    for (int i = 0; i < Reglas.length; i++) {
                        mStrings.add(Reglas[i]);
                    }
                    if (checked) {
                        //List<String> mStrings = new ArrayList<>();
                        Inputcant.setVisibility(View.GONE);
                        promptsView.findViewById(R.id.txtInCant).setVisibility(View.GONE);
                        TableRow tr = (TableRow)promptsView.findViewById(R.id.row2);
                        tr.setVisibility(View.GONE);
                        /*for (int i = 0; i < Reglas.length; i++) {
                            mStrings.add(Reglas[i]);
                        }
                        spinner.setAdapter(new ArrayAdapter<>(ArticulosActivity.this, android.R.layout.simple_spinner_dropdown_item, mStrings));*/
                    }

                    Inputcant.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            List<String> mStrings = new ArrayList<>();
                            spinner.setAdapter(null);
                            if (s.length() != 0) {
                                Log.d("", "afterTextChanged: " + s.length() + " > " + Reglas.length);
                                if (Reglas.length > 0) {
                                    Log.d("", "afterTextChanged: entro");
                                    for (int i = 0; i < Reglas.length; i++) {
                                        String[] frag = Reglas[i].replace("+", ",").split(",");

                                        if (Integer.parseInt(frag[0]) > 0) {
                                            if (Integer.parseInt(Inputcant.getText().toString()) >= Integer.parseInt(frag[0])) {
                                                mStrings.add(frag[0] + "+" + frag[1]);
                                            }
                                        }
                                        spinner.setAdapter(new ArrayAdapter<>(ArticulosActivity.this, android.R.layout.simple_spinner_dropdown_item, mStrings));
                                    }
                                }
                            }
                        }
                    });
                    alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            // Prevent dialog close on back press button
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });
                    InputPrecio.setEnabled(false);
                    InputExiste.setEnabled(false);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!checked) {
                                                Float Precio = Float.parseFloat(mnotes.getmExistencia());
                                                Float Exist = Float.parseFloat(mnotes.getmExistencia());

                                                if (Precio != 0.0) {
                                                    if (Inputcant.length() != 0) {
                                                        Float cantida = Float.parseFloat(Inputcant.getText().toString());
                                                        if (cantida <= Exist) {
                                                            String BONIFICADO = "0";
                                                            if (spinner.getSelectedItem() != null) {
                                                                BONIFICADO = spinner.getSelectedItem().toString();
                                                            }if (radioButton.isChecked()){

                                                            }else{
                                                                Log.d("", "alder: "+mnotes.getmUnidadMedida());
                                                                //Toast.makeText(ArticulosActivity.this, mnotes.getmUnidadMedida(), Toast.LENGTH_SHORT).show();
                                                                cantida = cantida/Float.parseFloat(mnotes.getmUnidadMedida());
                                                            }
                                                            InputExiste.setText(mnotes.getmPrecio());
                                                            //vLinea = Float.parseFloat(mnotes.getmPrecio()) * Float.parseFloat(Inputcant.getText().toString());
                                                            vLinea = Float.parseFloat(mnotes.getmPrecio()) * cantida;
                                                            SubTotalLinea = Float.parseFloat(String.valueOf(vLinea * 0.15));
                                                            TotalFinalLinea = vLinea + SubTotalLinea;
                                                            strings.add(mnotes.getmName());
                                                            strings.add(mnotes.getmCodigo());
                                                            //strings.add(Inputcant.getText().toString());
                                                            strings.add(cantida.toString());
                                                            strings.add(vLinea.toString());
                                                            strings.add(SubTotalLinea.toString());
                                                            strings.add(TotalFinalLinea.toString());
                                                            strings.add(BONIFICADO);
                                                            strings.add(InputPrecio.getText().toString());
                                                            getIntent().putStringArrayListExtra("myItem", strings);
                                                            setResult(RESULT_OK, getIntent());
                                                            editor.putBoolean("menu", false).apply();
                                                            editor.putBoolean("mostrar", true).apply();
                                                            finish();
                                                        } else {
                                                            new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "LA EXISTENCIA ACTUAL ES: " + Exist.toString())
                                                                    .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                }
                                                            }).show();
                                                        }
                                                    } else {
                                                        new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "INGRESE UNA CANTIDAD POR FAVOR")
                                                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                            }
                                                        }).show();
                                                    }
                                                } else {
                                                    new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "ARTICULO SIN EXISTENCIA, FAVOR ACTUALICE")
                                                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                        }
                                                    }).show();


                                                }
                                            } else {
                                                dialog.cancel();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).create().show();
                }
            });
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            editor.putBoolean("menu", false).apply();
            editor.putBoolean("mostrar", true).apply();
            finish();
            return true;
        }
        return false;
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
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){
            editor.putBoolean("menu", false).apply();
            finish();
        }
        return super.onOptionsItemSelected(item);
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
            ArrayList<Articulo> newList = new ArrayList<>();
            for(Articulo articulo:objects){
                if (articulo.getmName().toLowerCase().contains(query)){
                    newList.add(articulo);
                }
            }
            listView.setAdapter(new Articulo_Leads(this, newList));
        }
    }
}
