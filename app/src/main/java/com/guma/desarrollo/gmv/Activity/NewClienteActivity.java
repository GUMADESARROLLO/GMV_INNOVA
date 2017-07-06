package com.guma.desarrollo.gmv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Notificaciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewClienteActivity extends AppCompatActivity {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    TextView nombre,ruc,municipio,telefono,direccion,correo;
    Spinner departamento;
    ArrayList<Clientes> mCliente = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__cliente);
        setTitle("NUEVO CLIENTE");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        nombre = (TextView)findViewById(R.id.txtNombre);
        ruc = (TextView)findViewById(R.id.txtRuc);
        departamento = (Spinner)findViewById(R.id.txtDepartamento);
        municipio = (TextView)findViewById(R.id.txtMunicipio);
        telefono = (TextView)findViewById(R.id.txtTelefono);
        direccion = (TextView)findViewById(R.id.txtDireccion);
        correo = (TextView)findViewById(R.id.txtCorreo);


        String[] plants = new String[]{
                "SELECCIONA UN DEPARTAMENTO...","MANAGUA","MASAYA","LEON","GRANADA",
                "CARAZO","ESTELI","RIVAS","CHINANDEGA","CHONTALES","MADRIZ","MATAGALPA",
                "NUEVA SEGOVIA","BOACO","RÍO SAN JUAN","JINOTEGA","ATLANTICO SUR","ATLANTICO NORTE"
        };
        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        departamento.setAdapter(spinnerArrayAdapter);

        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = (TextView)departamento.getSelectedView();
                String mdepartamento = textView.getText().toString();
                if(TextUtils.isEmpty(nombre.getText())|| nombre.getText().length()<5) {
                    new Notificaciones().snackieBar(NewClienteActivity.this,"Nombre invalido ó demasiado corto...", Color.RED, Color.WHITE, Color.YELLOW).show();
                }else if (mdepartamento.equals("SELECCIONA UN DEPARTAMENTO...")) {
                    new Notificaciones().snackieBar(NewClienteActivity.this,"Favor seleccione un departamento...", Color.RED, Color.WHITE, Color.YELLOW).show();
                }else if (TextUtils.isEmpty(telefono.getText())) {
                    new Notificaciones().snackieBar(NewClienteActivity.this,"Favor ingrese un telefono...", Color.RED, Color.WHITE, Color.YELLOW).show();
                }else if (TextUtils.isEmpty(direccion.getText())|| direccion.length()<10) {
                    new Notificaciones().snackieBar(NewClienteActivity.this,"Direccion invalida ó demasiada corta...", Color.RED, Color.WHITE, Color.YELLOW).show();
                }else{

                    Integer ID = Clientes_model.getIDtemporal(ManagerURI.getDirDb(), NewClienteActivity.this);


                    Log.d("", "alderekisde: "+"OBTENIDO ="+ID);
                    Clientes tmp = new Clientes();
                    tmp.setmCliente(ID.toString());
                    tmp.setmNombre(nombre.getText().toString());
                    tmp.setmRuc(ruc.getText().toString());
                    tmp.setmDepartamento(mdepartamento);
                    tmp.setmMunicipio(municipio.getText().toString());
                    tmp.setmTelefono(telefono.getText().toString());
                    tmp.setmCorreo(correo.getText().toString());
                    tmp.setmDireccion(direccion.getText().toString());
                    tmp.setmVendedor(preferences.getString("VENDEDOR", "00"));
                    mCliente.add(tmp);

                    ID++;
                    Clientes_model.SaveCliente(NewClienteActivity.this,mCliente,ID);
                    Toast.makeText(NewClienteActivity.this, "CLIENTE INGRESADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewClienteActivity.this);
            builder.setMessage("SE PERDERAN LOS DATOS DEL CLIENTE").setTitle("¿ESTA SEGURO?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(NewClienteActivity.this,AgendaActivity.class));
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
