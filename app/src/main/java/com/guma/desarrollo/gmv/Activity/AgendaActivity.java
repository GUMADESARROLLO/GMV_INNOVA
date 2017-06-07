package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.Razon_model;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.MyApplication;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.Tasks.TaskDownload;
import com.guma.desarrollo.gmv.Tasks.TaskUnload;
import com.guma.desarrollo.gmv.api.ConnectivityReceiver;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AgendaActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {


    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();
    private ArrayList<GroupInfo> deptList = new ArrayList<>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    private Menu menu;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;



    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadData();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        listAdapter = new CustomAdapter(AgendaActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);
        ReferenciasContexto.setContextArticulo(AgendaActivity.this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checked = preferences.getBoolean("pref",false);


        setTitle("Ultm. Actualizacion: " + preferences.getString("lstDownload","00/00/0000"));
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GroupInfo headerInfo = deptList.get(groupPosition);
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                editor.putString("ClsSelected",detailInfo.getCodigo());
                editor.putString("NameClsSelected",detailInfo.getName());
                editor.apply();
                editor.putString("BANDERA", "0").apply();
                startActivity(new Intent(AgendaActivity.this,MarcarRegistroActivity.class));
                finish();
                return false;
            }
        });

        findViewById(R.id.imgCump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgendaActivity.this,CumpleannoActivity.class));
            }
        });

        findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final CharSequence[]items = { "MIS CLIENTES","INVENTARIO","PEDIDO", "COBRO","ENVIAR","RECIBIR","REPORTE DEL DIA","CIERRE DEL DIA","ACERCA DE","SALIR"};
                new AlertDialog.Builder(v.getContext()).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (items[which].equals(items[0])){
                            startActivityForResult(new Intent(AgendaActivity.this,ClientesActivity.class),0);
                            finish();
                        }else{
                            if (items[which].equals(items[1])){
                                startActivity(new Intent(AgendaActivity.this,ArticulosActivity.class));
                                editor.putBoolean("mostrar", true).apply();
                                editor.putBoolean("menu", true).apply();
                            }else{
                                if (items[which].equals(items[2])){
                                    startActivity(new Intent(AgendaActivity.this,BandejaPedidosActivity.class));
                                }else{
                                    if (items[which].equals(items[3])){
                                        startActivity(new Intent(AgendaActivity.this,BandejaCobrosActivity.class));
                                    }else{
                                        if (items[which].equals(items[4])){
                                            new TaskUnload(AgendaActivity.this).execute();
                                        } else {
                                            if (items[which].equals(items[5])){
                                                if (ManagerURI.isOnlinea(AgendaActivity.this)){
                                                    new TaskDownload(AgendaActivity.this).execute(0);
                                                } else {
                                                    Toast.makeText(AgendaActivity.this, "No Posee Cobertura de datos...", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                if (items[which].equals(items[6])){
                                                    startActivity(new Intent(AgendaActivity.this,RptHoyActivity.class));
                                                } else{
                                                    if (items[which].equals(items[7])){
                                                       cerrar();
                                                    }else {
                                                        if (items[which].equals(items[8])) {

                                                            startActivity(new Intent(AgendaActivity.this,AcercadeActivity.class));
                                                        }else{
                                                            if (items[which].equals(items[9])){

                                                                checked = false;
                                                                editor.putBoolean("pref", false).commit();
                                                                editor.apply();
                                                                finish();
                                                            }else{
                                                                Toast.makeText(AgendaActivity.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }).create().show();
            }
        });
        expandAll();
       // AutoTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            loadData();
        }
    }

    private void cerrar(){
        //Agenda_model.borrar(AgendaActivity.this);
        Pedidos_model.borrar(AgendaActivity.this,ManagerURI.getDirDb());
        Cobros_model.borrar(AgendaActivity.this);
        Razon_model.borrar(AgendaActivity.this);
        new Notificaciones().Alert(AgendaActivity.this,"AVISO","CIERRE DEL DIA COMPLETO...").setCancelable(false).setPositiveButton("OK", null).show();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        menu.getItem(0).setIcon(isConnected ? getResources().getDrawable(R.drawable.btngreen) : getResources().getDrawable(R.drawable.btnred));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Ultm. Actualizacion: " + preferences.getString("lstDownload","00/00/0000"));
        expandAll();
        AutoTask(preferences.getBoolean("ntData",false));
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }
    private void blankAgenda(){
        String[] strDias = getResources().getStringArray(R.array.dias);
        for (int i=0;i<strDias.length;i++){
            initTabbla(strDias[i]);
        }
    }
    private int initTabbla(String Grupo){
        int groupPosition = 0;
        GroupInfo headerInfo = subjects.get(Grupo);
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(Grupo);
            subjects.put(Grupo, headerInfo);
            deptList.add(headerInfo);
        }
        headerInfo.setProductList(headerInfo.getProductList());
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
        this.menu = menu;
        return true;
    }
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    private void loadData(){

      /*  List<Map<String, Object>> lista = Agenda_model.getAgenda(ManagerURI.getDirDb(), AgendaActivity.this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        if (lista.size()>0) {
            editor.putString("IDPLAN", lista.get(0).get("IDPLAN").toString()).apply();
        }
        if (lista.size()>0){
            String[] strDias = getResources().getStringArray(R.array.dias);
            for (int i=0;i<strDias.length;i++){
                String[] mD = lista.get(0).get(strDias[i]).toString().split("-");
                for (int d=0;d<mD.length;d++){
                    if (mD[d].equals("")){
                        addProduct(0,strDias[i],"VACIO","");
                    }else{
                        for (Clientes obj : Clientes_model.getInfoCliente(ManagerURI.getDirDb(), AgendaActivity.this,mD[d])) {
                            if (onCake(obj.getmCumple())){
                                addProduct(R.drawable.ic_cake_black_24dp,strDias[i],obj.getmNombre(),mD[d]);
                            }else{
                                addProduct(0,strDias[i],obj.getmNombre(),mD[d]);
                            }
                        }
                    }
                }
            }
        }*/
        for (Clientes obj : Clientes_model.getClientes(ManagerURI.getDirDb(), AgendaActivity.this,"NOMBRE")) {
            addProduct(0,"MASTER CLIENTE",obj.getmNombre(),obj.getmCliente());
        }

       // Clientes_model.getClientes(ManagerURI.getDirDb(), AgendaActivity.this,"NOMBRE");
    }
    private  boolean onCake(String Fecha_cumple){
        boolean isVisible= false;
        if (Fecha_cumple.equals("00-00-0000")){

        }else{
            Date dte = new Date();
            String nowMes = Clock.getMes(dte,"M");

            String cumMes = String.valueOf(Integer.parseInt(Fecha_cumple.substring(3,5)));

            if (nowMes.equals(cumMes)){

                isVisible = true;
            }
        }
        return isVisible;
    }

    private int addProduct(int icon,String department, String product,String Codigo){
        int groupPosition = 0;
        GroupInfo headerInfo = subjects.get(department);


        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        int listSize = productList.size();
        listSize++;

        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        detailInfo.setCodigo(Codigo);
        detailInfo.setIcon(icon);
        productList.add(detailInfo);

        headerInfo.setProductList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
    private void AutoTask(boolean bln){

        if (bln){
            if (Integer.parseInt(Clock.getDiferencia(Clock.StringToDate(Clock.getNow(),"yyyy-mm-dd HH:mm:ss"),Clock.StringToDate(preferences.getString("lstDownload","00/00/0000"),"yyyy-mm-dd HH:mm:ss"),"Hrs")) >= 6){
                new TaskDownload(AgendaActivity.this).execute(0);
            }
            if (Integer.parseInt(Clock.getDiferencia(Clock.StringToDate(Clock.getNow(),"yyyy-mm-dd HH:mm:ss"),Clock.StringToDate(preferences.getString("lstUnload","00/00/0000"),"yyyy-mm-dd HH:mm:ss"),"Hrs")) >= 3){

                new TaskUnload(AgendaActivity.this).execute(0);
            }
        }else{
            new TaskDownload(AgendaActivity.this).execute(0);
            new TaskUnload(AgendaActivity.this).execute(0);
            loadData();
            editor.putBoolean("ntData", true);
            editor.apply();
        }
    }
    private double getLatency(String ipAddress){
        String pingCommand = "/system/bin/ping -c 10 " + ipAddress;
        String inputLine = "";
        double avgRtt;

        try {
            Process process = Runtime.getRuntime().exec(pingCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("avg")) {
                    break;
                }
                inputLine = bufferedReader.readLine();
            }

        }
        catch (Exception e){
            Log.d("", "Error: getLatency" + e.getMessage());
            e.printStackTrace();
        }



        String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
        avgRtt = Double.valueOf(strAvgRtt);
        return avgRtt;
    }

}