package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis.perez on 23/03/2017.
 */

public class Actividades_model {
    public static void  SaveActividades(Context context, ArrayList<Actividad> ACTI){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM ACTIVIDAD");
            for(int i=0;i<ACTI.size();i++){
                Actividad a =ACTI.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("IdAE" , a.getmIdAE());
                contentValues.put("Categoria" , a.getmCategoria());
                contentValues.put("Actividad" , a.getmActividad());
                //contentValues.put("X", a.getChecked()? 1 : 0);
                myDataBase.insert("ACTIVIDAD", null, contentValues );
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

    public static List<Actividad> getActividades(String basedir, Context context) {
        List<Actividad> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "ACTIVIDAD", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Actividad tmp = new Actividad();
                    tmp.setmIdAE(cursor.getString(cursor.getColumnIndex("IdAE")));
                    tmp.setmCategoria(cursor.getString(cursor.getColumnIndex("Categoria")));
                    tmp.setmActividad(cursor.getString(cursor.getColumnIndex("Actividad")));
                    //tmp.setChecked(cursor.getString(cursor.getColumnIndex("X")));
                    //tmp.setChecked(cursor.getInt(cursor.getColumnIndex("X"))!=0);
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
