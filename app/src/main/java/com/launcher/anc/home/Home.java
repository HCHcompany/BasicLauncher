package com.launcher.anc.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.model.AppListAdapter;
import com.launcher.anc.model.AppModel;
import com.launcher.anc.model.GridFragmentBase;

import java.util.ArrayList;

public class Home extends GridFragmentBase implements LoaderManager.LoaderCallbacks<ArrayList<AppModel>>{

    private AppListAdapter adapter;
    private Context context;

    public Home(){
        super(GlobalSettings.HOME_INSTANCE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        setEmptyText(GlobalSettings.EMPTY_MESSAGE_DEFAULT_GRIDVIEW);//Mostrar mensaje por defecto.

        adapter = new AppListAdapter(context);
        setGridAdapter(adapter);

        // Hasta que se cargan los datos muestra una ruleta o pantalla de carga
        setGridShown(false);

        // Crear el cargador para cargar la lista de aplicaciones en segundo plano.
        getLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<AppModel>> loader){

    }
}
