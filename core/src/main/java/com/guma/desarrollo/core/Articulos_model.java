package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Articulos_model {
    public static void  SaveArticulos(Context context, ArrayList<Articulo> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM ARTICULOS");
            for(int i=0;i<ARTI.size();i++){
                Articulo a = ARTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("ARTICULO" , a.getmCodigo());
                contentValues.put("DESCRIPCION" , a.getmName());
                contentValues.put("EXISTENCIA" , a.getmExistencia());
                contentValues.put("UNIDAD" , a.getmUnidad());
                contentValues.put("UNIDAD_MEDIDA" , a.getmUnidadMedida());
                contentValues.put("PRECIO" , a.getmPrecio());
                contentValues.put("PUNTOS" , a.getmPuntos());
                contentValues.put("REGLAS" , a.getmReglas());
                myDataBase.insert("ARTICULOS", null, contentValues );
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

    public static List<Articulo> getArticulos(String basedir, Context context) {
        List<Articulo> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ARTICULOS", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Articulo tmp = new Articulo();
                    tmp.setmCodigo(cursor.getString(cursor.getColumnIndex("ARTICULO")));
                    tmp.setmName(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    tmp.setmExistencia(cursor.getString(cursor.getColumnIndex("EXISTENCIA")));
                    tmp.setmUnidad(cursor.getString(cursor.getColumnIndex("UNIDAD")));
                    tmp.setmPrecio(cursor.getString(cursor.getColumnIndex("PRECIO")));
                    tmp.setmPuntos(cursor.getString(cursor.getColumnIndex("PUNTOS")));
                    tmp.setmReglas(cursor.getString(cursor.getColumnIndex("REGLAS")));
                    tmp.setmUnidadMedida(cursor.getString(cursor.getColumnIndex("UNIDAD_MEDIDA")));
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
    public static List<String> getReglas( Context context,String mArticulo) {
        //String[] lista = null;
        List<String> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ARTICULOS", new String[] { "REGLAS","EXISTENCIA" }, "ARTICULO"+ "=?", new String[] { mArticulo }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Articulo tmp = new Articulo();
                    //lista = cursor.getString(cursor.getColumnIndex("REGLAS"));
                    lista.add(0,cursor.getString(cursor.getColumnIndex("REGLAS")));
                    lista.add(1,cursor.getString(cursor.getColumnIndex("EXISTENCIA")));
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
}
