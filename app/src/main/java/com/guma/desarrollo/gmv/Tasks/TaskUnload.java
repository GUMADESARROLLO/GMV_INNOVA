package com.guma.desarrollo.gmv.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guma.desarrollo.core.Agenda;
import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.Cobros;
import com.guma.desarrollo.core.Cobros_model;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.Razon;
import com.guma.desarrollo.core.Razon_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.core.Visitas;

import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Notificaciones;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_pedidos;
import com.guma.desarrollo.gmv.models.Respuesta_razones;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class TaskUnload extends AsyncTask<Integer,Integer,String> {
    public ProgressDialog pdialog;
    Context cnxt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public TaskUnload(Context cnxt) {
        this.cnxt = cnxt;
        preferences = PreferenceManager.getDefaultSharedPreferences(cnxt);
        editor = preferences.edit();
    }
    @Override
    protected void onPreExecute() {
        pdialog = ProgressDialog.show(cnxt, "","Iniciando...", true);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... para) {
        List<Cobros> obj = Cobros_model.getCobros(ManagerURI.getDirDb(), cnxt,false);
        if (obj.size()>0){
            Class_retrofit.Objfit().create(Servicio.class).InserCorbos(new Gson().toJson(obj)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        if (Boolean.valueOf(response.body())){
                            pdialog.setMessage("Iniciando.... ");
                            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),cnxt,"DELETE FROM COBROS");
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {}
            });
        }else {
            Log.d(TAG, "doInBackground: NO HAY COBROS");
        }


        List<Pedidos> listPedidos = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(),cnxt,false);
        Gson gson = new Gson();
        Log.d(TAG, "alderr: "+gson.toJson(listPedidos));
        if (listPedidos.size()>0) {
            Log.d("json->",gson.toJson(listPedidos));
            Class_retrofit.Objfit().create(Servicio.class).enviarPedidos(gson.toJson(listPedidos)).enqueue(new Callback<Respuesta_pedidos>() {
                @Override
                public void onResponse(Call<Respuesta_pedidos> call, Response<Respuesta_pedidos> response) {
                    if(response.isSuccessful()){
                        Respuesta_pedidos pedidoRespuesta = response.body();
                        pdialog.setMessage("Enviando Pedidos.... ");
                        Boolean var = Boolean.valueOf(pedidoRespuesta.getResults().get(0).getmEstado());
                        if (var){
                            new AlertDialog.Builder(cnxt).setTitle("MENSAJE").setMessage("PEDIDOS ENVIADOS, ACTUALICE SU EQUIPO..").setCancelable(false).setPositiveButton("OK",null).show();
                        }else {
                            new AlertDialog.Builder(cnxt).setTitle("MENSAJE").setMessage(pedidoRespuesta.getResults().get(0).getmEstado()).setCancelable(false).setPositiveButton("OK", null).show();
                        }
                    }else{
                        Toast.makeText(cnxt, "doInBackground ERROR AL ENVIAR PEDIDOS, ERROR: "+response.body(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_pedidos> call, Throwable t) {
                    Toast.makeText(cnxt, "doInBackground ERROR EN ENVIO DE PEDIDOS", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Log.d(TAG, "doInBackground: NO HAY PEDIDOS");
        }

        List<Visitas> obVisitas = Agenda_model.getVisitas(ManagerURI.getDirDb(), cnxt);
        Log.d(TAG, "doInBackground: visitas " + obVisitas.size());
        if (obVisitas.size()>0){
            Class_retrofit.Objfit().create(Servicio.class).inVisitas(new Gson().toJson(obVisitas)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){

                        if (Boolean.valueOf(response.body())){
                            pdialog.setMessage("Enviando Visitas.... ");
                            Log.d(TAG, "doInBackground: Se fue LOG");
                            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),cnxt,"UPDATE VISITAS SET Send=1;");
                        }else{
                            Log.d(TAG, "doInBackground: no se fue LOG");
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(TAG, "doInBackground: No se fue VISITAS");
                }
            });
        }

        List<Razon> objRazones = Razon_model.getInfoRazon(ManagerURI.getDirDb(), cnxt,false);
        Log.d(TAG, "doInBackgroundRazones: Razones " + objRazones.size());
        Log.d(TAG, "doInBackgroundRazones: Razones " + new Gson().toJson(objRazones));

        if (objRazones.size()>0){

            Class_retrofit.Objfit().create(Servicio.class).enviarRazones(new Gson().toJson(objRazones)).enqueue(new Callback<Respuesta_razones>() {
                @Override
                public void onResponse(Call<Respuesta_razones> call, Response<Respuesta_razones> response) {
                    if (response.isSuccessful()){
                        Respuesta_razones razonesRespuesta = response.body();
                        SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),cnxt,"UPDATE RAZON SET SEND = 1;");
                        Log.d(TAG, "doInBackground: SE ENVIOOO ");
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_razones> call, Throwable t) {
                    Log.d(TAG, "doInBackgroundRazones: No se fue RAZONES");
                }
            });
        }

       /* List<Agenda> objAgenda = Agenda_model.UnloadAgenda(ManagerURI.getDirDb(), cnxt);
        Log.d(TAG, "doInBackground: " + objAgenda.size());
        if (objAgenda.size()>0){
            Class_retrofit.Objfit().create(Servicio.class).unAgenda(new Gson().toJson(objAgenda)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        if (Boolean.valueOf(response.body())){
                            Log.d(TAG, "doInBackground: se fue AGENDA");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(TAG, "doInBackground: Nose fue AGENDA");
                }
            });
        }*/


        pdialog.dismiss();
        editor.putString("lstUnload", Clock.getTimeStamp()).apply();
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    private void Alerta(String Titulo,String Mensaje) {
        new AlertDialog.Builder(cnxt).setTitle(Titulo).setMessage(Mensaje).setCancelable(false).setPositiveButton("OK",null).show();
    }
}