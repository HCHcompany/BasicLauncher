package com.launcher.anc.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.launcher.anc.model.AppModel;

public class HomeListener implements AdapterView.OnItemClickListener{

    private final Context context;

    public HomeListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        AppModel app = (AppModel) adapterView.getItemAtPosition(i);
        if(app != null){
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.getApplicationPackageName());
            if(intent != null){
                context.startActivity(intent);
            }
        }
    }
}
