package com.launcher.anc.wagon;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;

public class WagonView extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GlobalSettings.WAGON_ACTIVITY = this;
        setContentView(R.layout.wagon);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
