package com.guma.desarrollo.gmv.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.gmv.Constants;
import com.guma.desarrollo.gmv.api.DetectedActivitiesIntentService;
import com.guma.desarrollo.gmv.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MarcarRegistroActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<Status> {

    private static final String TAG = MarcarRegistroActivity.class.getSimpleName();

    private static final String LOCATION_KEY = "location-key";

    // Location API
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mLastLocation;
    private TextView mLatitude,mLongitude,textView,txtLati,txtlongi,mInlocal1,mInlocal2;

    TextView textViewTime;
    // Activity Recognition API
    private ActivityDetectionBroadcastReceiver mBroadcastReceiver;

    Button btn_step_2;
    String strgInit;

    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private AssetManager assetMgr;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle(" [ PASO 1 - Registrar Visita ] - " + preferences.getString("NameClsSelected"," --ERROR--"));


        mLatitude = (TextView) findViewById(R.id.txtlati);
        txtLati = (TextView) findViewById(R.id.lblLati);
        mLongitude = (TextView) findViewById(R.id.txtlongi);
        txtlongi = (TextView) findViewById(R.id.lblLongi);

        mInlocal1= (TextView) findViewById(R.id.txtInlocal1);
        mInlocal2= (TextView) findViewById(R.id.txtInlocal2);

        btn_step_2 = (Button) findViewById(R.id.btnGoToStep2);
        btn_step_2.setEnabled(true);
        textView = (TextView) findViewById(R.id.idTimer);
        strgInit = Clock.getNow();
        editor.putString("iniTimer", strgInit);
        timer = new Timer();

        assetMgr = getResources().getAssets();
        mLatitude.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        mLongitude.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        mInlocal1.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        mInlocal2.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));
        btn_step_2.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_medium.ttf"));


        txtLati.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));
        txtlongi.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));

        textViewTime = (TextView) findViewById(R.id.idTimer);

        btn_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitud = mLatitude.getText().toString();
                //if (latitud.equals("0.0")) {
                    //Toast.makeText(MarcarRegistroActivity.this, "coordenadas no encontradas", Toast.LENGTH_SHORT).show();
                //}else {
                    editor.putString("LATITUD", mLatitude.getText().toString());
                    editor.putString("LONGITUD", mLongitude.getText().toString());



                    RadioButton local = (RadioButton) findViewById(R.id.inLocal);
                    if (local.isChecked()) {
                        editor.putString("LUGAR_VISITA", "LOCAL");
                    } else {
                        editor.putString("LUGAR_VISITA", "NOLOCAL");
                    }
                    editor.apply();
                    startActivity(new Intent(MarcarRegistroActivity.this, AccionesActivity.class));
                    timer.cancel();
                    finish();
                    //Toast.makeText(MarcarRegistroActivity.this, "entro exitosamente", Toast.LENGTH_SHORT).show();
                //}


            }
        });

        findViewById(R.id.iv_callQr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MarcarRegistroActivity.this, QrActivity.class), 0);

            }
        });
        RadioButton rb = (RadioButton) findViewById(R.id.inLocal);
        rb.setChecked(true);

        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        updateValuesFromBundle(savedInstanceState);

       /* new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       // updateLocationUI();
                    }
                });
            }
        }, 0, 5000);*/

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);

    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            textView.setText(Clock.getDiferencia(Clock.StringToDate(strgInit,"yyyy-mm-dd HH:mm:ss"),Clock.StringToDate(Clock.getNow(),"yyyy-mm-dd HH:mm:ss"),"Timer"));
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(MarcarRegistroActivity.this,AgendaActivity.class));
            timer.cancel();
            finish();
            return true;
        }
        return false;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.inLocal:
                if (checked)
                    break;
            case R.id.outLocal:
                if (checked)
                    break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
            stopActivityUpdates();
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            startActivityUpdates();
        }

        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LOCATION_KEY, mLastLocation);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            try {
                JSONArray json_array = new JSONObject(data.getStringExtra("myResulte")).optJSONArray("Cliente");
                updateUIQR(json_array.getJSONObject(0).getString("mLogi"),json_array.getJSONObject(0).getString("mLati"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        processLastLocation();
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        timer.cancel();
                        finish();
                        break;
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startLocationUpdates();

            } else {
                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .enableAutoManage(this, this)
                .build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(Constants.UPDATE_INTERVAL)
                .setFastestInterval(Constants.UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest).setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient, mLocationSettingsRequest
                );

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Los ajustes de ubicación satisfacen la configuración.");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.d(TAG, "Los ajustes de ubicación no satisfacen la configuración. " +
                                    "Se mostrará un diálogo de ayuda.");
                            status.startResolutionForResult(
                                    MarcarRegistroActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.d(TAG, "El Intent del diálogo no funcionó.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d(TAG, "Los ajustes de ubicación no son apropiados.");
                        break;

                }
            }
        });
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LOCATION_KEY)) {
                mLastLocation = savedInstanceState.getParcelable(LOCATION_KEY);
                updateLocationUI();
            }
        }
    }

    private void updateLocationUI() {
        mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
        btn_step_2.setEnabled(true);
        btn_step_2.setBackgroundResource(R.drawable.inn_btn_morado);
        btn_step_2.setTextColor(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.white))));
    }
    private void updateUIQR(String Longi,String Lati) {
        mLatitude.setText(String.valueOf(Lati));
        mLongitude.setText(String.valueOf(Longi));
        btn_step_2.setEnabled(true);
        btn_step_2.setBackgroundResource(R.drawable.button_primary);
        btn_step_2.setTextColor(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.white))));
    }



    private void stopActivityUpdates() {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                mGoogleApiClient,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi
                .removeLocationUpdates(mGoogleApiClient, this);
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            manageDeniedPermission();
        }
    }

    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }

    private void startActivityUpdates() {
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                mGoogleApiClient,
                Constants.ACTIVITY_RECOGNITION_INTERVAL,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
    }

    private void startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            manageDeniedPermission();
        }
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        processLastLocation();
        startLocationUpdates();
        startActivityUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Conexión suspendida");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /*Toast.makeText(
                this,
                "Error de conexión con el código:" + connectionResult.getErrorCode(),
                Toast.LENGTH_LONG)
                .show();*/

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, String.format("Nueva ubicación: (%s, %s)",
                location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        updateLocationUI();
    }


    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Log.d(TAG, "Detección de actividad iniciada");

        } else {
            Log.e(TAG, "Error al iniciar/remover la detección de actividad: "
                    + status.getStatusMessage());
        }
    }
    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }

    }
}
