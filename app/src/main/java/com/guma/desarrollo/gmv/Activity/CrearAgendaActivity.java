package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;
import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Calendario;
import com.guma.desarrollo.gmv.api.Notificaciones;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrearAgendaActivity extends AppCompatActivity{
    private ExpandableListView simpleExpandableListView;
    private CustomAdapter listAdapter;
    private ArrayList<GroupInfo> deptList = new ArrayList<>();
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();
    List<Map<String, Object>> list,listSave,mDetalleAgenda,mTopAgenda;
    EditText mtVendedor,mRuta,mZONA,mSemanaIni,mSemanaEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle("PLAN DE TRABAJO");
        mtVendedor = (EditText) findViewById(R.id.txtIdVendedor);
        mRuta = (EditText) findViewById(R.id.txtRuta);
        mZONA= (EditText) findViewById(R.id.txtZona);
        mSemanaIni= (EditText) findViewById(R.id.startPlan);
        mSemanaEnd = (EditText) findViewById(R.id.endPlan);

        list = new ArrayList<>();
        listSave = new ArrayList<>();
        mDetalleAgenda= new ArrayList<>();
        mTopAgenda= new ArrayList<>();


        //mSemanaIni.setText(Clock.getTMD());
        //mSemanaEnd.setText(Clock.getTMD());


        final Calendario Cld1 = new Calendario();
        final Calendario Cld2 = new Calendario();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("", "onDateSet: SemanaIni " + mSemanaIni.getText() + " mSemanaEnd: " + mSemanaEnd.getText());
                //Log.d("", "Diferencia en Dias: " + Clock.getDiferencia(Clock.StringToDate(mSemanaIni.getText().toString()),Clock.StringToDate(mSemanaEnd.getText().toString()),"Dias"));
                     if (TextUtils.isEmpty(mZONA.getText())){
                    mZONA.setError("Campo Requerido");
                }else{
                    if (TextUtils.isEmpty(mSemanaIni.getText())){
                        mSemanaIni.setError("Campo Requerido");
                    }else{
                        if (TextUtils.isEmpty(mSemanaEnd.getText())){
                            mSemanaEnd.setError("Campo Requerido");
                        }else{
                            if (listSave.size()>0){
                                if ((Integer.parseInt(Clock.getDiferencia(Clock.StringToDate(mSemanaIni.getText().toString(),"yyyy-mm-dd"),Clock.StringToDate(mSemanaEnd.getText().toString(),"yyyy-mm-dd"),"Dias"))+1) > 5 ){
                                    new Notificaciones().Alert(CrearAgendaActivity.this,"ERROR!!!","No se permite un rago de mas de 5 dias...").setCancelable(false).setPositiveButton("OK", null).show();
                                }else{
                                    SaveAgenda();
                                }
                            }else{
                                Toast.makeText(CrearAgendaActivity.this, "Agenda Vacia.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });


        findViewById(R.id.startPlan).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Cld1.show(getSupportFragmentManager(), "datePicker");
                    Cld1.setFrm(1);
                    v.clearFocus();
                }

            }
        });
        findViewById(R.id.endPlan).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Cld2.show(getSupportFragmentManager(), "datePicker");
                    Cld2.setFrm(2);
                    v.clearFocus();
                }
            }
        });


        simpleExpandableListView = (ExpandableListView) findViewById(R.id.ExpListDias);
        listAdapter = new CustomAdapter(CrearAgendaActivity.this, deptList);
        simpleExpandableListView.setAdapter(listAdapter);

        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                startActivityForResult(new Intent(CrearAgendaActivity.this,ClientesActivity.class).putExtra("DIA",deptList.get(i).getName()),0);
                return false;
            }
        });

        loadData();
        expandAll();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            Map<String, Object> map = new HashMap<>();
            map.put("ITEMDIA", data.getStringArrayListExtra("myCliente").get(0));
            map.put("ITEMNAME", data.getStringArrayListExtra("myCliente").get(1));
            map.put("ITEMCODIGO", data.getStringArrayListExtra("myCliente").get(2));
            list.add(map);
            listSave.add(map);
            Refresh();
        }
    }


    private void Refresh() {
        for (int i=0;i < list.size();i++){
            addProduct(list.get(i).get("ITEMDIA").toString(),list.get(i).get("ITEMNAME").toString(),list.get(i).get("ITEMCODIGO").toString(),"N");
        }
        list.clear();
        expandAll();
    }
    private void SaveAgenda(){
        String mLUN ="",mMAR="",mMIE="",mJUE="",mVIE="";
        String[] strDias = getResources().getStringArray(R.array.dias);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapTop = new HashMap<>();
        for (Map<String, Object> obj : listSave){
            String mDia = obj.get("ITEMDIA").toString();
            switch (mDia){
                case "LUNES":
                    mLUN += obj.get("ITEMCODIGO").toString()+"-";
                break;
                case "MARTES":
                    mMAR += obj.get("ITEMCODIGO").toString()+"-";
                    break;
                case "MIERCOLES":
                    mMIE += obj.get("ITEMCODIGO").toString()+"-";
                    break;
                case "JUEVES":
                    mJUE += obj.get("ITEMCODIGO").toString()+"-";
                    break;
                case "VIERNES":
                    mVIE += obj.get("ITEMCODIGO").toString()+"-";
                    break;
            }

        }

        map.put(strDias[0], (mLUN.equals("")  ? "" : mLUN.substring(0,mLUN.length()-1)));
        map.put(strDias[1], (mMAR.equals("")  ? "" : mMAR.substring(0,mMAR.length()-1)));
        map.put(strDias[2], (mMIE.equals("")  ? "" : mMIE.substring(0,mMIE.length()-1)));
        map.put(strDias[3], (mJUE.equals("")  ? "" : mJUE.substring(0,mJUE.length()-1)));
        map.put(strDias[4], (mVIE.equals("")  ? "" : mVIE.substring(0,mVIE.length()-1)));
        mDetalleAgenda.add(map);

        mapTop.put("Vendedor",mtVendedor.getText());
        mapTop.put("mRuta",mRuta.getText());
        mapTop.put("mZona",mZONA.getText());
        mapTop.put("mSemanaIni",mSemanaIni.getText());
        mapTop.put("mSemanaEnd",mSemanaEnd.getText());
        mTopAgenda.add(mapTop);

        Log.d("", "SaveAgenda: Top " + mTopAgenda);
        Log.d("", "SaveAgenda: Detalles " + mDetalleAgenda);
        Agenda_model.SaveAgenda(CrearAgendaActivity.this,mTopAgenda,mDetalleAgenda);
        setResult(RESULT_OK, getIntent());
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
    private void expandAll() {
        for (int i = 0; i < listAdapter.getGroupCount(); i++){
            simpleExpandableListView.expandGroup(i);
        }
    }
    private void loadData(){
        String[] strDias = getResources().getStringArray(R.array.dias);
        for (int i=0;i<strDias.length;i++){
            initTabbla(strDias[i]);
        }
    }
    private int addProduct(String Grupo, String product,String Codigo,String Cumple){
        int groupPosition = 0;
        GroupInfo headerInfo = subjects.get(Grupo);

        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(Grupo);
            subjects.put(Grupo, headerInfo);
            deptList.add(headerInfo);
        }
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        int listSize = productList.size();
        listSize++;

        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        detailInfo.setCodigo(Codigo);
        detailInfo.setCumple(Cumple);
        productList.add(detailInfo);

        headerInfo.setProductList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
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

}
