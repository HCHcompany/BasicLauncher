package com.launcher.anc.model;

import android.content.Context;
import android.widget.ArrayAdapter;

public class AppListAdapter extends ArrayAdapter<AppModel> {

    private final Context context;

    public AppListAdapter(Context context){
        super(context, android.R.layout.simple_list_item_2);//Por modificar
        this.context = context;
    }
}
