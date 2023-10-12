package com.launcher.anc.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.launcher.anc.R;

import java.util.ArrayList;
import java.util.Collection;

public class AppListAdapter extends ArrayAdapter<AppModel> {

    private final LayoutInflater inflater;

    private final Context context;

    public AppListAdapter(Context context){
        super(context, android.R.layout.simple_list_item_2);//Por modificar
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<AppModel> data){
        if(data != null){
            this.clear();
            this.addAll(data);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addAll(Collection<? extends AppModel> items) {
        //Si la plataforma lo admite, use addAll; de lo contrario, agregue en bucle
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            super.addAll(items);
        }else{
            for(AppModel item: items){
                super.add(item);
            }
        }
    }

    //Completar nuevos elementos en la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.items, parent, false);
        } else {
            view = convertView;
        }

        AppModel item = getItem(position);
        ((ImageView)view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
        ((TextView)view.findViewById(R.id.text)).setText(item.getLabel());

        return view;
    }

}
