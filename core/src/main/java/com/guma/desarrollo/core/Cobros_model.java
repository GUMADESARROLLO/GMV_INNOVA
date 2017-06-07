package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryan.espinoza on 10/03/2017.
 */

public class Cobros_model {
    public static void  SaveCobro(Context context, ArrayList<Cobros> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            for(int i=0;i<ARTI.size();i++){
                Cobros a = ARTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDCOBRO" , a.getmIdCobro());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("RUTA" , a.getmRuta());
                contentValues.put("FECHA" , a.getmFecha());
                contentValues.put("IMPORTE" , a.getmImporte());
                contentValues.put("TIPO" , a.getmTipo());
                contentValues.put("OBSERVACION" , a.getmObservacion());
                myDataBase.insert("COBROS", null, contentValues );
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

    public static List<Cobros> getCobros(String basedir, Context context,Boolean hoy) {
        List<Cobros> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        Cursor cursor;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            if (hoy) {
                cursor = myDataBase.query(true, "COBROS", null, "FECHA LIKE ?", new String[] {"%"+ Clock.getTMD()+ "%" }, null, null, null, null);
            }else{
                cursor = myDataBase.query(true, "COBROS", null, null, null, null, null, null, null);
            }
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Cobros tmp = new Cobros();
                    tmp.setmIdCobro(cursor.getString(cursor.getColumnIndex("IDCOBRO")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmRuta(cursor.getString(cursor.getColumnIndex("RUTA")));
                    tmp.setmImporte(cursor.getString(cursor.getColumnIndex("IMPORTE")));
                    tmp.setmTipo(cursor.getString(cursor.getColumnIndex("TIPO")));
                    tmp.setmObservacion(cursor.getString(cursor.getColumnIndex("OBSERVACION")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
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
    public static List<Cobros> getInfoCobro(String basedir, Context context,String Cliente) {
        List<Cobros> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "COBROS", null, "IDCORBRO"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Cobros tmp = new Cobros();
                    tmp.setmIdCobro(cursor.getString(cursor.getColumnIndex("IDCOBRO")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmRuta(cursor.getString(cursor.getColumnIndex("RUTA")));
                    tmp.setmImporte(cursor.getString(cursor.getColumnIndex("IMPORTE")));
                    tmp.setmTipo(cursor.getString(cursor.getColumnIndex("TIPO")));
                    tmp.setmObservacion(cursor.getString(cursor.getColumnIndex("OBSERVACION")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
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
    public static void borrar(Context contexto) {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), contexto,"DELETE FROM COBROS WHERE SEND = '1'");
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }
}
