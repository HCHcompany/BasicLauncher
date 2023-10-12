package com.launcher.anc.old.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class HomeGridViewListener implements AdapterView.OnItemClickListener{
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("Home Item Listener", "Se ha presionado correctamente.");
    }
}
