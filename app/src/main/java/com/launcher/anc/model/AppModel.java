package com.launcher.anc.model;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class AppModel {
    private final Context mContext; //Contexto de actividad.
    private final ApplicationInfo mInfo; //Informacion de la app.

    private String mAppLabel; //Nombre de la app.
    private Drawable mIcon; //Icono de la app.

    private boolean mMounted; //montada?
    private final File mApkFile; //El archivo apk.

    private TextView txtv = null;
    private ImageView imgv = null;
    private boolean selected = false;

    public AppModel(Context context, ApplicationInfo info){
        mContext = context;
        mInfo = info;
        mApkFile = new File(info.sourceDir);
    }

    public ApplicationInfo getAppInfo() {
        return mInfo;
    }

    public String getApplicationPackageName() {
        return getAppInfo().packageName;
    }

    public String getLabel() {
        return mAppLabel;
    }

    public void setTextView(TextView txt){
        if(this.txtv == null){
            this.txtv = txt;
        }
    }

    public TextView getTextView(){
        return this.txtv;
    }

    public void setImageView(ImageView img){
        if(this.imgv == null){
            this.imgv = img;
        }
    }

    public ImageView getImageView(){
        return this.imgv;
    }

    public Drawable getIcon() {
        if (mIcon == null) {
            if (mApkFile.exists()) {
                mIcon = mInfo.loadIcon(mContext.getPackageManager());
                return mIcon;
            } else {
                mMounted = false;
            }
        } else if (!mMounted) {
            // If the app wasn't mounted but is now mounted, reload
            // its icon.
            if (mApkFile.exists()) {
                mMounted = true;
                mIcon = mInfo.loadIcon(mContext.getPackageManager());
                return mIcon;
            }
        } else {
            return mIcon;
        }

        return mContext.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
    }

    public boolean isSelected(){
        return this.selected;
    }

    public void setSelected(boolean bln){
        this.selected = bln;
    }

    void loadLabel(Context context) {
        if (mAppLabel == null || !mMounted) {
            if (!mApkFile.exists()) {
                mMounted = false;
                mAppLabel = mInfo.packageName;
            } else {
                mMounted = true;
                CharSequence label = mInfo.loadLabel(context.getPackageManager());
                mAppLabel = label != null ? label.toString() : mInfo.packageName;
            }
        }
    }
}

