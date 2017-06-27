package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.gmv.R;

import java.util.Timer;
import java.util.TimerTask;

public class AccionesActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    TextView txt1,txt2,txt3;
    TextView textView;
    Timer timer;
    private AssetManager assetMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        //editor.putString("BANDERA","1").apply();
        String bandera = preferences.getString("BANDERA", "0");
        setTitle(" [ PASO 2 - Acciones ] " +  preferences.getString("NameClsSelected"," --ERROR--"));

        txt1 = (TextView) findViewById(R.id.lbl1);
        txt2 = (TextView) findViewById(R.id.txtbtnCV);
        txt3 = (TextView) findViewById(R.id.lbl2);
        findViewById(R.id.btnCV).setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        assetMgr = getResources().getAssets();
        if (bandera.equals("1")){
            findViewById(R.id.btnCV).setVisibility(View.VISIBLE);
            findViewById(R.id.txtbtnCV).setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
        }if (bandera.equals("2")){
            findViewById(R.id.btnRZ).setVisibility(View.GONE);
            findViewById(R.id.lbl2).setVisibility(View.GONE);
            findViewById(R.id.txtbtnCV).setVisibility(View.VISIBLE);
            findViewById(R.id.btnCV).setVisibility(View.VISIBLE);
        }

        textView = (TextView) findViewById(R.id.idTimer);
        timer = new Timer();

        txt1.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        txt2.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        txt3.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));

        /*findViewById(R.id.btnCBR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("INICIO",Clock.getTime()).apply();
                startActivity(new Intent(AccionesActivity.this,CobroInActivity.class));
                finish();
            }
        });*/

        findViewById(R.id.btnPD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("IDPEDIDO","").apply();
                editor.putString("INICIO",Clock.getTime()).apply();
                editor.putBoolean("mostrar",true).apply();
                startActivity(new Intent(AccionesActivity.this,IndicadoresClienteActivity.class));
                finish();
            }
        });
        findViewById(R.id.btnRZ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("INICIO",Clock.getTime()).apply();
                startActivity(new Intent(AccionesActivity.this,RazonesActivity.class));
                finish();
            }
        });
        findViewById(R.id.btnCV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarPref();
                startActivity(new Intent(AccionesActivity.this,AgendaActivity.class));
                timer.cancel();
                finish();
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
            limpiarPref();
            timer.cancel();
            finish();
            startActivity(new Intent(AccionesActivity.this,AgendaActivity.class));
            return true;
        }
        return false;
    }
    public void limpiarPref(){
        editor.putString("LATITUD", "0.0");
        editor.putString("LONGITUD", "0.0");
        editor.putString("LUGAR_VISITA", "");
        editor.putString("BANDERA","0");
        editor.putString("INICIO","00:00:00");
        editor.putString("FINAL","00:00:00");
        editor.apply();
    }
}
