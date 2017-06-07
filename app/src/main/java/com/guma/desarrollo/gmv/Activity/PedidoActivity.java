package com.guma.desarrollo.gmv.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PedidoActivity extends AppCompatActivity {
    private ListView listView;
    List<Map<String, Object>> list;
    TextView Total,txtCount,txtItemName,txtItemCant,txtItemCod,txtItemValor,txtBonificado,txtPrecio;
    EditText Inputcant,Exist;
    ArrayList<Pedidos> fList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Spinner spinner;


    TextView textView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listViewSettingConnect);
        list = new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle(preferences.getString("NameClsSelected"," --ERROR--"));
        Total = (TextView) findViewById(R.id.Total);
        txtCount= (TextView) findViewById(R.id.txtCountArti);
        timer = new Timer();
        textView = (TextView) findViewById(R.id.idTimer);





        String bandera = preferences.getString("BANDERA", "0");




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity.this);
                builder.setMessage("¿Desea Eliminar el Registro?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                list.remove(i);
                                Refresh();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create().show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
                showInputBox(parent,list,position);
                return true;
                //return  false;
            }
        });
        findViewById(R.id.txtSendPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()!=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity.this);
                    builder.setMessage("¿Confirma la transaccion?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent send = new Intent(PedidoActivity.this,ResumenActivity.class);
                                    send.putExtra("LIST", (Serializable) list);
                                    //editor.putString("COMENTARIO", txtComent.getText().toString()).apply();

                                    startActivity(send);
                                    timer.cancel();
                                    finish();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).create().show();

                }else{
                    new Notificaciones().Alert(PedidoActivity.this,"PEDIDO VACIO","INGRESE ARTICULOS AL PEDIDO...").setCancelable(false).setPositiveButton("OK", null).show();
                }
            }
        });

        String IdPedido = preferences.getString("IDPEDIDO", "");
        String cliente = preferences.getString("CLIENTE", "");
        if (!IdPedido.equals("")){
            setTitle("EDICION DE PEDIDO: "+IdPedido+" "+cliente);
            List<Pedidos> obj = Pedidos_model.getDetalle(ManagerURI.getDirDb(), PedidoActivity.this,IdPedido);
                fList = new ArrayList<>();
                for(Pedidos obj2 : obj) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ITEMNAME", obj2.getmDescripcion());
                    map.put("ITEMCODIGO", obj2.getmArticulo());
                    map.put("PRECIO", Funciones.NumberFormat(Float.parseFloat(obj2.getmPrecio().replace(",",""))));
                    //map.put("PRECIO", obj2.getmPrecio());
                    map.put("ITEMCANTI", obj2.getmCantidad());
                    map.put("BONIFICADO", obj2.getmBonificado());
                    //map.put("ITEMVALOR", Funciones.NumberFormat(Float.parseFloat(obj2.getmCantidad())*Float.parseFloat(obj2.getmPrecio())));
                    map.put("ITEMVALOR", Funciones.NumberFormat(Float.parseFloat(obj2.getmCantidad())*Float.parseFloat(obj2.getmPrecio().replace(",",""))));
                    //map.put("ITEMVALOR", obj2.getmCantidad());
                    list.add(map);
                }
                /*Agregar comentario*/
            List<Pedidos> comen = Pedidos_model.getComentario(ManagerURI.getDirDb(), PedidoActivity.this,IdPedido);
            for(Pedidos obj2 : comen) {
               // txtComent.setText(obj2.getmComentario());
            }
            Refresh();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PedidoActivity.this,ArticulosActivity.class),0);
            }
        });
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String IdPEDIDO = preferences.getString("IDPEDIDO", "");
            if (IdPEDIDO!="") {
                timer.cancel();
                LinearLayout mainLayout=(LinearLayout)findViewById(R.id.clockLayout);
                //mainLayout.setV   isibility(View.GONE);
            }else{
                textView.setText(Clock.getDiferencia(Clock.StringToDate(preferences.getString("iniTimer", "0000-00-00 00:00:00"), "yyyy-mm-dd HH:mm:ss"), Clock.StringToDate(Clock.getNow(), "yyyy-mm-dd HH:mm:ss"), "Timer"));
            }
        }
    };
    public void showInputBox(AdapterView<?> parent,final List<Map<String, Object>> list2, final int index){
        final Dialog dialogo = new Dialog(PedidoActivity.this);
        dialogo.setTitle(list2.get(index).get("ITEMNAME").toString());
        TextView textView = (TextView) dialogo.findViewById(android.R.id.title);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        dialogo.setContentView(R.layout.input_box);

        Inputcant = (EditText) dialogo.findViewById(R.id.txtFrmCantidad);
        Exist = (EditText) dialogo.findViewById(R.id.txtFrmExistencia);
        Inputcant.setText(list2.get(index).get("ITEMCANTI").toString().replace(".0",""));
        spinner = (Spinner) dialogo.findViewById(R.id.sp_boni);
        Button bt = (Button)dialogo.findViewById(R.id.btnDone);
        final Map<String, Object> map = new HashMap<>();
        map.put("ITEMNAME", list2.get(index).get("ITEMNAME").toString());
        map.put("ITEMCODIGO", list2.get(index).get("ITEMCODIGO").toString());
        map.put("PRECIO", list2.get(index).get("PRECIO").toString());

        List<String> Reglas = Articulos_model.getReglas(PedidoActivity.this, list2.get(index).get("ITEMCODIGO").toString());
        final String[] Reglas2 = Reglas.get(0).split(",");
        Exist.setText(Reglas.get(1).toString());
        List<String> mStrings = new ArrayList<>();
        mStrings.add(list2.get(index).get("BONIFICADO").toString());
        spinner.setAdapter(new ArrayAdapter<>(PedidoActivity.this, android.R.layout.simple_spinner_dropdown_item, mStrings));
        Inputcant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(PedidoActivity.this, "adadas", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                List<String> mStrings = new ArrayList<>();
                spinner.setAdapter(null);

                if (s.length() != 0) {
                    Log.d("", "alder: "+Reglas2[0]);
                    if (Reglas2.length >= 1) {
                        if (!Reglas2[0].equals("0")){
                            for (int i = 0; i < Reglas2.length; i++) {
                                String[] frag = Reglas2[i].replace("+", ",").split(",");
                                if (Integer.parseInt(Inputcant.getText().toString().replace(".","")) >= Integer.parseInt(frag[0])) {
                                    mStrings.add(frag[0] + "+" + frag[1]);
                                }
                            }
                        }
                    }else{
                        mStrings.add("0");
                    }
                    if (mStrings.size()==0){
                        mStrings.add("0");
                        spinner.setAdapter(null);
                    }
                    spinner.setAdapter(new ArrayAdapter<>(PedidoActivity.this, android.R.layout.simple_spinner_dropdown_item, mStrings));
                }
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena = Inputcant.getText().toString();

                if (cadena.equals("")) {
                    new Notificaciones().Alert(PedidoActivity.this,"AVISO","VALOR MINIMO ES 1").show();
                }else{
                    Float numero = Float.valueOf(Inputcant.getText().toString());
                    Float precio = Float.valueOf(list2.get(index).get("PRECIO").toString().replace(",","."));
                    if (numero>0) {
                        //map.put("BONIFICADO", list2.get(index).get("BONIFICADO").toString());
                        map.put("ITEMCANTI", Inputcant.getText().toString());
                        map.put("BONIFICADO", spinner.getSelectedItem().toString());

                        //map.put("ITEMVALOR", Float.parseFloat(list2.get(index).get("PRECIO").toString()) * numero);
                        map.put("ITEMVALOR", precio * numero);
                        list.add(index, map);
                        list.remove(index + 1);
                        Refresh();
                        dialogo.dismiss();
                    }
                }
            }
        });
        dialogo.show();
        Window window = dialogo.getWindow();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
    }
    public void Refresh(){
        float vLine = 0;
        listView.setAdapter(
                new SimpleAdapter(
                        this,
                        list,
                        R.layout.list_item_carrito, new String[] {"ITEMNAME", "ITEMCANTI","ITEMCODIGO","ITEMVALOR","BONIFICADO","PRECIO" },
                        new int[] {R.id.tvListItemName,R.id.Item_cant,R.id.item_codigo,R.id.Item_valor,R.id.tbListBonificado,R.id.tvListItemPrecio}));


        for (Map<String, Object> obj : list){
            //Log.d("carajo",obj.get("ITEMNAME").toString()+ " "+ obj.get("ITEMVALOR").toString());
            vLine     += Float.parseFloat(obj.get("ITEMVALOR").toString().replace(",",""));
        }
        Total.setText("TOTAL C$ "+ Funciones.NumberFormat(vLine));
        txtCount.setText(listView.getCount() +" Articulo");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedidos, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.accion_new) {
            LayoutInflater li = LayoutInflater.from(PedidoActivity.this);
            View promptsView = li.inflate(R.layout.input_observacion, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PedidoActivity.this);
            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",null)
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).create().show();



        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            Map<String, Object> map = new HashMap<>();
            map.put("ITEMNAME", data.getStringArrayListExtra("myItem").get(0));
            map.put("ITEMCODIGO", data.getStringArrayListExtra("myItem").get(1));
            map.put("ITEMCANTI",  data.getStringArrayListExtra("myItem").get(2));
            map.put("ITEMVALOR",  data.getStringArrayListExtra("myItem").get(3));
            map.put("ITEMSUBTOTAL", data.getStringArrayListExtra("myItem").get(4));
            map.put("ITEMVALORTOTAL", Funciones.NumberFormat(Float.parseFloat(data.getStringArrayListExtra("myItem").get(5))));
            map.put("BONIFICADO", data.getStringArrayListExtra("myItem").get(6));
            map.put("PRECIO", Funciones.NumberFormat(Float.parseFloat(data.getStringArrayListExtra("myItem").get(7))));

            list.add(map);
            Refresh();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //startActivity(new Intent(PedidoActivity.this,AgendaActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
