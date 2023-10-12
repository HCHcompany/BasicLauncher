package com.launcher.anc.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppLoader extends AsyncTaskLoader<ArrayList<AppModel>> {

    private final Context context;
    private ArrayList<AppModel> installedApps;
    private final PackageManager pm;
    private PackageReceiver receiver;
    public static final Comparator<AppModel> ALPHA_COMPARATOR = new Comparator<AppModel>() {
        private final Collator collator = Collator.getInstance();
        @Override
        public int compare(AppModel appModel, AppModel t1) {
            return collator.compare(appModel.getLabel(), t1.getLabel());
        }
    };

    public AppLoader(Context context) {
        super(context);
        this.context = context;
        this.pm = this.context.getPackageManager();
    }

    @Nullable
    @Override
    public ArrayList<AppModel> loadInBackground() {
        //Recuperar la lista de aplicaciones instaladas.
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        if(apps == null){
            apps = new ArrayList<ApplicationInfo>();
        }

        //Añadir las aplicaciones correspondientes y cargar sus etiquetas.
        ArrayList<AppModel> items = new ArrayList<AppModel>();
        for(int i = 0; i < apps.size(); i++){
            String pkg = apps.get(i).packageName;

            //Añadir solo aplicaciones que se pueden iniciar.
            if(context.getPackageManager().getLaunchIntentForPackage(pkg) != null){
               AppModel app = new AppModel(getContext(), apps.get(i));
               app.loadLabel(getContext());
               items.add(app);
            }//Se pueden añadir en otra lista las apps que no se pueden iniciar.
        }

        //Ordenar la lista.
        Collections.sort(items, ALPHA_COMPARATOR);
        return items;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<AppModel> apps){
        if(isReset()){
            if(apps != null){
                onReleaseResouces(apps);
            }
        }

        ArrayList<AppModel> oldApps = apps;
        installedApps = apps;

        if(isStarted()){
            // Si el cargador está actualmente iniciado, podemos inmediatamente entregar sus resultados.
            super.deliverResult(apps);
        }

        // En este punto podemos liberar los recursos asociados con
        // 'oldApps' si es necesario; Ahora que se entrega el nuevo resultado,
        // sabemos que ya no está en uso.
        if(oldApps != null){
            onReleaseResouces(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if(installedApps != null){
            // Si actualmente tenemos un resultado disponible, entrégarlo inmediatamente.
            deliverResult(installedApps);
        }

        // Recibir los cambios en la operación de instalación y desinstalación de aplicaciones.
        if(receiver == null){
           receiver = new PackageReceiver(this);
        }

        if(takeContentChanged() || installedApps == null){
            // Si los datos han cambiado desde la última vez que se cargaron
            // o no está disponible actualmente, inicia una carga.
            forceLoad();
        }

        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad(); //Cancelar carga.
    }

    @Override
    public void onCanceled(@Nullable ArrayList<AppModel> apps) {
        super.onCanceled(apps);
        onReleaseResouces(apps);
    }

    @Override
    protected void onReset() {
        // Detener el cargador.
        onStopLoading();

        // En este punto podemos liberar los recursos asociados con las 'aplicaciones' si es necesario.
        if(installedApps != null){
            onReleaseResouces(installedApps);
            installedApps = null;
        }

        // Detener el servicio de recibimiento de informacion de apps.
        if(receiver != null){
            getContext().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    //Método auxiliar para hacer el trabajo de limpieza si es necesario, por ejemplo si estamos usando Cursor, entonces deberíamos cerrarlo aquí.
    protected void onReleaseResouces(ArrayList<AppModel> apps){}
}
