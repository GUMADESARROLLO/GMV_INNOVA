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
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
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
import com.guma.desarrollo.gmv.models.Respuesta_cobros;
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

        List<Pedidos> listPedidos = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(),cnxt,false);

        Gson gson = new Gson();
        Log.d(TAG, "doInBackgroundPedidos: "+gson.toJson(listPedidos));
        if (listPedidos.size()>0) {
            Log.d("json->",gson.toJson(listPedidos));
            Class_retrofit.Objfit().create(Servicio.class).enviarPedidos(gson.toJson(listPedidos)).enqueue(new Callback<Respuesta_pedidos>() {
                @Override
                public void onResponse(Call<Respuesta_pedidos> call, Response<Respuesta_pedidos> response) {
                    pdialog.setMessage("Enviando Pedidos.... ");
                    if(response.isSuccessful()){
                        pdialog.setMessage("Actualizando Pedidos.... ");
                        Respuesta_pedidos pedidosRespuesta = response.body();
                        Pedidos_model.actualizarPedidos(cnxt, pedidosRespuesta.getResults());
                        new AlertDialog.Builder(cnxt).setTitle("MENSAJE").setMessage("PEDIDOS ENVIADOS Y ACTUALIZADOS...").setCancelable(false).setPositiveButton("OK",null).show();
                    }else{
                        Toast.makeText(cnxt, "doInBackground ERROR AL ENVIAR PEDIDOS, ERROR: "+response.body(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_pedidos> call, Throwable t) {
                    new AlertDialog.Builder(cnxt).setTitle("MENSAJE").setMessage("ERROR EN ENVIO DE PEDIDOS"+t.getMessage()).setCancelable(false).setPositiveButton("OK",null).show();
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
        if (objRazones.size()>0){

            Class_retrofit.Objfit().create(Servicio.class).enviarRazones(new Gson().toJson(objRazones)).enqueue(new Callback<Respuesta_razones>() {
                @Override
                public void onResponse(Call<Respuesta_razones> call, Response<Respuesta_razones> response) {
                    if (response.isSuccessful()){
                        Respuesta_razones razonesRespuesta = response.body();
                        SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),cnxt,"UPDATE RAZON SET SEND = 1;");
                    }
                }
                @Override
                public void onFailure(Call<Respuesta_razones> call, Throwable t) {
                    Log.d(TAG, "doInBackgroundRazones: No se fue RAZONES");
                }
            });
        }

        List<Clientes> objClientes = Clientes_model.getInfoCliente(ManagerURI.getDirDb(),cnxt,"");
        Gson gson2 = new Gson();
        if (objClientes.size()>0) {
            Log.d("json-> NUEVOS_CLIENTES ",gson2.toJson(objClientes));
            Class_retrofit.Objfit().create(Servicio.class).enviarClientes(gson2.toJson(objClientes)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pdialog.setMessage("Enviando Nuevos Clientes.... ");
                    if (response.isSuccessful()){
                        pdialog.setMessage("Enviando nuevos clientes.... ");
                        if (Boolean.valueOf(response.body())){

                        }else{
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(TAG, "doInBackground: No se fue CLIENTES");
                }
            });
        }else{
            Log.d(TAG, "doInBackground: NO HAY CLIENTES NUEVOS");
        }

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