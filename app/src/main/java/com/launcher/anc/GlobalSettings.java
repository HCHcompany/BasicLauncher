package com.launcher.anc;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.model.AppGridView;

import java.lang.reflect.Type;

public class GlobalSettings{
    //Global Constant.
    public static final String EMPTY_MESSAGE_DEFAULT_GRIDVIEW = "No existen aplicaciones instaladas."; //Mensaje general de vacio.
    public static final int WIDTH_COLUMS_GRID_APPS = 60; // 60, 70 o adaptativo.
    public static final int SPACES_HORIZONTAL_GRID_APPS = 4; // Espacio por cada item de manera horizontal.
    public static final int SPACES_VERTICAL_GRID_APPS = 4; // Espacio por cada item de manera vertical.

    //Global Variables
    public static float XDPI_ICON_GRID = 60;
    public static float YDPI_ICON_GRID = 60;
    public static int NUMS_COLUMS_GRID_APPS = 3; //2 o se trabajara en otra opcion (adaptativo = GridView.AUTO_FIT).
    public static FragmentActivity HOME_ACTIVITY = null;
    public static FragmentActivity WAGON_ACTIVITY = null;
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

    //Metodos.
    public static boolean isFirstUser(){
         return false;
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

    public static boolean GlobalSettingsLoad(){
        return false;
    }
}
