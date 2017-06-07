package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alder.hernandez on 13/03/2017.
 */

public class Pedidos_model {
    public static void  SavePedido(Context context, ArrayList<Pedidos> PEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            for(int i=0;i<PEDIDO.size();i++){
                Pedidos a = PEDIDO.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("FECHA_CREADA" , a.getmFecha());
                contentValues.put("MONTO" , a.getmPrecio());
                contentValues.put("ESTADO" , a.getmEstado());
                contentValues.put("DESCRIPCION" , a.getmComentario());

                myDataBase.insert("PEDIDO", null, contentValues );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static void  SaveDetallePedido(Context context, ArrayList<Pedidos> DETPEDIDO){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<DETPEDIDO.size();i++){
                Pedidos a = DETPEDIDO.get(i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("IDPEDIDO" , a.getmIdPedido());
                contentValues.put("ARTICULO" , a.getmArticulo());
                contentValues.put("DESCRIPCION" , a.getmDescripcion());
                contentValues.put("CANTIDAD" , a.getmCantidad());
                contentValues.put("TOTAL" , a.getmPrecio());
                contentValues.put("BONIFICADO" , a.getmBonificado());

                myDataBase.insert("PEDIDO_DETALLE", null, contentValues );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
    public static List<Pedidos> getInfoPedidos(String basedir, Context context,Boolean hoy) {
        List<Pedidos> lista = new ArrayList<>();
        Cursor cursor;
        Integer i = 0;
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();

            if (hoy){
                cursor = myDataBase.query(true, "PEDIDO",null, "FECHA_CREADA LIKE '%"+Clock.getTMD()+"%' AND ESTADO IN ("+ TextUtils.join(",", new String[] { "0", "1", "2", "3", "4" } )+")", null, null, null, null, null);
            }else {
                cursor = myDataBase.query(true, "PEDIDO", null, "ESTADO IN (" + TextUtils.join(",", new String[]{"0", "1", "2", "3", "4"}) + ")", null, null, null, null, null);
            }
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                    Pedidos tmp = new Pedidos();
                    tmp.setmIdPedido(cursor.getString(cursor.getColumnIndex("IDPEDIDO")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("VENDEDOR")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("MONTO")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA_CREADA")));
                    tmp.setmEstado(cursor.getString(cursor.getColumnIndex("ESTADO")));
                    tmp.setmComentario(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    Cursor cursor2 = myDataBase.query(true, "PEDIDO_DETALLE", null, "IDPEDIDO"+ "=?", new String[] { cursor.getString(cursor.getColumnIndex("IDPEDIDO")) }, null, null, null, null);
                    cursor2.moveToFirst();

                    while(!cursor2.isAfterLast()) {
                        tmp.getDetalles().put("ID"+i,cursor2.getString(cursor2.getColumnIndex("IDPEDIDO")));
                        tmp.getDetalles().put("ARTICULO"+i,cursor2.getString(cursor2.getColumnIndex("ARTICULO")));
                        tmp.getDetalles().put("DESC"+i,cursor2.getString(cursor2.getColumnIndex("DESCRIPCION")));
                        tmp.getDetalles().put("CANT"+i,cursor2.getString(cursor2.getColumnIndex("CANTIDAD")));
                        tmp.getDetalles().put("TOTAL"+i,cursor2.getString(cursor2.getColumnIndex("TOTAL")));
                        tmp.getDetalles().put("BONI"+i,cursor2.getString(cursor2.getColumnIndex("BONIFICADO")));
                        i++;
                        cursor2.moveToNext();
                    }
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
    public static List<Pedidos> getDetalle(String basedir, Context context,String mIdPedido) {
        List<Pedidos> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "PEDIDO_DETALLE", null, "IDPEDIDO"+ "=?", new String[] { mIdPedido }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    tmp.setmIdPedido(cursor.getString(cursor.getColumnIndex("IDPEDIDO")));
                    tmp.setmArticulo(cursor.getString(cursor.getColumnIndex("ARTICULO")));
                    tmp.setmDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    tmp.setmCantidad(cursor.getString(cursor.getColumnIndex("CANTIDAD")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("TOTAL")));
                    tmp.setmBonificado(cursor.getString(cursor.getColumnIndex("BONIFICADO")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
    public static List<Pedidos> getComentario(String basedir, Context context,String mIdPedido) {
        List<Pedidos> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "PEDIDO", null, "IDPEDIDO"+ "=?", new String[] { mIdPedido }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    tmp.setmComentario(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
    public static void  actualizarPedidos(Context context, ArrayList<Pedidos> PEDIDOS){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<PEDIDOS.size();i++){
                Pedidos a = PEDIDOS.get(i);
                Log.d("guardando",a.getmIdPedido());
                Log.d("guardando",a.getmEstado());
                SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(),context,"UPDATE PEDIDO SET ESTADO = '"+a.getmEstado()+"' WHERE IDPEDIDO = '"+a.getmIdPedido()+"'");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }

    public static void borrar(Context contexto,String basedir) {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(basedir, contexto);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "PEDIDO", null, "ESTADO"+ "=?", new String[] { "3" }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Pedidos tmp = new Pedidos();
                    SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM PEDIDO WHERE IDPEDIDO= '"+cursor.getString(cursor.getColumnIndex("IDPEDIDO"))+"'");
                    SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM PEDIDO_DETALLE WHERE IDPEDIDO= '"+cursor.getString(cursor.getColumnIndex("IDPEDIDO"))+"'");
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
}
