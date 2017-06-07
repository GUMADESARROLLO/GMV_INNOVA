package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.guma.desarrollo.core.Actividad;
import com.guma.desarrollo.gmv.ActividadInfo;
import com.guma.desarrollo.gmv.CategoriaInfo;
import com.guma.desarrollo.gmv.R;

import java.util.ArrayList;

/**
 * Created by luis.perez on 25/03/2017.
 */

public class ActividadesAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<CategoriaInfo> deptList;

    public ActividadesAdapter(Context context, ArrayList<CategoriaInfo> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ActividadInfo> productList = deptList.get(groupPosition).getActividadList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent)
    {
        ActividadInfo detailInfo = (ActividadInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = infalInflater.inflate(R.layout.child_items, null);
            view = infalInflater.inflate(R.layout.actividad_items, null);
        }


        //TextView childItem = (TextView) view.findViewById(R.id.childItem);
        //childItem.setText(detailInfo.getmActividad().trim());
        TextView IdAEItem = (TextView) view.findViewById(R.id.IdAEItem);
        IdAEItem.setText(detailInfo.getIdAE());
        TextView ActividadItem = (TextView) view.findViewById(R.id.ActividadItem);
        ActividadItem.setText(detailInfo.getActividad());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ActividadInfo> productList = deptList.get(groupPosition).getActividadList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        CategoriaInfo headerInfo = (CategoriaInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = inf.inflate(R.layout.group_items, null);
            view = inf.inflate(R.layout.categoria_items, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.headingCategoria);
        heading.setText(headerInfo.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
