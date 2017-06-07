package com.guma.desarrollo.gmv.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.guma.desarrollo.core.ManagerURI;

import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.core.Usuario;
import com.guma.desarrollo.core.Usuario_model;
import com.guma.desarrollo.gmv.R;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Notificaciones;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity  {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private Retrofit retrofit;
    public ProgressDialog pdialog;
    ArrayList<Usuario> mDetalleUser = new ArrayList<>();
    public EditText usuario;
    public EditText pass;
    public String useri;
    public String passw;
    //List<Usuario> Existe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        checked = preferences.getBoolean("pref", false);


        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = (EditText) findViewById(R.id.txtUsuerio);
                pass = (EditText)findViewById(R.id.password);
                if (TextUtils.isEmpty(usuario.getText())){
                    usuario.setError("CAMPO REQUERIDO");
                }else if(TextUtils.isEmpty(usuario.getText())){
                    pass.setError("CAMPO REQUERIDO");
                } else {
                    passw = pass.getText().toString();
                    useri = usuario.getText().toString();
                    pdialog = ProgressDialog.show(LoginActivity.this, "", "Procesando. Porfavor Espere...", true);
                    List<Usuario> Existe = Usuario_model.ValidarUser(useri,passw,LoginActivity.this);
                    if (Existe.size()>0){
                        Log.d("RESULTADOS",Existe.get(0).getmNombre());
                        editor.putString("VENDEDOR",Existe.get(0).getmUsuario());
                        editor.putString("NOMBRE",Existe.get(0).getmNombre());
                        editor.putString("USUARIO",Existe.get(0).getmIdUser());
                        editor.putBoolean("pref", !checked);
                        editor.apply();
                        pdialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
                        finish();
                    }else{
                        new TaskLogin().execute();
                    }

                }
            }
        });

        if (checked){
            startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
            finish();
        }
    }


    private class TaskLogin extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            Class_retrofit.Objfit().create(Servicio.class).obtenerListaUsuario(useri,passw).enqueue(new Callback<Respuesta_usuario>() {
                @Override
                public void onResponse(Call<Respuesta_usuario> call, Response<Respuesta_usuario> response) {
                    if(response.isSuccessful()){
                        Respuesta_usuario usuarioRespuesta = response.body();
                        editor.putString("VENDEDOR",usuarioRespuesta.getResults().get(0).getmUsuario());
                        editor.putString("NOMBRE",usuarioRespuesta.getResults().get(0).getmNombre());
                        editor.putString("USUARIO",usuarioRespuesta.getResults().get(0).getmIdUser());
                        editor.putString("ROL",usuarioRespuesta.getResults().get(0).getmRol());
                        editor.putBoolean("pref", !checked);

                        Usuario tmpUser = new Usuario();
                        tmpUser.setmIdUser(usuarioRespuesta.getResults().get(0).getmIdUser());
                        tmpUser.setmUsuario(usuarioRespuesta.getResults().get(0).getmUsuario());
                        tmpUser.setmNombre(usuarioRespuesta.getResults().get(0).getmNombre());
                        tmpUser.setmPass(usuarioRespuesta.getResults().get(0).getmPass());
                        tmpUser.setmRol(usuarioRespuesta.getResults().get(0).getmRol());
                        mDetalleUser.add(tmpUser);
                        Usuario_model.SaveUsuario(LoginActivity.this, mDetalleUser);

                        editor.apply();
                        pdialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,AgendaActivity.class));
                        finish();
                    }else{
                        new Notificaciones().Alert(LoginActivity.this,"ERROR","ERROR AL AUTENTIARSE, INTENTELO MAS TARDE")
                                .setCancelable(false).setPositiveButton("OK", null).show();
                        pdialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_usuario> call, Throwable t) {
                    new Notificaciones().Alert(LoginActivity.this,"ERROR",t.getMessage())
                            .setCancelable(false).setPositiveButton("OK", null).show();
                    pdialog.dismiss();
                }
            });
            return null;
        }
    }
}