package com.launcher.anc;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.model.AppGridView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalSettings{
    //Global Constant.
    public static final String EMPTY_MESSAGE_DEFAULT_GRIDVIEW = "No existen aplicaciones instaladas."; //Mensaje general de vacio.
    public static final int WIDTH_COLUMS_GRID_APPS = 60; // 60, 70 o adaptativo.
    public static final int SPACES_HORIZONTAL_GRID_APPS = 10; // Espacio por cada item de manera horizontal.
    public static final int SPACES_VERTICAL_GRID_APPS = 10; // Espacio por cada item de manera vertical.

    //Global Variables
    public static float XDPI_ICON_GRID = 60;
    public static float YDPI_ICON_GRID = 60;
    public static int NUMS_COLUMS_GRID_APPS = 4; //2 o se trabajara en otra opcion (adaptativo = GridView.AUTO_FIT).
    public static FragmentActivity HOME_ACTIVITY = null;
    public static FragmentActivity WAGON_ACTIVITY = null;
    public static FragmentActivity WELCOME_ACTIVITY = null;
    public static Activity MAIN_ACTIVITY = null;
    public static AppGridView WAGON_GRID_VIEW = null;
    public static int WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT = 0;


    //Nuevos
    //com.launcher.anc.model.GridFragmentBase
    //Home
    public static final int ID_HOME_VIEW_FRAGMENT_GRID = 0x00ff0004; // Id de la grilla de apps de home.
    public static final int HOME_INSTANCE = 14; // Identificador de instancia en home.
    public static final int ID_HOME_INTERNAL_EMPTY = 0x00ff0006; // ID para texto que se muestra por defecto.
    public static final int ID_HOME_PROGRESS_CONTAINER = 0x00ff0007; // ID para barra de progreso.
    public static final int ID_HOME_INTERNAL_LIST_CONTAINER = 0x00ff0008; // ID para la grilla con aplicaciones.

    //Wagon
    public static final int ID_WAGON_VIEW_FRAGMENT_GRID = 0x00ff0005; // Id de la grilla de apps de wagon.
    public static final int WAGON_INSTANCE = 15; // Identificador de instancia en wagon.
    public static final int ID_WAGON_INTERNAL_EMPTY = 0x00ff0009; // ID para texto que se muestra por defecto.
    public static final int ID_WAGON_PROGRESS_CONTAINER = 0x00ff0010; // ID para barra de progreso.
    public static final int ID_WAGON_INTERNAL_LIST_CONTAINER = 0x00ff0011; // ID para la grilla con aplicaciones.

    //com.launcher.anc.home.Home
    public static final ArrayList<String> PACKAGES = new ArrayList<String>();

    //Metodos.
    public static boolean isFirstUser(Context context){
        File packs = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath()  + "/array.ar");
        File settings = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath()  + "/settings.ar");

        if(!packs.exists() || !settings.exists()){
            return true;
        }else{
            return false;
        }
    }

    //Se adapta a la pantalla.
    public static void convertToPixels(float dp, Context context){
        Resources res = context.getResources();
        XDPI_ICON_GRID = dp;
        YDPI_ICON_GRID = dp;
        NUMS_COLUMS_GRID_APPS = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    //Obtiene un valor por medio de columnas.
    public static void adapterIcons(int cols, Context context){
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        XDPI_ICON_GRID = ((metrics.widthPixels / cols));// * (metrics.density));
        YDPI_ICON_GRID = (((metrics.heightPixels / cols)) / 3) + (((cols == 3) ? cols : (cols == 2) ? (3 + cols) : 3) * 22);// * (metrics.density)) / 2;
        NUMS_COLUMS_GRID_APPS = cols;
    }

    public static boolean saveSettings(int cols, ArrayList<String> packages, Context context){
        Toast.makeText(context, "" + cols, Toast.LENGTH_LONG).show();
        PACKAGES.add("com.whatsapp");
        PACKAGES.add("com.facebook.katana");
        PACKAGES.add("com.instagram.android");
        PACKAGES.add("com.facebook.lite");
        PACKAGES.add("com.instagram.lite");
        PACKAGES.add("com.facebook.orca");
        PACKAGES.add("com.pinterest");
        PACKAGES.add("com.zhiliaoapp.musically");
        PACKAGES.add("com.twitter.android");
        PACKAGES.add("com.google.android.youtube");
        PACKAGES.add("com.spotify.music");
        PACKAGES.add("com.google.android.youtube");
        PACKAGES.add("com.android.chrome");

        for(String new_p : packages){
            if(!PACKAGES.contains(new_p)){
                PACKAGES.add(new_p);
            }
        }

        File packs = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath() + "/array.ar");
        File settings = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath() + "/settings.ar");

        if(packs.exists()){
            packs.delete();
        }

        if(settings.exists()){
            settings.delete();
        }

        try{
            packs.createNewFile();
            settings.createNewFile();

            FileWriter wr0 = new FileWriter(packs, true);
            for(String p : PACKAGES){
                wr0.append(p + "\n");
            }
            wr0.close();

            FileWriter wr1 = new FileWriter(settings, true);
            wr1.write("cols:"+cols);
            wr1.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean GlobalSettingsLoad(Context context){
        try{
            File packs = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath() + "/array.ar");
            File settings = new File(context.getExternalFilesDirs(null)[0].getAbsolutePath() + "/settings.ar");

            if(packs.exists() && settings.exists()){
                FileReader r0 = new FileReader(packs);
                BufferedReader b0 = new BufferedReader(r0);

                while(b0.ready()){
                      PACKAGES.add(b0.readLine());
                }
                b0.close();
                r0.close();

                FileReader r1 = new FileReader(settings);
                BufferedReader b1 = new BufferedReader(r1);

                while(b1.ready()){
                      String line = b1.readLine();
                      if(line.startsWith("cols:")){
                          adapterIcons(Integer.valueOf(line.replace("cols:", "").trim()) ,context);
                      }
                }

                return true;
            }else{
               return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
