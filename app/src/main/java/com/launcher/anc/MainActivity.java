package com.launcher.anc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.launcher.anc.home.HomeView;
import com.launcher.anc.welcome.WelcomeView;

public class MainActivity extends Activity{

    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GlobalSettings.MAIN_ACTIVITY = this;

        if(GlobalSettings.isFirstUser(this)){
            Intent i = new Intent(this, WelcomeView.class);
            startActivity(i);
        }else{
            if(GlobalSettings.GlobalSettingsLoad(this)){
                Intent i = new Intent(this, HomeView.class);
                startActivity(i);
            }else{
                Intent i = new Intent(this, WelcomeView.class);
                startActivity(i);
            }
        }
    }
}