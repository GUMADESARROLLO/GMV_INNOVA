package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.Indicadores;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class IndicadoresClienteActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private float TotalPuntos;


    TextView mpVenta,mItemFact,mLimite,mCredito,mPuntos,mHistorial;

    TextView textView,lbl_1,lbl_2,lbl_3,lbl_4,lbl_5,lbl_6;
    private AssetManager assetMgr;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        textView = (TextView) findViewById(R.id.idTimer);
        mpVenta = (TextView) findViewById(R.id.txtPromedio);
        mItemFact = (TextView) findViewById(R.id.txtCantItem);
        final Button btnOK = (Button) findViewById(R.id.btnIndicadores);
        timer = new Timer();
        assetMgr = getResources().getAssets();
        mLimite = (TextView) findViewById(R.id.txtLimite);
        mCredito = (TextView) findViewById(R.id.txtCredito);

        lbl_1 = (TextView) findViewById(R.id.lbl1);
        lbl_1.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        lbl_2 = (TextView) findViewById(R.id.lbl2);
        lbl_2.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        lbl_3 = (TextView) findViewById(R.id.lbl3);
        lbl_3.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        lbl_4 = (TextView) findViewById(R.id.lbl4);
        lbl_4.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        lbl_5 = (TextView) findViewById(R.id.lbl5);
        lbl_5.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));
        lbl_6 = (TextView) findViewById(R.id.lbl6);
        lbl_6.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light.ttf"));

        mPuntos = (TextView) findViewById(R.id.txtPuntos);
        mHistorial = (TextView) findViewById(R.id.txtHistorial);

        mHistorial.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));

        btnOK.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));


        List<Indicadores> obj = Clientes_model.getIndicadores(ManagerURI.getDirDb(), IndicadoresClienteActivity.this,preferences.getString("ClsSelected"," --ERROR--"));
        setTitle(" [ PASO 3 - Pedido ] " + preferences.getString("NameClsSelected"," --ERROR--"));

        if (obj.size()>0) {
            mpVenta.setText(Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmPromedioVenta3M())));
            mItemFact.setText(Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmCantidadItems3M())));

        }

        final List<Clientes> obClientes = Clientes_model.getInfoCliente(ManagerURI.getDirDb(), IndicadoresClienteActivity.this,preferences.getString("ClsSelected","0"));
        if (obClientes.size()>0){
            //setTitle("PASO 2 [ Cobro ] - " + obClientes.get(0).getmNombre());
            mCredito.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obClientes.get(0).getmCredito())));
            mLimite.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obClientes.get(0).getmDisponible())));


        }



        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obClientes.get(0).getmMoroso().equals("S")){
                    btnOK.setText("CLIENTE MOROSO");
                    btnOK.setBackgroundResource(R.drawable.button_danger_rounded);
                }else{

                    startActivity(new Intent(IndicadoresClienteActivity.this,PedidoActivity.class));
                    timer.cancel();
                    finish();
                }
            }
        });




        for(Facturas objFactura: Clientes_model.getFacturas(ManagerURI.getDirDb(),IndicadoresClienteActivity.this,preferences.getString("ClsSelected"," --ERROR--"))){
            TotalPuntos += Float.parseFloat(objFactura.getmRemanente());

        }

        mPuntos.setText(String.valueOf(Funciones.NumberFormat(TotalPuntos)));
        mPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndicadoresClienteActivity.this,FacturasActivity.class));
            }
        });

        mHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndicadoresClienteActivity.this,ComprasActivity.class));
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);

    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            textView.setText(Clock.getDiferencia(Clock.StringToDate(preferences.getString("iniTimer","0000-00-00 00:00:00"),"yyyy-mm-dd HH:mm:ss"),Clock.StringToDate(Clock.getNow(),"yyyy-mm-dd HH:mm:ss"),"Timer"));
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(IndicadoresClienteActivity.this,AccionesActivity.class));
            finish();
            return true;
        }
        return false;
    }
}
