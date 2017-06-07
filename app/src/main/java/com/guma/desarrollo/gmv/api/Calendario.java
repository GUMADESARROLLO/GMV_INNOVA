package com.guma.desarrollo.gmv.api;

/**
 * Created by maryan.espinoza on 14/03/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by marangelo.php on 14/07/2016.
 */
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.guma.desarrollo.gmv.R;

import java.util.Calendar;

public class Calendario extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    int frm;
    public int getFrm() {
        return frm;
    }

    public void setFrm(int frm) {
        this.frm = frm;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        EditText tv1 = (EditText) getActivity().findViewById(R.id.startPlan);
        EditText tv2 = (EditText) getActivity().findViewById(R.id.endPlan);
        String mes,dia;
        if (view.getDayOfMonth()<=9){
            dia = "0" + String.valueOf(view.getDayOfMonth());
        }else{
            dia = String.valueOf(view.getDayOfMonth());
        }

        if (view.getMonth()<=9){
            mes = "0" + String.valueOf(view.getMonth());
        }else{
            mes = String.valueOf(view.getMonth());
        }
        String Fecha = view.getYear()+"-"+mes+"-"+dia;

        if (getFrm()==1){
            tv1.setText(Fecha);
        }else{
            tv2.setText(Fecha);
        }
    }

}
