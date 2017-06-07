package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by luis.perez on 28/03/2017.
 */

public class Razon_model {

    //public static void SaveRazon(Context context,ArrayList<Razon> RAZON){
    public static void SaveRazon(Context context,Razon r){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(),context);
            myDataBase = myDbHelper.getReadableDatabase();
            /*for (int i=0;i<RAZON.size();i++)
            {
                Razon r = RAZON.get(i);*/
                ContentValues contentValues = new ContentValues();
                ContentValues contentValuesDet = new ContentValues();

                contentValues.put("IdRazon",r.getmIdRazon());
                contentValues.put("Vendedor",r.getmVendedor());
                contentValues.put("Cliente",r.getmCliente());
                contentValues.put("Nombre",r.getmNombre());
                contentValues.put("Fecha",r.getmFecha());
                contentValues.put("Observacion",r.getmObservacion());
                contentValues.put("SEND",r.getmSend());
                /*Guradar el Encabezado del Registro*/
                myDataBase.insert("RAZON",null,contentValues);
                /*Guardar el Detalle del Registro*/
                for (int i=0;i<r.rdet.size();i++)
                {
                    contentValuesDet.put("IdRazon",r.rdet.get(i).mIdRazon.toString());
                    contentValuesDet.put("IdAE",r.rdet.get(i).mIdAE.toString());
                    contentValuesDet.put("Actividad",r.rdet.get(i).mActividad.toString());
                    contentValuesDet.put("Categoria",r.rdet.get(i).mCategoria.toString());
                    myDataBase.insert("RAZON_DETALLE",null,contentValuesDet);
                }
                contentValuesDet=null;
            /*}*/
            contentValues = null;
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if (myDataBase != null) { myDataBase.close(); }
            if (myDbHelper != null) { myDbHelper.close(); }
        }
    }

    public static void SaveRazonDetalle(Context context, ArrayList<Razon> RAZON_DETALLE){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(),context);
            myDataBase = myDbHelper.getReadableDatabase();
            for (int i=0;i<RAZON_DETALLE.size();i++){
                Razon r = RAZON_DETALLE.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("",r.getmIdRazon());

                myDataBase.insert("RAZON_DETALLE",null,contentValues);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if (myDataBase != null) { myDataBase.close(); }
            if (myDbHelper != null) { myDbHelper.close(); }
        }
    }

    public static List<Razon> getInfoRazon(String basedir, Context context,boolean all){
        List<Razon> lista = new ArrayList<>();

        Integer i = 0;
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        Cursor cursor;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Log.d(TAG, "doInBackgroundRazones: "+all);
            if (all){
                cursor = myDataBase.query(true,"RAZON", null, null, null, null, null, null, null);
            }else{
                cursor = myDataBase.query(true, "RAZON", null, "SEND NOT IN (" + TextUtils.join(",", new String[]{"1"}) + ")", null, null, null, null, null);
            }
            Log.d(TAG, "doInBackgroundRazones: "+cursor.getCount());
            if (cursor.getCount()>0){
                Log.d(TAG, "doInBackgroundRazones: Razones ENTROOOOO");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    Razon tmp = new Razon();
                    tmp.setmIdRazon(cursor.getString(cursor.getColumnIndex("IdRazon")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("Vendedor")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("Cliente")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("Fecha")));
                    tmp.setmObservacion(cursor.getString(cursor.getColumnIndex("Observacion")));
                    Cursor cursor2 = myDataBase.query(true, "RAZON_DETALLE", null, "IdRazon"+ "=?", new String[] { cursor.getString(cursor.getColumnIndex("IdRazon")) }, null, null, null, null);
                    cursor2.moveToFirst();

                    while (!cursor2.isAfterLast()){
                        tmp.getDetalles().put("IdRazon"+i,cursor2.getString(cursor2.getColumnIndex("IdRazon")));
                        tmp.getDetalles().put("IdAE"+i,cursor2.getString(cursor2.getColumnIndex("IdAE")));
                        tmp.getDetalles().put("Actividad"+i,cursor2.getString(cursor2.getColumnIndex("Actividad")));
                        tmp.getDetalles().put("Categoria"+i,cursor2.getString(cursor2.getColumnIndex("Categoria")));
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
            if (myDataBase != null) { myDataBase.close(); }
            if (myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }

    public static List<Razon> getDetalles(String basedir, Context context, String mIdRazon){
        List<Razon> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDBHelper = null;
        try
        {
            myDBHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDBHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "RAZON_DETALLE", null, "IdRazon"+ "=?", new String[] { mIdRazon }, null, null, null, null);
            if (cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    Razon tmp = new Razon();
                    tmp.setmIdRazon(cursor.getString(cursor.getColumnIndex("IdRazon")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if (myDataBase != null) { myDataBase.close(); }
            if (myDBHelper != null) { myDBHelper.close(); }
        }
        return lista;
    }

    public static void borrar(Context contexto) {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM RAZON WHERE SEND = '1'");
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }

}
