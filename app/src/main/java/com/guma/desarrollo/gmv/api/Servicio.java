package com.guma.desarrollo.gmv.api;

import com.google.gson.JsonObject;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.gmv.models.Respuesta_actividades;
import com.guma.desarrollo.gmv.models.Respuesta_agenda;
import com.guma.desarrollo.gmv.models.Respuesta_articulos;
import com.guma.desarrollo.gmv.models.Respuesta_clientes;
import com.guma.desarrollo.gmv.models.Respuesta_cobros;
import com.guma.desarrollo.gmv.models.Respuesta_historial;
import com.guma.desarrollo.gmv.models.Respuesta_indicadores;
import com.guma.desarrollo.gmv.models.Respuesta_mora;

import com.guma.desarrollo.gmv.models.Respuesta_pedidos;
import com.guma.desarrollo.gmv.models.Respuesta_razones;
import com.guma.desarrollo.gmv.models.Respuesta_usuario;

import com.guma.desarrollo.gmv.models.Respuesta_puntos;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by maryan.espinoza on 01/03/2017.
 */

public interface Servicio {

    @GET("ARTICULOS")
    Call<Respuesta_articulos> obtenerListaArticulos();

    @GET("descuentos")
    Call<Respuesta_articulos> obtenerDescuentos();

    @GET("LOTES")
    Call<Respuesta_articulos> obtenerLotes();

    @FormUrlEncoded
    @POST("Clientes")
    Call<Respuesta_clientes> obtenerListaClientes(@Field("mVendedor") String mVendedor);

    @FormUrlEncoded
    @POST("ClientesIndicadores")
    Call<Respuesta_indicadores> obtenerListaClienteIndicadores(@Field("mVendedor") String mVendedor);

    @FormUrlEncoded
    @POST("ClientesMora")
    Call<Respuesta_mora> obtenerListaClienteMora(@Field("mVendedor") String mVendedor);

    @FormUrlEncoded
    @POST("LoginRest")
    Call<Respuesta_usuario> obtenerListaUsuario(@Field("usuario") String apiKey, @Field("pass") String apiKey2);

    @FormUrlEncoded
    @POST("CONSECUTIVO")
    Call<Respuesta_usuario> obtenerConsecutivo(@Field("usuario") String apiKey);

    @FormUrlEncoded
    @POST("url_pedidos")
    Call<Respuesta_pedidos> enviarPedidos(@Field("PEDIDOS") String pedidos);

    @FormUrlEncoded
    @POST("insertRazones")
    Call<Respuesta_razones> enviarRazones(@Field("RAZONES") String razones);

    @FormUrlEncoded
    @POST("updatePedidos")
    Call<Respuesta_pedidos> actualizarPedidos(@Field("PEDIDOS") String pedidos);

    @FormUrlEncoded
    @POST("updateClientes")
    Call<Respuesta_clientes> actualizarClientes(@Field("CLIENTES") String clientes);

    @FormUrlEncoded
    @POST("NuevosClientes")
    Call<String> enviarClientes(@Field("mCliente") String mCliente);

    @FormUrlEncoded
    @POST("Puntos")
    Call<Respuesta_puntos> obtenerFacturasPuntos(@Field("mVendedor") String mVendedor);

    /*@FormUrlEncoded
    @POST("InsertCobros")
    Call<Respuesta_cobros> InserCorbos(@Field("pCobros") String pCobros);*/

    @GET("Actividades")
    Call<Respuesta_actividades> obtenerListaActividades();

    @FormUrlEncoded
    @POST("inVisitas")
    Call<String> inVisitas(@Field("mVisitas") String mVisitas);

    @FormUrlEncoded
    @POST("Agenda")
    Call<Respuesta_agenda> Agenda(@Field("mVendedor") String mVendedor);

    @FormUrlEncoded
    @POST("unAgenda")
    Call<String> unAgenda(@Field("mAgenda") String mVisitas);

    @FormUrlEncoded
    @POST("Historial")
    Call<Respuesta_historial> obtHistorial(@Field("mVendedor") String mVendedor);

}