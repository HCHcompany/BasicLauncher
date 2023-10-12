package com.launcher.anc;

public class GlobalSettings{
    //Global
    public static final String EMPTY_MESSAGE_DEFAULT_GRIDVIEW = "No existen aplicaciones instaladas.";


    //com.launcher.anc.home.GridFragment
    public static final int INTERNAL_EMPTY_ID = 0x00ff0001; // ID para texto que se muestra por defecto.
    public static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002; // ID para barra de progreso.
    public static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003; // ID para la grilla con aplicaciones.

    public static final int WIDTH_COLUMS_GRID_APPS = 60; // 60, 70 o adaptativo.
    public static final int NUMS_COLUMS_GRID_APPS = 2; //2 o se trabajara en otra opcion (adaptativo = GridView.AUTO_FIT).
    public static final int SPACES_HORIZONTAL_GRID_APPS = 4; // Espacio por cada item de manera horizontal.
    public static final int SPACES_VERTICAL_GRID_APPS = 4; // Espacio por cada item de manera vertical.


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




}
