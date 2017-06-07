package com.guma.desarrollo.gmv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by Maryan Espinoza on 19/02/2017.
 */

public class LeadsAdapter extends ArrayAdapter<Lead> {
    public LeadsAdapter(Context context, List<Lead> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item,parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.tv_name_ART);
        TextView title = (TextView) convertView.findViewById(R.id.txtCodigo);
        TextView company = (TextView) convertView.findViewById(R.id.txtPrecio);

        Lead lead = getItem(position);
        name.setText(lead.getName());
        title.setText(lead.getTitle());
        company.setText(lead.getCompany());

        return convertView;
    }
}