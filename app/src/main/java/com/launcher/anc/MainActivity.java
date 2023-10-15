package com.launcher.anc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.launcher.anc.home.HomeView;
import com.launcher.anc.welcome.WelcomeView;

public class MainActivity extends Activity{

    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, WelcomeView.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if(isHome){

        }else{
            
        }
        //super.onBackPressed();
    }
}