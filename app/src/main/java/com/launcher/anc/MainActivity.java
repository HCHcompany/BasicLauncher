package com.launcher.anc;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    @Override
    public void onBackPressed() {
        if(isHome){

        }else{
            
        }
        //super.onBackPressed();
    }
}