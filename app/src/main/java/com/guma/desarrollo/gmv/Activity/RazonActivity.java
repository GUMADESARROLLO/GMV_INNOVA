package com.guma.desarrollo.gmv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.core.Actividades_model;
import com.guma.desarrollo.core.Clientes;
import com.guma.desarrollo.core.Clientes_model;
import com.guma.desarrollo.core.Clock;
import com.guma.desarrollo.core.ManagerURI;
import com.guma.desarrollo.core.Pedidos;
import com.guma.desarrollo.core.Pedidos_model;
import com.guma.desarrollo.core.Razon;
import com.guma.desarrollo.core.RazonDetalle;
import com.guma.desarrollo.core.Razon_model;
import com.guma.desarrollo.core.SQLiteHelper;
import com.guma.desarrollo.gmv.ActividadInfo;
import com.guma.desarrollo.gmv.Adapters.ActividadesAdapter;
import com.guma.desarrollo.gmv.Adapters.CustomAdapter;
import com.guma.desarrollo.gmv.CategoriaInfo;
import com.guma.desarrollo.gmv.ChildInfo;
import com.guma.desarrollo.gmv.GroupInfo;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RazonActivity extends AppCompatActivity {
    private LinkedHashMap<String, CategoriaInfo> subjects = new LinkedHashMap<>();
    private ArrayList<CategoriaInfo> deptList = new ArrayList<>();

    private ActividadesAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    private TextView tvCategoriaItem;
    private TextView tvActividadItem;
    private TextView tvIdAEItem;
    private CheckBox cbActividad;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String CodCls,IdRazon;

    private void loadActividades(){
        for (Actividad obj : Actividades_model.getActividades(ManagerURI.getDirDb(),RazonActivity.this))
        {
            addProduct(obj.getmCategoria(), obj.getmActividad(), obj.getmIdAE().toString());
        }
    }

    private int addProduct(String Categoria, String Actividad, String IdAE){
        int groupPosition = 0;
        CategoriaInfo headerInfo = subjects.get(Categoria);
        if (headerInfo==null)
        {
            headerInfo = new CategoriaInfo();
            headerInfo.setName(Categoria);
            subjects.put(Categoria, headerInfo);
            deptList.add(headerInfo);
        }
        ArrayList<ActividadInfo> productList =headerInfo.getActividadList();
        int listSize = productList.size();
        listSize++;
        ActividadInfo detailInfo = new ActividadInfo();
        detailInfo.setIdAE(IdAE);
        detailInfo.setActividad(Actividad);
        productList.add(detailInfo);
        headerInfo.setActividadesList(productList);
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razon);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setTitle(preferences.getString("NameClsSelected"," --ERROR--"));

        CodCls =  preferences.getString("ClsSelected","");

        loadActividades();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListViewRazon);
        listAdapter = new ActividadesAdapter(RazonActivity.this,deptList);
        simpleExpandableListView.setAdapter(listAdapter);
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CategoriaInfo headerInfo = deptList.get(groupPosition);
                ActividadInfo detailInfo = headerInfo.getActividadList().get(childPosition);
                return false;
            }
        });

        cbActividad = (CheckBox) findViewById(R.id.ActividadChk);

        findViewById(R.id.btnSaveRazon).setOnClickListener(new View.OnClickListener(){
            private String strIdAE="";
            private String strActividad="";
            private boolean strValor=false;
            @Override
            public void onClick(View v) {
                Razon ra = new Razon();
                ArrayList<RazonDetalle> rd = new ArrayList<RazonDetalle>();
                int key = SQLiteHelper.getId(ManagerURI.getDirDb(), RazonActivity.this, "RAZON");
                IdRazon = preferences.getString("VENDEDOR", "00") + "P" + Clock.getIdDate() + String.valueOf(key);
                ra.setmIdRazon(IdRazon);
                ra.setmVendedor("F09");
                ra.setmCliente(preferences.getString("ClsSelected",""));
                ra.setmNombre(preferences.getString("NameClsSelected"," --ERROR--"));
                ra.setmFecha(Clock.getNow());
                ra.setmObservacion("Observacion");
                for (int i=0;i<simpleExpandableListView.getCount()-1;i++)
                {
                    tvCategoriaItem = (TextView) simpleExpandableListView.getChildAt(i).findViewById(R.id.headingCategoria);
                    tvActividadItem = (TextView) simpleExpandableListView.getChildAt(i).findViewById(R.id.ActividadItem);
                    tvIdAEItem = (TextView) simpleExpandableListView.getChildAt(i).findViewById(R.id.IdAEItem);
                    cbActividad = (CheckBox) simpleExpandableListView.getChildAt(i).findViewById(R.id.ActividadChk);
                    //Agregar los detalles
                    if (tvActividadItem !=null)
                    {
                        if (cbActividad.isChecked())
                        {
                            rd.add(new RazonDetalle(IdRazon,tvIdAEItem.getText().toString(), tvActividadItem.getText().toString(),""));
                            ra.setRdet(rd);
                        }
                    }
                }
                Razon_model.SaveRazon(RazonActivity.this,ra);
            }
        });
    }
    public void guardar(){

            for (int i=0;i<simpleExpandableListView.getCount();i++)
            {
                tvActividadItem = (TextView) simpleExpandableListView.getChildAt(i).findViewById(R.id.ActividadItem);
                if (tvActividadItem !=null)
                    Toast.makeText(RazonActivity.this,tvActividadItem.getText(), Toast.LENGTH_LONG);
            }
    }

}
