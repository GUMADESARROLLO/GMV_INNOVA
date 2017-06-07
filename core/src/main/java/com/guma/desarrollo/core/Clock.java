package com.guma.desarrollo.core;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Clock {
    public static String getNow() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y-%m-%d %T");
        return sTime;
    }
    public static String getTMD() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y-%m-%d");
        return sTime;
    }
    public static String getTime(){
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%H:%M:%S");
        return sTime;
    }
    public static String getTimeStamp() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y-%m-%d %H:%M:%S");
        return sTime;
    }
    public static String getIdDate() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%d%m%y");
        return sTime;
    }
    public static String getDiferencia(Date fechaInicial, Date fechaFinal, String parametro){

        long diferencia = fechaFinal.getTime() - fechaInicial.getTime();

        long segsMilli = 1000;
        long minsMilli = segsMilli * 60;
        long horasMilli = minsMilli * 60;
        long diasMilli = horasMilli * 24;

        long diasTranscurridos = diferencia / diasMilli;
        diferencia = diferencia % diasMilli;

        long horasTranscurridos = diferencia / horasMilli;
        diferencia = diferencia % horasMilli;

        long minutosTranscurridos = diferencia / minsMilli;
        diferencia = diferencia % minsMilli;

        long segsTranscurridos = diferencia / segsMilli;

        //return "diasTranscurridos: " + diasTranscurridos + " , horasTranscurridos: " + horasTranscurridos +" , minutosTranscurridos: " + minutosTranscurridos + " , segsTranscurridos: " + segsTranscurridos;

        if (parametro.equals("Hrs")){
            return String.valueOf(horasTranscurridos);
        }else {
            if (parametro.equals("Dias")){
                return String.valueOf(diasTranscurridos);
            }else{
                if (parametro.equals("Timer")){
                    String hrs,mint,Segu;
                    if (horasTranscurridos <= 9){
                        hrs = "0" + String.valueOf(horasTranscurridos);
                    }else{
                        hrs = String.valueOf(horasTranscurridos);
                    }
                    if (minutosTranscurridos <= 9){
                        mint = "0" + String.valueOf(minutosTranscurridos);
                    }else{
                        mint = String.valueOf(minutosTranscurridos);
                    }
                    if (segsTranscurridos <= 9){
                        Segu = "0" + String.valueOf(segsTranscurridos);
                    }else{
                        Segu = String.valueOf(segsTranscurridos);
                    }
                    return hrs +":"+ mint + ":" + Segu;
                }
            }

        }
        return "";

    }
    public static Date StringToDate(String dateInString,String Formato){
        SimpleDateFormat formatter = new SimpleDateFormat(Formato);
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getMonth(Context cnxt, String Date){
        return cnxt.getResources().getStringArray(R.array.meses)[Integer.parseInt(Date.substring(3,5))];
    }

    public static String getMes(Date date,String rtn) {

        return (String) android.text.format.DateFormat.format(rtn, date);

    }


}
