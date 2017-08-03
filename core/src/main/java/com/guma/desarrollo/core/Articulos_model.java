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
                contentValues.put("NLP1" , a.getmNlp1());
                contentValues.put("NLP2" , a.getmNlp2());
                contentValues.put("NLP3" , a.getmNlp3());
                contentValues.put("NLP4" , a.getmNlp4());

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

    public static void  SaveDescuentos(Context context, ArrayList<Articulo> ARTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM DESCUENTOS");
            for(int i=0;i<ARTI.size();i++){
                Articulo a = ARTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CLASIFICACION" , a.getmClasificacion());
                contentValues.put("CODIGO" , a.getmCodigo());
                contentValues.put("DESCRIPCION" , a.getmName());
                contentValues.put("PRECIO" , a.getmPrecio());
                contentValues.put("IVA" , a.getmIva());
                contentValues.put("MINIMO" , a.getmMinimo());
                contentValues.put("MAXIMO" , a.getmMaximo());
                contentValues.put("DESCUENTO" , a.getmDescuento());
                myDataBase.insert("DESCUENTOS", null, contentValues );
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

    //public static List<String> getPrecioIva( Context context,String grupo, String listaPrecio,String articulo,boolean precio) {
        //List<String> lista = new ArrayList<>();
    public static String getPrecioIva( Context context,String grupo, String listaPrecio,String articulo,boolean precio) {
        String lista = "0";
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        Cursor cursor = null;
        String columna;
        try
        {
            Log.d("", "getPrecioIva: "+articulo);
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            if (precio) {
                columna = "NLP2";
                cursor = myDataBase.query(true, "ARTICULOS", new String[] { "NLP2" }, "ARTICULO"+ "=?", new String[] { articulo }, null, null, null, null);
            }else {
                columna = listaPrecio;
                cursor = myDataBase.query(true, "ARTICULOS", new String[]{listaPrecio}, "ARTICULO" + "=?", new String[]{articulo}, null, null, null, null);
            }
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Log.d("", "getPrecioIva: "+cursor.getColumnIndex(columna));
                    //lista.add(0,cursor.getString(cursor.getColumnIndex(columna)));
                    lista = cursor.getString(cursor.getColumnIndex(columna));
                    cursor.moveToNext();
                }
            }/*else{
                lista.add(0,"0");
            }*/
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }


    public static String getIvaDescent(Context context, String grupo, String listaPrecio, String articulo) {
        String lista = "";
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "DESCUENTOS", null, "CODIGO" + " =? "+ " AND " + "CLASIFICACION =?", new String[] { articulo,grupo }, null, null, null, null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    lista = cursor.getString(cursor.getColumnIndex("IVA"));
                    cursor.moveToNext();
                }
            }else{
                lista = "0";
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

    public static String getDescuento(Context context, String grupo, String articulo) {
        String lista = "";
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query("DESCUENTOS", new String[] {"MINIMO" +"|| '+' ||"+ "DESCUENTO AS REGLA"}, "CODIGO" + " =? "+ " AND " + "CLASIFICACION =?",
                                            new String[] { articulo,grupo }, null, null, null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    lista += cursor.getString(cursor.getColumnIndex("REGLA"))+",";

                    cursor.moveToNext();
                }
            }else{
                lista = "";
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        if (lista.length()>0) {
            lista = lista.substring(0, lista.length() - 1);
        }
        Log.d("", "afterTextChanged: "+lista);
        return lista;
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
                    tmp.setmNlp1(cursor.getString(cursor.getColumnIndex("NLP1")));
                    tmp.setmNlp2(cursor.getString(cursor.getColumnIndex("NLP2")));
                    tmp.setmNlp3(cursor.getString(cursor.getColumnIndex("NLP3")));
                    tmp.setmNlp4(cursor.getString(cursor.getColumnIndex("NLP4")));
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

    public  static List<Articulo> getExistencia( Context context,String mArticulo) {
        List<Articulo> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            Log.d("", "getExistencia: "+mArticulo);
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ARTICULOS", new String[] { "EXISTENCIA","UNIDAD" }, "ARTICULO"+ "=?", new String[] { mArticulo }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Articulo tmp = new Articulo();
                    tmp.setmExistencia(cursor.getString(cursor.getColumnIndex("EXISTENCIA")));
                    tmp.setmUnidad(cursor.getString(cursor.getColumnIndex("UNIDAD")));
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
}