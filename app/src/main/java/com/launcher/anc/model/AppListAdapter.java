package com.launcher.anc.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;

import java.util.ArrayList;
import java.util.Collection;

public class AppListAdapter extends ArrayAdapter<AppModel> {

    private final LayoutInflater inflater;
    private final Context context;

    private ArrayList<AppModel> data;

    public AppListAdapter(Context context){
        super(context, android.R.layout.simple_list_item_2);//Por modificar
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<AppModel> data){
        if(data != null){
            this.data = data;
            this.clear();
            this.addAll(data);
        }
    }

    public ArrayList<AppModel> getData(){
        return this.data;
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
        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView title = (TextView)view.findViewById(R.id.text);

        Log.i("GlobalSIze: ", "X: " + GlobalSettings.XDPI_ICON_GRID);
        icon.getLayoutParams().width = (int)GlobalSettings.XDPI_ICON_GRID;
        icon.getLayoutParams().height = (int)GlobalSettings.YDPI_ICON_GRID;
        //icon.setLayoutParams(new ViewGroup.LayoutParams(10, 10));

        //Añadir informacion.
        icon.setImageDrawable(item.getIcon());
        title.setText(item.getLabel());

        data.get(position).setTextView(title);
        data.get(position).setImageView(icon);
        return view;
    }
}
