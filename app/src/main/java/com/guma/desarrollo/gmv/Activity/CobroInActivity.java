package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.Funciones;
import com.guma.desarrollo.core.Mora;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CobroInActivity extends AppCompatActivity {
    EditText mImporte,mObservacion;
    TextView mSaldo,mLimite,m30,m60,m90,m120,md120,mTotal;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ArrayList<Cobros> mCobro = new ArrayList<>();
    String Usuario,mCliente;
    TextView textView;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        Usuario = preferences.getString("VENDEDOR","0");
        mCliente= preferences.getString("ClsSelected","0");

        textView = (TextView) findViewById(R.id.idTimer);
        timer = new Timer();
        
        mImporte = (EditText) findViewById(R.id.crbImporte);
        mObservacion = (EditText) findViewById(R.id.crbObservacion);

        mSaldo = (TextView) findViewById(R.id.txtmSaldo);
        mLimite = (TextView) findViewById(R.id.txtmLimite);
        m30 = (TextView) findViewById(R.id.txtm30);
        m60= (TextView) findViewById(R.id.txtm60);
        m90= (TextView) findViewById(R.id.txtm90);
        m120= (TextView) findViewById(R.id.txtm120);
        md120 = (TextView) findViewById(R.id.txtmd120);
        mTotal = (TextView) findViewById(R.id.txtmTotal);



        final Spinner spinner = (Spinner) findViewById(R.id.sp_transac);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"EFECTIVO","CHEQUE","TRANSFERENCIA"}));
        findViewById(R.id.btnSave_Cobro_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mImporte.getText()) || TextUtils.isEmpty(mObservacion.getText())){
                    new Notificaciones().snackieBar(CobroInActivity.this,"Hay Campos Vacios...", Color.RED, Color.WHITE, Color.YELLOW).show();
                }else{
                    int key = SQLiteHelper.getId(ManagerURI.getDirDb(),CobroInActivity.this,"COBROS");
                    String idCobro = Usuario+"-" + "C"+Clock.getIdDate()+String.valueOf(key);
                    Cobros tmp = new Cobros();

                    tmp.setmIdCobro(idCobro);
                    tmp.setmCliente(mCliente);
                    tmp.setmRuta(Usuario);
                    tmp.setmImporte(mImporte.getText().toString().trim());
                    tmp.setmTipo(spinner.getSelectedItem().toString());
                    tmp.setmObservacion(mObservacion.getText().toString().trim());
                    tmp.setmFecha(Clock.getNow());
                    mCobro.add(tmp);

                    Cobros_model.SaveCobro(CobroInActivity.this,mCobro);
                    editor.putString("FINAL",Clock.getTime()).apply();

                    Agenda_model.SaveLog(CobroInActivity.this,"COBRO","TIPO VISITA: COBRO: "+idCobro);
                    new Notificaciones().Alert(CobroInActivity.this,"COBRO","Informacion Guardada")
                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),CobroInActivity.this,"INSERT INTO VISITAS VALUES ('1','"+mCliente+"','" + Clock.getNow() + "', 'lati', 'Longi','S','','COBRO','0')");
                            timer.cancel();
                            editor.putString("BANDERA", "2").apply();
                            startActivity(new Intent(CobroInActivity.this,AccionesActivity.class));
                            finish();
                        }
                    }).show();
                }
            }
        });
        List<Clientes> obClientes = Clientes_model.getInfoCliente(ManagerURI.getDirDb(), CobroInActivity.this,preferences.getString("ClsSelected","0"));
        if (obClientes.size()>0){
            setTitle("[  PASO 3 - Cobro ]  " + obClientes.get(0).getmNombre());
            mSaldo.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obClientes.get(0).getmSaldo())));
            mLimite.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obClientes.get(0).getmCredito())));
        }
        List<Mora> obj = Clientes_model.getMora(ManagerURI.getDirDb(), CobroInActivity.this,preferences.getString("ClsSelected","0"));
        if (obj.size()>0){
            m30.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmD30())));
            m60.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmD60())));
            m90.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmD90())));
            m120.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmD120())));
            md120.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(obj.get(0).getmMd120())));
            String Final= String.valueOf(Float.parseFloat(obj.get(0).getmD30()) + Float.parseFloat(obj.get(0).getmD60()) + Float.parseFloat(obj.get(0).getmD90()) + Float.parseFloat(obj.get(0).getmD120())+Float.parseFloat(obj.get(0).getmMd120()));
            mTotal.setText("C$ " + Funciones.NumberFormat(Float.parseFloat(Final)));
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(CobroInActivity.this,AccionesActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


