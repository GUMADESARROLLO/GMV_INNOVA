package com.guma.desarrollo.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by maryan.espinoza on 02/03/2017.
 */

public class Clientes_model {
    public static void  SaveMora(Context context, ArrayList<Mora> MORAS){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM CLIENTES_MORA");
            for(int i=0;i<MORAS.size();i++){
                Mora a = MORAS.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("VENCIDOS" , a.getmVencidos());
                contentValues.put("D30" , a.getmD30());
                contentValues.put("D60" , a.getmD60());
                contentValues.put("D90" , a.getmD90());
                contentValues.put("D120" , a.getmD120());
                contentValues.put("MD120" , a.getmMd120());
                myDataBase.insert("CLIENTES_MORA", null, contentValues );
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
    public static List<Mora> getMora(String basedir, Context context,String Cliente) {
        List<Mora> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES_MORA", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Mora tmp = new Mora();
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmVencidos(cursor.getString(cursor.getColumnIndex("VENCIDOS")));
                    tmp.setmD30(cursor.getString(cursor.getColumnIndex("D30")));
                    tmp.setmD60(cursor.getString(cursor.getColumnIndex("D60")));
                    tmp.setmD90(cursor.getString(cursor.getColumnIndex("D90")));
                    tmp.setmD120(cursor.getString(cursor.getColumnIndex("D120")));
                    tmp.setmMd120(cursor.getString(cursor.getColumnIndex("MD120")));
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
    public static void  SaveIndicadores(Context context, ArrayList<Indicadores> Indica){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM CLIENTES_INDICADORES");
            for(int i=0;i<Indica.size();i++){
                Indicadores a = Indica.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                contentValues.put("META" , a.getmMetas());
                contentValues.put("VENTAACTUAL" , a.getmVentasActual());
                contentValues.put("VENTAS3M" , a.getmPromedioVenta3M());
                contentValues.put("ITEM3M" , a.getmCantidadItems3M());
                contentValues.put("CUMPLIMIENTO" , a.getmCumplimiento());
                myDataBase.insert("CLIENTES_INDICADORES", null, contentValues );
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
    public static void  SaveClientes(Context context, ArrayList<Clientes> Indica){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM CLIENTES");
            for(int i=0;i<Indica.size();i++){
                Clientes a = Indica.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("DIRECCION" , a.getmDireccion());
                contentValues.put("RUC" , a.getmRuc());
                contentValues.put("PUNTOS" , a.getmPuntos());
                contentValues.put("MOROSO" , a.getmMoroso());
                contentValues.put("CREDITO" , a.getmCredito());
                contentValues.put("SALDO" , a.getmSaldo());
                contentValues.put("DISPONIBLE" , a.getmDisponible());
                contentValues.put("CUMPLE" , a.getmCumple());
                contentValues.put("Mes" , a.getmMes());
                myDataBase.insert("CLIENTES", null, contentValues );

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
    public static void  SaveHistorialCompra(Context context, ArrayList<Historial> Indica){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM HSTCOMPRA");
            for(int i=0;i<Indica.size();i++){
                Historial a = Indica.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("ARTICULO" , a.getmArticulo());
                contentValues.put("NOMBRE" , a.getmNombre());
                contentValues.put("CANTIDAD" , a.getmCantidad());
                contentValues.put("FECHA" , a.getmFecha());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("VENDEDOR" , a.getmVendedor());
                myDataBase.insert("HSTCOMPRA", null, contentValues );
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
    public static List<Historial> getHistorial(String basedir, Context context, String Cliente) {
        List<Historial> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "HSTCOMPRA", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Historial tmp = new Historial();
                    tmp.setmArticulo(cursor.getString(cursor.getColumnIndex("ARTICULO")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmCantidad(cursor.getString(cursor.getColumnIndex("CANTIDAD")));
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("VENDEDOR")));
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
    public static void  SaveFacturas(Context context, ArrayList<Facturas> Indica){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(ManagerURI.getDirDb(), context);
            myDataBase = myDbHelper.getWritableDatabase();
            SQLiteHelper.ExecuteSQL(ManagerURI.getDirDb(), context,"DELETE FROM FACTURAS_PUNTOS");
            for(int i=0;i<Indica.size();i++){
                Facturas a = Indica.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("FECHA" , a.getmFecha());
                contentValues.put("CLIENTE" , a.getmCliente());
                contentValues.put("FACTURA" , a.getmFactura());
                contentValues.put("PUNTOS" , a.getmPuntos());
                contentValues.put("REMANENTE" , a.getmRemanente());
                myDataBase.insert("FACTURAS_PUNTOS", null, contentValues );
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
    public static List<Facturas> getFacturas(String basedir, Context context, String Cliente) {
        List<Facturas> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "FACTURAS_PUNTOS", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Facturas tmp = new Facturas();
                    tmp.setmFecha(cursor.getString(cursor.getColumnIndex("FECHA")));
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmFactura(cursor.getString(cursor.getColumnIndex("FACTURA")));
                    tmp.setmPuntos(cursor.getString(cursor.getColumnIndex("PUNTOS")));
                    tmp.setmRemanente(cursor.getString(cursor.getColumnIndex("REMANENTE")));
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

    public static List<Indicadores> getIndicadores(String basedir, Context context, String Cliente) {
        List<Indicadores> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES_INDICADORES", null, "CLIENTE"+ "=?", new String[] { Cliente }, null, null, null, null);
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Indicadores tmp = new Indicadores();
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmVendedor(cursor.getString(cursor.getColumnIndex("VENDEDOR")));
                    tmp.setmMetas(cursor.getString(cursor.getColumnIndex("META")));
                    tmp.setmVentasActual(cursor.getString(cursor.getColumnIndex("VENTAACTUAL")));
                    tmp.setmPromedioVenta3M(cursor.getString(cursor.getColumnIndex("VENTAS3M")));
                    tmp.setmCantidadItems3M(cursor.getString(cursor.getColumnIndex("ITEM3M")));
                    tmp.setmCumplimiento(cursor.getString(cursor.getColumnIndex("CUMPLIMIENTO")));
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
    public static List<Clientes> getInfoCliente(String basedir, Context context, String IdCliente) {
        List<Clientes> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();

            Cursor cursor = myDataBase.query(true, "CLIENTES", null, "CLIENTE"+ "=?", new String[] { IdCliente }, null, null, null, null);
            Log.d(TAG, "getAgenda: "+ IdCliente + " " +cursor.getCount());
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                    Clientes tmp = new Clientes();
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmDireccion(cursor.getString(cursor.getColumnIndex("DIRECCION")));
                    tmp.setmRuc(cursor.getString(cursor.getColumnIndex("RUC")));
                    tmp.setmPuntos(cursor.getString(cursor.getColumnIndex("PUNTOS")));
                    tmp.setmMoroso(cursor.getString(cursor.getColumnIndex("MOROSO")));
                    tmp.setmCredito(cursor.getString(cursor.getColumnIndex("CREDITO")));
                    tmp.setmSaldo(cursor.getString(cursor.getColumnIndex("SALDO")));
                    tmp.setmDisponible(cursor.getString(cursor.getColumnIndex("DISPONIBLE")));
                    tmp.setmCumple(cursor.getString(cursor.getColumnIndex("CUMPLE")));
                    Log.d("", "alderPrubea: "+cursor.getString(cursor.getColumnIndex("CUMPLE")));
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
    public static List<Clientes> getClientes(String basedir, Context context, String Order) {
        List<Clientes> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.query(true, "CLIENTES", null, null, null, null, null, Order, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Clientes tmp = new Clientes();
                    tmp.setmCliente(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setmNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setmDireccion(cursor.getString(cursor.getColumnIndex("DIRECCION")));
                    tmp.setmCumple(cursor.getString(cursor.getColumnIndex("CUMPLE")));
                    tmp.setmMoroso(cursor.getString(cursor.getColumnIndex("MOROSO")));
                    tmp.setmMes(cursor.getInt(cursor.getColumnIndex("Mes")));

                    tmp.setmCredito(cursor.getString(cursor.getColumnIndex("CREDITO")));
                    tmp.setmSaldo(cursor.getString(cursor.getColumnIndex("SALDO")));
                    tmp.setmDisponible(cursor.getString(cursor.getColumnIndex("DISPONIBLE")));



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