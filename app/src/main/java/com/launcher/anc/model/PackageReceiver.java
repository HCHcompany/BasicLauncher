package com.launcher.anc.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Esta clase esta dirigida a recibir informacion de las aplicaciones por medio de filtros
 * Sirve para actualizar informacion de nuevas aplicaciones y de las que son removidas
 * Ademas sirve para validar si una aplicacion se encuentra disponible, esta opcion es por si se encuentra
 * instalada en la tarjeta SD.*/
public class PackageReceiver extends BroadcastReceiver{

    private final AppLoader loader; //Cargador de apllicaciones.

    public PackageReceiver(AppLoader loader){
        this.loader = loader;

        //Filtro para recibir actualizaciones de aplicaciones removidas o actualizadas.
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        this.loader.getContext().registerReceiver(this, filter);

        //Regístrar eventos relacionados con la instalación de apps en la tarjeta SD.
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        this.loader.getContext().registerReceiver(this, sdFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent){
        this.loader.onContentChanged();
    }
}
