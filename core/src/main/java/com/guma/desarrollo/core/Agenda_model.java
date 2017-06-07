package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by maryan.espinoza on 23/03/2017.
 */

public class Agenda_model {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    public static List<Visitas> getVisitas(String basedir, Context context) {
        List<Visitas> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "VISITAS", null, "Send"+ "=?", new String[] { "0" }, null, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Visitas tmp = new Visitas();
                    tmp.setmIdPlan(cursor.getString(cursor.getColumnIndex("IdPlan")));
                    tmp.setmIdCliente(cursor.getString(cursor.getColumnIndex("IdCliente")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("Fecha")));
                    tmp.setmLati(cursor.getString(cursor.getColumnIndex("Lati")));
                    tmp.setmLogi(cursor.getString(cursor.getColumnIndex("Logi")));
                    tmp.setmLocal(cursor.getString(cursor.getColumnIndex("Local")));
                    tmp.setmInicio(cursor.getString(cursor.getColumnIndex("Inicio")));
                    tmp.setmFin(cursor.getString(cursor.getColumnIndex("Fin")));
                    tmp.setmObservacion(cursor.getString(cursor.getColumnIndex("Observacion")));
                    tmp.setmTipo(cursor.getString(cursor.getColumnIndex("Tipo")));
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
    public static List<Map<String, Object>> getAgenda(String basedir, Context context) {
        //List<String> lista = new ArrayList<>();
        List<Map<String, Object>> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "VCLIENTES", null, null, null, null, null, null, null);
            if(cursor.getCount() > 0) {
                //Log.d(TAG, "getAgenda: "+cursor.getCount());
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("IDPLAN",cursor.getString(cursor.getColumnIndex("IdPlan")));
                    map.put("LUNES",cursor.getString(cursor.getColumnIndex("Lunes")));
                    map.put("MARTES",cursor.getString(cursor.getColumnIndex("Martes")));
                    map.put("MIERCOLES",cursor.getString(cursor.getColumnIndex("Miercoles")));
                    map.put("JUEVES",cursor.getString(cursor.getColumnIndex("Jueves")));
                    map.put("VIERNES",cursor.getString(cursor.getColumnIndex("Viernes")));
                    lista.add(map);
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
    public static List<Agenda> UnloadAgenda(String basedir, Context context) {
        //List<String> lista = new ArrayList<>();
        List<Agenda> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            //Cursor cursor = myDataBase.query(true, "VCLIENTES", null, null, null, null, null, null, null);
            //Cursor cursor = myDataBase.rawQuery("SELECT T0.*,T1.Lunes,T1.Martes,T1.Miercoles,T1.Jueves,T1.Viernes FROM AGENDA T0 INNER JOIN VCLIENTES T1 ON T0.IdPlan = T1.IdPlan",null);
            Cursor cursor = myDataBase.rawQuery("SELECT T0.*,T1.Lunes,T1.Martes,T1.Miercoles,T1.Jueves,T1.Viernes FROM AGENDA T0 INNER JOIN VCLIENTES T1 ON T0.IdPlan = T1.IdPlan",null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Agenda tmp = new Agenda();
                    tmp.setmIdPlan(cursor.getString(cursor.getColumnIndex("IdPlan")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("Vendedor")));
                    tmp.setmRuta(cursor.getString(cursor.getColumnIndex("Ruta")));
                    tmp.setmInicia(cursor.getString(cursor.getColumnIndex("Inicia")));
                    tmp.setmTermina(cursor.getString(cursor.getColumnIndex("Termina")));
                    tmp.setmZona(cursor.getString(cursor.getColumnIndex("Zona")));
                    tmp.setmLunes(cursor.getString(cursor.getColumnIndex("Lunes")));
                    tmp.setmMartes(cursor.getString(cursor.getColumnIndex("Martes")));
                    tmp.setmMiercoles(cursor.getString(cursor.getColumnIndex("Miercoles")));
                    tmp.setmJueves(cursor.getString(cursor.getColumnIndex("Jueves")));
                    tmp.setmViernes(cursor.getString(cursor.getColumnIndex("Viernes")));
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
    public static void  Save_Agenda(Context context, ArrayList<Agenda> Indica){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM FACTURAS_PUNTOS");
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM AGENDA");
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM VCLIENTES");
            for(int i=0;i<Indica.size();i++){
                Agenda a = Indica.get(i);
                //SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM AGENDA");
                ContentValues cntTop = new ContentValues();
                cntTop.put("IdPlan" , a.mIdPlan);
                cntTop.put("Vendedor" , a.mVendedor);
                cntTop.put("Ruta" , a.mRuta);
                cntTop.put("Inicia" , a.mInicia);
                cntTop.put("Termina" , a.mTermina);
                cntTop.put("Zona" , a.mZona);
                myDataBase.insert("AGENDA", null, cntTop );

                //SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM VCLIENTES");
                ContentValues cntDetalle = new ContentValues();
                cntDetalle.put("IdPlan" , a.mIdPlan);
                cntDetalle.put("Lunes" , a.mLunes);
                cntDetalle.put("Martes" , a.mMartes);
                cntDetalle.put("Miercoles" , a.mMiercoles);
                cntDetalle.put("Jueves" , a.mJueves);
                cntDetalle.put("Viernes" , a.mViernes);
                myDataBase.insert("VCLIENTES", null, cntDetalle );

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
    public static void  SaveAgenda(Context context, List<Map<String, Object>> mTop,  List<Map<String, Object>> mDetalle){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM AGENDA");
            String mIdpl = "PLN" + SQLiteHelper.getId(ManagerURI.getDirDb(), context, "PLAN");

            ContentValues cntTop = new ContentValues();
            cntTop.put("IdPlan" , mIdpl);
            cntTop.put("Vendedor" , mTop.get(0).get("Vendedor").toString());
            cntTop.put("Ruta" , mTop.get(0).get("mRuta").toString());
            cntTop.put("Inicia" , mTop.get(0).get("mSemanaIni").toString());
            cntTop.put("Termina" , mTop.get(0).get("mSemanaEnd").toString());
            cntTop.put("Zona" , mTop.get(0).get("mZona").toString());
            myDataBase.insert("AGENDA", null, cntTop );

            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM VCLIENTES");
            ContentValues cntDetalle = new ContentValues();
            cntDetalle.put("IdPlan" , mIdpl);
            cntDetalle.put("Lunes" , mDetalle.get(0).get("LUNES").toString());
            cntDetalle.put("Martes" , mDetalle.get(0).get("MARTES").toString());
            cntDetalle.put("Miercoles" , mDetalle.get(0).get("MIERCOLES").toString());
            cntDetalle.put("Jueves" , mDetalle.get(0).get("JUEVES").toString());
            cntDetalle.put("Viernes" , mDetalle.get(0).get("VIERNES").toString());
            myDataBase.insert("VCLIENTES", null, cntDetalle );


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

    public static void SaveLog(Context context, String tipo,String observacion) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();

            ContentValues cntDetalle = new ContentValues();
            cntDetalle.put("IdPlan" , preferences.getString("IDPLAN","0"));
            cntDetalle.put("IdCliente" , preferences.getString("ClsSelected","0"));
            cntDetalle.put("Fecha" , Clock.getNow());
            cntDetalle.put("Lati" ,preferences.getString("LATITUD","0"));
            cntDetalle.put("Logi" , preferences.getString("LONGITUD","0"));
            cntDetalle.put("Local" ,preferences.getString("LUGAR_VISITA","0"));
            cntDetalle.put("Inicio" , preferences.getString("INICIO","0"));
            cntDetalle.put("Fin" , preferences.getString("FINAL","0"));
            cntDetalle.put("Tipo" , tipo);
            cntDetalle.put("Observacion" , observacion);
            cntDetalle.put("Send" , "0");
            myDataBase.insert("VISITAS", null, cntDetalle );
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
    public static void borrar(Context context){
        SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM VISITAS WHERE Send = 1");
    }
}
