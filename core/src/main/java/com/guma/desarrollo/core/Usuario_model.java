package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alder.hernandez on 03/03/2017.
 */

public class Usuario_model {
    public static List<Usuario> getUsuario(String basedir, Context context,String ruta) {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            /*myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES_MORA", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                }
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
    public static List<Usuario> ValidarUser(String mUsuario,String Pass,Context context) {

        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {

            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "USUARIOS", null, "USUARIO"+"=?" + " AND "+ " CONTRASENA" +"=?", new String[] { mUsuario,Pass }, null, null, null, null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Usuario tmp = new Usuario();
                    Log.d("ARREGLO",cursor.getString(cursor.getColumnIndex("USUARIO")));
                    tmp.setmIdUser(cursor.getString(cursor.getColumnIndex("IDUSER")));
                    tmp.setmUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
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
    public static void  SaveUsuario(Context context, ArrayList<Usuario> detUsuario){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM USUARIOS");
            for(int i=0;i<detUsuario.size();i++){
                Usuario a = detUsuario.get(i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("IDUSER" , a.getmIdUser());
                contentValues.put("USUARIO" , a.getmUsuario());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("CONTRASENA" , a.getmPass());
                contentValues.put("ESTADO" , "1");

                myDataBase.insert("USUARIOS", null, contentValues );
            }
            SaveConsecutivo(context,detUsuario);
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
    public static void  SaveConsecutivo(Context context, ArrayList<Usuario> detUsuario){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM LLAVES");
            Usuario a = detUsuario.get(0);

            ContentValues contentValues = new ContentValues();
            contentValues.put("TIPO" , "PEDIDOS");
            contentValues.put("SECUENCIA" , a.getmPedido());
            myDataBase.insert("LLAVES", null, contentValues );
            contentValues.put("TIPO" , "COBROS");
            contentValues.put("SECUENCIA" , a.getmCobro());
            myDataBase.insert("LLAVES", null, contentValues );
            contentValues.put("TIPO" , "RAZON");
            contentValues.put("SECUENCIA" , a.getmRazon());
            myDataBase.insert("LLAVES", null, contentValues );
            contentValues.put("TIPO" , "CLIENTE");
            contentValues.put("SECUENCIA" , a.getmCliente());
            myDataBase.insert("LLAVES", null, contentValues );
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
}
