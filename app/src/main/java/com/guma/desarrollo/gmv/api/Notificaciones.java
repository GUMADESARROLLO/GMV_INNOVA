package com.guma.desarrollo.gmv.api;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by maryan.espinoza on 10/03/2017.
 */

public class Notificaciones {
    public Snackbar snackieBar(Activity activity, String aText, int aBgColor, int aTextColor, int aButtonColor){
        Snackbar snackie = Snackbar.make(activity.findViewById(android.R.id.content), aText, Snackbar.LENGTH_LONG);
        View snackView = snackie.getView();
        TextView snackViewText = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        Button snackViewButton = (Button) snackView.findViewById(android.support.design.R.id.snackbar_action);
        snackView.setBackgroundColor(aBgColor);
        snackViewText.setTextColor(aTextColor);
        snackViewButton.setTextColor(aButtonColor);
        return snackie;
    }
    public AlertDialog.Builder Alert(Activity activity,String Titulos,String Mensaje){
        return new AlertDialog.Builder(activity).setTitle(Titulos).setMessage(Mensaje).setCancelable(false).setPositiveButton("OK",null);
    }
}