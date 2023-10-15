package com.launcher.anc.welcome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Welcome extends Fragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        FrameLayout frame = new FrameLayout(context);
        frame.setFitsSystemWindows(true);

        //Bienvenida.
        LinearLayout welcome = new LinearLayout(frame.getContext());

        //Preferencias

        //Tour

        return frame;
    }
}
