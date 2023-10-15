package com.launcher.anc.home;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;

public class HomeView extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GlobalSettings.HOME_ACTIVITY = this;
        setContentView(R.layout.home);
    }

    @Override
    public void onBackPressed(){}
}
