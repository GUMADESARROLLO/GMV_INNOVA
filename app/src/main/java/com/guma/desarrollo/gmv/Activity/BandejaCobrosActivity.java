package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.ServiceTestCase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Articulo;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.Facturas;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.Adapters.Cobros_Leads;
import com.guma.desarrollo.gmv.Adapters.Facturas_Leads;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.List;

public class BandejaCobrosActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Cobros> fList;
    TextView mTotal;
    float mCalTotal;

    TextView mCliente,mImporte,mFecha,mObservaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_cobros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("COBROS DEL DIA");
        listView = (ListView) findViewById(R.id.listView_cobros);
        fList = new ArrayList<>();
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        mTotal = (TextView) findViewById(R.id.txtTotalCobros);

        List<Cobros> lstObj = Cobros_model.getCobros(ManagerURI.getDirDb(), BandejaCobrosActivity.this,false);

        if (lstObj.size() == 0){
            findViewById(R.id.imgMoneyOff) .setVisibility(View.VISIBLE);
            findViewById(R.id.txtMoneyOff) .setVisibility(View.VISIBLE);
        }

        for(Cobros obj : lstObj) {
            fList.add(obj);
            mCalTotal += Float.parseFloat(obj.getmImporte());
        }
        Log.d("", "onListaCobro: " + Cobros_model.getCobros(ManagerURI.getDirDb(), BandejaCobrosActivity.this,false));
        mTotal.setText("C$ " + String.valueOf(mCalTotal));
        listView.setAdapter(new Cobros_Leads(this, fList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater li = LayoutInflater.from(BandejaCobrosActivity.this);
                final Cobros crbs = (Cobros) adapterView.getItemAtPosition(i);
                View promptsView = li.inflate(R.layout.info_cobro, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BandejaCobrosActivity.this);
                alertDialogBuilder.setView(promptsView);

                mCliente = (TextView) promptsView.findViewById(R.id.txtFrmCliente);
                mImporte = (TextView) promptsView.findViewById(R.id.txtFrmImporte);
                mFecha = (TextView) promptsView.findViewById(R.id.txtFrmFecha);
                mObservaciones = (TextView) promptsView.findViewById(R.id.txtFrmObservaciones);

                mCliente.setText(crbs.getmCliente());
                mImporte.setText(crbs.getmImporte());
                mFecha.setText(crbs.getmFecha());
                mObservaciones.setText(crbs.getmObservacion());


                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                }).create().show();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id = item.getItemId();
        if (id == 16908332){ finish(); }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
