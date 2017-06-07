package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Razon;
import com.guma.desarrollo.core.RazonDetalle;
import com.guma.desarrollo.core.Razon_model;
import com.guma.desarrollo.core.Row;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.Adapters.CustomArrayAdapter;
import com.guma.desarrollo.gmv.Adapters.RazonesAdapter;
import com.guma.desarrollo.gmv.CategoriaInfo;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScrollingRazones extends AppCompatActivity {
    List<Row> rows;
    List<Actividad> datos;
    private ListView listView;
    private ArrayList<CategoriaInfo> deptList = new ArrayList<>();
    TextView textView,textView2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String CodCls,IdRazon;
    EditText etObservacion;
    Timer timer;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_razones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        final RazonesAdapter listAdapter;
        timer = new Timer();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle(preferences.getString("NameClsSelected"," --ERROR--"));
        btnSave = (Button) findViewById(R.id.btnSaveRazones);
        btnSave.setTypeface(Typeface.createFromAsset(getResources().getAssets() ,"fonts/roboto_medium.ttf"));
        CodCls =  preferences.getString("ClsSelected","");

        textView2 = (TextView) findViewById(R.id.idTimer);
        datos = Actividades_model.getActividades(ManagerURI.getDirDb(), ScrollingRazones.this);

        rows = new ArrayList<>(datos.size());
        for (int i = 0; i < datos.size(); i++) {
            Row row = new Row();
            row.setTitle(datos.get(i).getmActividad());
            row.setSubtitle(datos.get(i).getmCategoria());
            row.setSubsubtitle(datos.get(i).getmIdAE());
            row.setChecked(false);
            rows.add(row);
        }
        listView.setAdapter(new CustomArrayAdapter(this, rows));
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ListView lvv = (ListView)findViewById(R.id.list);
                int CantReg=0;
                for (int j=0;j<lvv.getAdapter().getCount();j++){
                    CheckBox tvvTest  = (CheckBox) lvv.getAdapter().getView(j,null,null).findViewById(R.id.checkBox);
                    if (tvvTest.isChecked())
                    {
                        CantReg++;
                    }
                }
                if (CantReg>0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingRazones.this);
                    builder.setMessage("Desea Guardar la Visita?")
                            .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ListView lv = (ListView)findViewById(R.id.list);
                                            int count = lv.getAdapter().getCount();

                                            Razon ra = new Razon();
                                            ArrayList<RazonDetalle> rd = new ArrayList<RazonDetalle>();
                                            int key = SQLiteHelper.getId(ManagerURI.getDirDb(), ScrollingRazones.this, "RAZON");
                                            IdRazon = preferences.getString("VENDEDOR", "00") + "R" + Clock.getIdDate() + String.valueOf(key);
                                            ra.setmIdRazon(IdRazon);
                                            ra.setmVendedor("F09");
                                            ra.setmCliente(preferences.getString("ClsSelected",""));
                                            ra.setmNombre(preferences.getString("NameClsSelected"," --ERROR--"));
                                            ra.setmFecha(Clock.getNow());
                                            ra.setmObservacion(etObservacion.getText().toString());
                                            ra.setmSend("0");

                                            for (int i = 0; i < lv.getAdapter().getCount(); i++)
                                            {
                                                TextView tvActividad = (TextView) lv.getAdapter().getView(i,null,null).findViewById(R.id.textViewTitle);
                                                TextView tvCategoria = (TextView) lv.getAdapter().getView(i,null,null).findViewById(R.id.textViewSubtitle);
                                                TextView tvIdAE = (TextView) lv.getAdapter().getView(i,null,null).findViewById(R.id.textViewSubSubtitle);
                                                CheckBox tvTest  = (CheckBox) lv.getAdapter().getView(i,null,null).findViewById(R.id.checkBox);

                                                if (tvTest.isChecked())
                                                {
                                                    rd.add(new RazonDetalle(IdRazon,tvIdAE.getText().toString(),tvActividad.getText().toString(),tvCategoria.getText().toString()));
                                                    ra.setRdet(rd);
                                                }
                                            }

                                            editor.putString("FINAL",Clock.getTime()).apply();
                                            Razon_model.SaveRazon(ScrollingRazones.this,ra);
                                            Agenda_model.SaveLog(ScrollingRazones.this,"RAZON","TIPO DE VISITA: RAZON: "+IdRazon);

                                            /*FIN GUARDAR*/
                                            startActivity(new Intent(ScrollingRazones.this,AccionesActivity.class));
                                            editor.putString("BANDERA","2").apply();
                                            finish();
                                        }
                                    }
                            ).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else
                {
                    new Notificaciones().Alert(ScrollingRazones.this,"Sin Actividades","Favor Seleccione al menos una Actividad como Razón de Visita...").setCancelable(false).setPositiveButton("OK", null).show();
                }

            }
        });
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(ScrollingRazones.this);
                View promptsView = li.inflate(R.layout.input_observacion, null);
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ScrollingRazones.this);
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
        });
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            textView2.setText(Clock.getDiferencia(Clock.StringToDate(preferences.getString("iniTimer", "0000-00-00 00:00:00"), "yyyy-mm-dd HH:mm:ss"), Clock.StringToDate(Clock.getNow(), "yyyy-mm-dd HH:mm:ss"), "Timer"));
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ScrollingRazones.this,AgendaActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
