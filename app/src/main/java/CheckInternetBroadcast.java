import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.github.mikephil.charting.formatter.IFillFormatter;

/**
 * Created by alder.hernandez on 15/06/2017.
 */

public class CheckInternetBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
    int[] type = {ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};
        if (isNetworkAvaliable(context,type)){
            Toast.makeText(context, "Tiene conexion a internet!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "No Tiene Conexion!!!", Toast.LENGTH_SHORT).show();
        }
    }
    private  boolean isNetworkAvaliable(Context context, int[] typeNetworks){
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int typeNetwork: typeNetworks){
                NetworkInfo networkInfo = cm.getNetworkInfo(typeNetwork);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
                    return  true;
                }
            }
        }catch (Exception ex){
            return false;
        }
        return  false;
    }
}
