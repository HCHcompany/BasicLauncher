package com.launcher.anc.model;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

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
        return null;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<AppModel> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void onCanceled(@Nullable ArrayList<AppModel> data) {
        super.onCanceled(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
