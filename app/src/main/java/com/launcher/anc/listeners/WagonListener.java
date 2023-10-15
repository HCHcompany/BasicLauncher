package com.launcher.anc.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.model.AppModel;

public class WagonListener implements AdapterView.OnItemClickListener {
    private final Context context;

    public WagonListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        AppModel app = (AppModel) adapterView.getItemAtPosition(i);
        if(app != null){
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.getApplicationPackageName());
            if(intent != null){
                context.startActivity(intent);
                if(GlobalSettings.WAGON_ACTIVITY != null){
                    GlobalSettings.WAGON_ACTIVITY.finish();
                    GlobalSettings.WAGON_ACTIVITY = null;
                }
            }
        }
    }
}
