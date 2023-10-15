package com.launcher.anc.welcome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;

public class WelcomeView extends FragmentActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GlobalSettings.WELCOME_ACTIVITY = this;
        setContentView(R.layout.welcome);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
