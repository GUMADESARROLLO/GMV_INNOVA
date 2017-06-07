package com.guma.desarrollo.gmv.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Agenda_model;
import com.guma.desarrollo.core.Articulos_model;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.Activity.ReferenciasContexto;
import com.guma.desarrollo.gmv.api.Class_retrofit;
import com.guma.desarrollo.gmv.api.Servicio;
import com.guma.desarrollo.gmv.models.Respuesta_actividades;
import com.guma.desarrollo.gmv.models.Respuesta_agenda;
import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;
import com.guma.desarrollo.gmv.models.Respuesta_historial;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;
import com.guma.desarrollo.gmv.models.Respuesta_pedidos;
import com.guma.desarrollo.gmv.models.Respuesta_puntos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maryan.espinoza on 08/03/2017.
 */

public class TaskDownload extends AsyncTask<Integer,Integer,String> {
    public ProgressDialog pdialog;
    Context cnxt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String Usuario;

    private static final String TAG = "AgendaActivity";
    public TaskDownload(Context cnxt) {
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
    protected String doInBackground(Integer... params) {
        Usuario = preferences.getString("VENDEDOR","0");
        Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaArticulos()
                .enqueue(new Callback<Respuesta_articulos>() {
                    @Override
                    public void onResponse(Call<Respuesta_articulos> call, Response<Respuesta_articulos> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Articulos.... ");
                            Respuesta_articulos articuloRespuesta = response.body();
                            Log.d(TAG, "onResponse: Articulos " + articuloRespuesta.getCount());
                            Articulos_model.SaveArticulos(cnxt,articuloRespuesta.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Articulos" + response.errorBody() );
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta_articulos> call, Throwable t) {
                        Log.d(TAG, "onResponse: Failure Articulos" + t.getMessage() );
                        pdialog.dismiss();
                    }
                });

        /*Actividades*/
        //Toast.makeText(cnxt, "Antes de ...", Toast.LENGTH_LONG).show();
        Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaActividades()
                .enqueue(new Callback<Respuesta_actividades>() {
                    @Override
                    public void onResponse(Call<Respuesta_actividades> call, Response<Respuesta_actividades> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Actividades.... ");
                            Respuesta_actividades actividadRespuesta = response.body();
                            Log.d(TAG, "onResponse: Actividades " + actividadRespuesta.getCount());
                            Actividades_model.SaveActividades(cnxt,actividadRespuesta.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Actividades" + response.errorBody() );
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta_actividades> call, Throwable t) {
                        Log.d(TAG, "onResponse: Failure Actividades " + t.getMessage() );
                        pdialog.dismiss();
                    }
                });

       Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaClienteMora(Usuario)
                .enqueue(new Callback<Respuesta_mora>() {
                    @Override
                    public void onResponse(Call<Respuesta_mora> call, Response<Respuesta_mora> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Puntos.... ");
                            Respuesta_mora moraRespuesta = response.body();
                            Log.d(TAG, "onResponse: Mora " + moraRespuesta.getCount());
                            Clientes_model.SaveMora(cnxt,moraRespuesta.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Mora " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_mora> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Mora " + t.getMessage() );
                    }
                });


       List<Pedidos> listPedidos = Pedidos_model.getInfoPedidos(ManagerURI.getDirDb(),cnxt,false);

        Gson gson = new Gson();
       if (listPedidos.size()>0) {
            Class_retrofit.Objfit()
                    .create(Servicio.class)
                    .actualizarPedidos(gson.toJson(listPedidos))
                    .enqueue(new Callback<Respuesta_pedidos>() {
                        @Override
                        public void onResponse(Call<Respuesta_pedidos> call, Response<Respuesta_pedidos> response) {
                            if (response.isSuccessful()) {
                                pdialog.setMessage("Actualizando pedidos....");
                                Log.d("alder",response.body().toString());
                                Pedidos Obj = new Pedidos();
                                Respuesta_pedidos pedidosRespuesta = response.body();
                                Pedidos_model.actualizarPedidos(cnxt, pedidosRespuesta.getResults());
                            } else {
                                Log.d("", "onResponse: noSuccessful PEDIDOS " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Respuesta_pedidos> call, Throwable t) {
                            Log.d("", "onResponse: Failure pedidos: " + t.getMessage());
                        }
                    });
        }

       Class_retrofit.Objfit()
                .create(Servicio.class)
                .obtenerListaClienteIndicadores(Usuario)
                .enqueue(new Callback<Respuesta_indicadores>() {
                    @Override
                    public void onResponse(Call<Respuesta_indicadores> call, Response<Respuesta_indicadores> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Informacion de Clientes.... ");
                            Respuesta_indicadores IndicadorRespuesta = response.body();

                            Log.d(TAG, "onResponse: Indicadores " + IndicadorRespuesta.getCount());
                            Clientes_model.SaveIndicadores(cnxt,IndicadorRespuesta.getResults());
                        }else{
                            Log.d(TAG, "onResponse: noSuccessful Indicadores " + response.errorBody() );
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta_indicadores> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Indicadores " + t.getMessage() );

                    }
                });
       Class_retrofit.Objfit().
                create(Servicio.class).
                obtenerListaClientes(Usuario).
                enqueue(new Callback<Respuesta_clientes>() {
                    @Override
                    public void onResponse(Call<Respuesta_clientes> call, Response<Respuesta_clientes> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Clientes.... ");
                            Respuesta_clientes clRespuesta = response.body();
                            Log.d(TAG, "onResponse: Clientes "  + clRespuesta.getCount());
                            Clientes_model.SaveClientes(cnxt,clRespuesta .getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Clientes " + response.errorBody() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta_clientes> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Clientes " + t.getMessage() );

                    }
                });


       Class_retrofit.Objfit().create(Servicio.class)
                .obtenerFacturasPuntos(Usuario)
                .enqueue(new Callback<Respuesta_puntos>() {
                    @Override
                    public void onResponse(Call<Respuesta_puntos> call, Response<Respuesta_puntos> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Puntos.... ");
                            Respuesta_puntos clpuntos = response.body();
                            Clientes_model.SaveFacturas(cnxt,clpuntos.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Facturas " + response.errorBody() );
                        }

                    }


                    @Override
                    public void onFailure(Call<Respuesta_puntos> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Facturas " + t.getMessage() );
                    }
                });


        Log.d(TAG, "onResponse: Agenda el usuario " + Usuario);
        Class_retrofit.Objfit().create(Servicio.class)
                .Agenda(Usuario)
                .enqueue(new Callback<Respuesta_agenda>() {
                    @Override
                    public void onResponse(Call<Respuesta_agenda> call, Response<Respuesta_agenda> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Puntos.... ");
                            Respuesta_agenda clpuntos = response.body();
                            Log.d(TAG, "onResponse: Agenda " + clpuntos.getCount());
                            Log.d(TAG, "onResponse Alder: "+response.body().getResults().get(0).getmLunes());
                            /*Log.d(TAG, "onResponse: Agenda " + clpuntos.getCount());*/
                            Agenda_model.Save_Agenda(cnxt,clpuntos.getResults());
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Agenda " + response.errorBody() );
                        }

                    }
                    @Override
                    public void onFailure(Call<Respuesta_agenda> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Agenda " + t.getMessage() );
                    }
                });

        Class_retrofit.Objfit().create(Servicio.class)
                .obtHistorial(Usuario)
                .enqueue(new Callback<Respuesta_historial>() {
                    @Override
                    public void onResponse(Call<Respuesta_historial> call, Response<Respuesta_historial> response) {
                        if(response.isSuccessful()){
                            pdialog.setMessage("Cargado Historial.... ");
                            Respuesta_historial clpuntos = response.body();
                            Log.d(TAG, "onResponse: Historial " + clpuntos.getCount());
                            Clientes_model.SaveHistorialCompra(cnxt,clpuntos.getResults());
                            Alerta();
                            pdialog.dismiss();
                        }else{
                            pdialog.dismiss();
                            Log.d(TAG, "onResponse: noSuccessful Historial " + response.errorBody() );
                        }

                    }


                    @Override
                    public void onFailure(Call<Respuesta_historial> call, Throwable t) {
                        pdialog.dismiss();
                        Log.d(TAG, "onResponse: Failure Historial " + t.getMessage() );
                    }
                });

       editor.putString("lstDownload", Clock.getTimeStamp());
       editor.apply();

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
    private void Alerta() {
        new AlertDialog.Builder(cnxt).setTitle("RECIBIDO").setMessage("Informacion Recibida...").setCancelable(false).setPositiveButton("OK",null).show();
    }
}