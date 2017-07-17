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
import com.guma.desarrollo.gmv.Adapters.Lotes_Leads;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticulosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    private ListView listView,listViewLotes2;
    EditText Inputcant,InputExiste,InputPrecio,inputIva,InputDesc;
    RadioButton radioButton;
    Spinner spinner;
    Float vLinea,SubTotalLinea,TotalFinalLinea;
    private SearchView searchView;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private Articulo_Leads lbs;
    private Lotes_Leads lbl;
    private List<Articulo> objects;
    private List<Articulo> objects2;
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


        /*Toast.makeText(this, preferences.getString("GRUPO", ""), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, preferences.getString("LISTA", ""), Toast.LENGTH_SHORT).show();*/

        if (checked2) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                    final Articulo mnotes = (Articulo) parent.getItemAtPosition(position);

                    final String[] Reglas = Articulos_model.getDescuento(ArticulosActivity.this,preferences.getString("GRUPO", ""),mnotes.getmCodigo()).split(",");
                    LayoutInflater li = LayoutInflater.from(ArticulosActivity.this);

                    View promptsView = li.inflate(R.layout.input_cant, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArticulosActivity.this);

                    String lista = preferences.getString("LISTA", "");
                    String grupo = preferences.getString("GRUPO", "");
                    List<String> PrecDescuento;
                    if (!lista.equals("")) {
                       PrecDescuento = Articulos_model.getPrecioIva(ArticulosActivity.this, grupo, lista, mnotes.getmCodigo(),false);
                    }else{
                       PrecDescuento = Articulos_model.getPrecioIva(ArticulosActivity.this, grupo, lista, mnotes.getmCodigo(),true);
                    }
                    InputPrecio = (EditText) promptsView.findViewById(R.id.txtFrmPrecio);
                    InputPrecio.setText(PrecDescuento.get(0).toString());
                    String IVA =  Articulos_model.getIvaDescent(ArticulosActivity.this,grupo,lista,mnotes.getmCodigo());
                    alertDialogBuilder.setView(promptsView);


                    Inputcant = (EditText) promptsView.findViewById(R.id.txtFrmCantidad);

                    inputIva = (EditText) promptsView.findViewById(R.id.txtIva);
                    InputExiste = (EditText) promptsView.findViewById(R.id.txtFrmExistencia);
                    InputDesc = (EditText) promptsView.findViewById(R.id.txtDescuento);


                    InputPrecio.setEnabled(false);
                    InputExiste.setEnabled(false);
                    inputIva.setEnabled(false);
                    InputDesc.setEnabled(false);

                    inputIva.setText(IVA);
                    //spinner = (Spinner) promptsView.findViewById(R.id.sp_boni);
                    

                    InputExiste.setText(mnotes.getmExistencia() + " [ " + mnotes.getmUnidad() + " ]");


                    if (checked) {
                        Inputcant.setVisibility(View.GONE);
                        promptsView.findViewById(R.id.txtInCant).setVisibility(View.GONE);
                        /*TableRow tr = (TableRow)promptsView.findViewById(R.id.row2);
                        tr.setVisibility(View.GONE);*/
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
                            String descuento = "0";
                            if (s.length() != 0) {
                                if (Reglas.length > 0) {
                                    for (int i = 0; i < Reglas.length; i++) {
                                        String[] frag = Reglas[i].replace("+", ",").split(",");
                                        if (!frag[0].equals("")) {
                                            if (Integer.parseInt(frag[0]) > 0) {
                                                if (Integer.parseInt(Inputcant.getText().toString()) >= Integer.parseInt(frag[0])) {
                                                    descuento = frag[1];
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.d("", "afterTextChanged: descuento: "+descuento);
                                InputDesc.setText(descuento);
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

                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!checked) {
                                                Float Precio = Float.parseFloat(mnotes.getmExistencia());
                                                Float Exist = Float.parseFloat(mnotes.getmExistencia());
                                            if (!InputPrecio.getText().toString().equals("0.00")){
                                                if (Precio != 0.00) {
                                                    if (Inputcant.length() != 0 && !Inputcant.getText().toString().equals("0")) {

                                                        Log.d("", "onClickStri: " + Inputcant.getText().toString());
                                                        Float cantida = Float.parseFloat(Inputcant.getText().toString());
                                                        if (cantida <= Exist) {
                                                            //InputExiste.setText(InputPrecio);
                                                            vLinea = Float.parseFloat(InputPrecio.getText().toString()) * cantida;
                                                            SubTotalLinea = Float.parseFloat(String.valueOf(vLinea * 0.15));
                                                            TotalFinalLinea = vLinea + SubTotalLinea;
                                                            strings.add(mnotes.getmName());
                                                            strings.add(mnotes.getmCodigo());

                                                            strings.add(cantida.toString());
                                                            strings.add(vLinea.toString());
                                                            strings.add(SubTotalLinea.toString());
                                                            strings.add(TotalFinalLinea.toString());
                                                            strings.add(InputPrecio.getText().toString());
                                                            strings.add(inputIva.getText().toString());
                                                            strings.add(InputDesc.getText().toString());

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
                                            }else{
                                                new Notificaciones().Alert(ArticulosActivity.this, "ERROR", "ARTICULO SIN PRECIO, FAVOR ACTUALICE")
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
                                            dialog.dismiss();
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
        ArrayList<Articulo> newList = new ArrayList<>();
        if (query.isEmpty()){
            for(Articulo articulo:objects){
                newList.add(articulo);
            }
        }else{
            //ArrayList<Articulo> newList = new ArrayList<>();
            for(Articulo articulo:objects){
                if (articulo.getmName().toLowerCase().contains(query)){
                    newList.add(articulo);
                }
            }
            //listView.setAdapter(new Articulo_Leads(this, newList));
        }
        listView.setAdapter(new Articulo_Leads(this, newList));
    }
}
