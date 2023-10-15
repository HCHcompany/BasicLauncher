package com.launcher.anc.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.model.AppListAdapter;
import com.launcher.anc.model.AppLoader;
import com.launcher.anc.model.AppModel;
import com.launcher.anc.model.GridFragmentBase;

import java.util.ArrayList;

public class Home extends GridFragmentBase{

    private AppListAdapter adapter;
    private Context context;

    public Home(){
        super(GlobalSettings.HOME_ACTIVITY, GlobalSettings.HOME_INSTANCE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        setEmptyText(GlobalSettings.EMPTY_MESSAGE_DEFAULT_GRIDVIEW);//Mostrar mensaje por defecto.

        adapter = new AppListAdapter(context);
        setGridAdapter(adapter);

        // Hasta que se cargan los datos muestra una ruleta o pantalla de carga
        setGridShown(true);
    }
}
