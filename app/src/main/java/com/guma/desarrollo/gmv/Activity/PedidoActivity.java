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

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
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
    TextView Total,txtCount;
    EditText Inputcant,Exist,txtIVA,Descuento,InputDesc;
    ArrayList<Pedidos> fList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
            }
        });
        findViewById(R.id.txtSendPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()!=0){
                    Double Totall = Double.valueOf(Total.getText().toString().replace("TOTAL C$ ","").replace(",",""));
                    AlertDialog.Builder builder = new AlertDialog.Builder(PedidoActivity.this);
                        builder.setMessage("¿Confirma la transaccion?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent send = new Intent(PedidoActivity.this, ResumenActivity.class);
                                        send.putExtra("LIST", (Serializable) list);
                                        editor.putString("COMENTARIO", preferences.getString("COMENTARIO", "") + " " + txtCount.getText()).apply();
                                        startActivity(send);
                                        timer.cancel();
                                        finish();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create().show();
                    //}
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
                    map.put("ITEMCANTI", obj2.getmCantidad());
                    map.put("BONIFICADO", obj2.getmBonificado());
                    map.put("IVA", obj2.getmIva());
                    map.put("DESCUENTO", obj2.getmDescuento());
                    Float SubTotal = Float.parseFloat(obj2.getmCantidad())*Float.parseFloat(obj2.getmPrecio().replace(",",""));
                    Float iva = SubTotal * (Float.parseFloat( obj2.getmIva().replace(",",""))/100);
                    Float descuento = SubTotal * (Float.parseFloat( obj2.getmDescuento().replace(",",""))/100);
                    map.put("ITEMVALOR",(SubTotal + iva) - descuento);

                    list.add(map);
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
        txtIVA = (EditText) dialogo.findViewById(R.id.txtIva);
        InputDesc = (EditText) dialogo.findViewById(R.id.txtDescuento);

        Inputcant.setText(list2.get(index).get("ITEMCANTI").toString().replace(".0",""));
        InputDesc.setText(list2.get(index).get("DESCUENTO").toString());
        Button bt = (Button)dialogo.findViewById(R.id.btnDone);
        
        final Map<String, Object> map = new HashMap<>();
        map.put("ITEMNAME", list2.get(index).get("ITEMNAME").toString());
        map.put("ITEMCODIGO", list2.get(index).get("ITEMCODIGO").toString());
        map.put("PRECIO", list2.get(index).get("PRECIO").toString());

        String lista = preferences.getString("LISTA", "");
        String grupo = preferences.getString("GRUPO", "");

        final String[] Reglas = Articulos_model.getDescuento(PedidoActivity.this,grupo,list2.get(index).get("ITEMCODIGO").toString()).split(",");

        String IVA =  Articulos_model.getIvaDescent(PedidoActivity.this,grupo,lista,list2.get(index).get("ITEMCODIGO").toString());
        List<Articulo> obj2 = Articulos_model.getExistencia(PedidoActivity.this, list2.get(index).get("ITEMCODIGO").toString());

        Exist.setEnabled(false);
        txtIVA.setEnabled(false);
        InputDesc.setEnabled(false);


        txtIVA.setText(IVA);

        Exist.setText(obj2.get(0).getmExistencia()+ " [ " +obj2.get(0).getmUnidad()+ " ]");

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
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena = Inputcant.getText().toString();

                if (cadena.equals("") || (Integer.valueOf(cadena)<1)) {
                    new Notificaciones().Alert(PedidoActivity.this,"AVISO","VALOR MINIMO ES 1").show();
                }else{
                    Float numero = Float.valueOf(Inputcant.getText().toString());
                    Float precio = Float.valueOf(list2.get(index).get("PRECIO").toString().replace(",","."));
                    if (numero>0) {
                        map.put("ITEMCANTI", Inputcant.getText().toString());
                        map.put("IVA", txtIVA.getText().toString());
                        map.put("DESCUENTO", InputDesc.getText().toString());

                        Float SubTotal = precio * numero;
                        Float iva = SubTotal * (Float.parseFloat(txtIVA.getText().toString())/100);
                        Float descuento = SubTotal * (Float.parseFloat(InputDesc.getText().toString())/100);
                        map.put("ITEMVALOR", (SubTotal + iva) - descuento);


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
                            R.layout.list_item_carrito, new String[] {"ITEMNAME", "ITEMCANTI","ITEMCODIGO","ITEMVALOR",/*"BONIFICADO",*/"PRECIO","IVA","DESCUENTO"},
                            new int[] {R.id.tvListItemName,R.id.Item_cant,R.id.item_codigo,R.id.Item_valor,/*R.id.tbListBonificado,*/R.id.tvListItemPrecio,R.id.txtIva,R.id.txtDescuento}
                            )
                        );

        for (Map<String, Object> obj : list){
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
            final View promptsView = li.inflate(R.layout.input_observacion, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PedidoActivity.this);
            alertDialogBuilder.setView(promptsView);
            final TextView comentario = (TextView)promptsView.findViewById(R.id.txtObservaciones);
            comentario.setText(preferences.getString("COMENTARIO",""));
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor.putString("COMENTARIO",comentario.getText().toString()).apply();
                        }
                    })
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
            //map.put("ITEMVALOR",  data.getStringArrayListExtra("myItem").get(3));
            map.put("ITEMSUBTOTAL", data.getStringArrayListExtra("myItem").get(4));
            map.put("ITEMVALORTOTAL", Funciones.NumberFormat(Float.parseFloat(data.getStringArrayListExtra("myItem").get(5))));
            map.put("PRECIO", Funciones.NumberFormat(Float.parseFloat(data.getStringArrayListExtra("myItem").get(6))));
            map.put("IVA", data.getStringArrayListExtra("myItem").get(7));
            map.put("DESCUENTO", data.getStringArrayListExtra("myItem").get(8));

            Float SubTotal = Float.parseFloat(data.getStringArrayListExtra("myItem").get(3).replace(",",""));
            Float iva = SubTotal * (Float.parseFloat(data.getStringArrayListExtra("myItem").get(7).replace(",",""))/100);
            Float descuento = SubTotal * (Float.parseFloat(data.getStringArrayListExtra("myItem").get(8).replace(",",""))/100);
            map.put("ITEMVALOR",  (SubTotal + iva) - descuento);
            //vLine     += (SubTotal + iva) - descuento;

            list.add(map);
            Refresh();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PedidoActivity.this);
            builder.setMessage("SE PERDERAN LOS DATOS DEL PEDIDO").setTitle("¿ESTA SEGURO?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            editor.putString("NUEVOCL","0").apply();
                            editor.putString("COMENTARIO","").apply();
                            startActivity(new Intent(PedidoActivity.this,AgendaActivity.class));
                            finish();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
