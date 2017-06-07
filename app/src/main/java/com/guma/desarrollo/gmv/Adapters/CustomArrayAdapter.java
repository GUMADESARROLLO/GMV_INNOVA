package com.guma.desarrollo.gmv.Adapters;

import java.util.List;

//import com.danielme.blog.demo.listviewcheckbox.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.core.Row;
import com.guma.desarrollo.gmv.R;

/**
 * Created by luis.perez on 03/04/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<Row> implements View.OnClickListener {

    private LayoutInflater layoutInflater;
    private AssetManager assetMgr;

    TextView textViewTitle,textViewSubtitle,textViewSubSubTitle;
    CheckBox checkBox;
    public CustomArrayAdapter(Context context, List<Row> objects){
        super(context, 0, objects);
        layoutInflater=LayoutInflater.from(context);
        assetMgr = context.getResources().getAssets();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_row, parent, false);
            textViewTitle=((TextView) convertView.findViewById(R.id.textViewTitle));
            textViewSubtitle=((TextView) convertView.findViewById(R.id.textViewSubtitle));
            textViewSubSubTitle=((TextView) convertView.findViewById(R.id.textViewSubSubtitle));
            checkBox = ((CheckBox) convertView.findViewById(R.id.checkBox));
        }


        final Row row = getItem(position);
        textViewTitle.setText(row.getTitle());
        textViewSubSubTitle.setText(row.getSubtitle());
        textViewSubSubTitle.setText(row.getSubsubtitle());

        textViewTitle.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_bold.ttf"));
        textViewSubtitle.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));
        textViewSubSubTitle.setTypeface(Typeface.createFromAsset(assetMgr ,"fonts/roboto_light_italic.ttf"));

        checkBox.setTag(position);
        checkBox.setChecked(row.isChecked());
        checkBox.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();
        getItem(position).setChecked(checkBox.isChecked());
    }


}
