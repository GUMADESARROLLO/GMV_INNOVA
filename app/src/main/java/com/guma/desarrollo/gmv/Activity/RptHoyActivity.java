package com.guma.desarrollo.gmv.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.Razon;
import com.guma.desarrollo.core.Razon_model;
import com.guma.desarrollo.gmv.Adapters.RazonesAdapter;
import com.guma.desarrollo.gmv.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class RptHoyActivity extends AppCompatActivity {
    TextView mPedido,mTotalPedido,mCobro,mTotalCobro,mOtros;
    float mCobroTotal,mPedidoTotal;
    int countCobro=0,countPedido=0,countOtros=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_hoy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle("REPORTE DEL DIA");


        for(Cobros obj : Cobros_model.getCobros(ManagerURI.getDirDb(), RptHoyActivity.this,true)) {
            mCobroTotal += Float.parseFloat(obj.getmImporte());
            countCobro++;
        }
        for(Pedidos obj : Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(), RptHoyActivity.this,true)) {
            mPedidoTotal += Float.parseFloat(obj.getmPrecio());
            countPedido++;
        }
        for(Razon obj : Razon_model.getInfoRazon(ManagerURI.getDirDb(), RptHoyActivity.this,true)) {
            countOtros++;
        }

        mPedido =(TextView) findViewById(R.id.txtRptPedido);
        mTotalPedido =(TextView) findViewById(R.id.txtRptPedidoMonto);
        mOtros = (TextView)findViewById(R.id.txtRptOtros);
        mCobro =(TextView) findViewById(R.id.txtRptCobro);

        mCobro.setText(Funciones.NumberFormat(countCobro));
        mTotalCobro =(TextView) findViewById(R.id.txtRptCobroMonto);
        mTotalCobro.setText("Equivalente a C$" + Funciones.NumberFormat(mCobroTotal));

        mPedido =(TextView) findViewById(R.id.txtRptPedido);
        mPedido.setText(String.valueOf(Funciones.NumberFormat(countPedido)));
        mTotalPedido =(TextView) findViewById(R.id.txtRptPedidoMonto);
        mTotalPedido.setText("Equivalente a C$" + Funciones.NumberFormat(mPedidoTotal));

        mOtros.setText(Funciones.NumberFormat(countOtros));
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
}
