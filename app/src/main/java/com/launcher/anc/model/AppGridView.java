package com.launcher.anc.model;

import android.content.Context;
import android.widget.GridView;

import com.launcher.anc.GlobalSettings;

public class AppGridView extends GridView {

    private final Context context;

    public AppGridView(Context context){
        super(context);
        this.context = context;
        this.setDrawSelectorOnTop(false); // No dibujar selector en parte superior de la grilla.
        this.setColumnWidth(GlobalSettings.WIDTH_COLUMS_GRID_APPS); // Tamaño de ancho de columnas que tendra la grilla.
        this.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); // Modo de vista estrecho, puede ser igual "STRETCH_SPACING_UNIFORM" esta por verse.
        this.setNumColumns(GlobalSettings.NUMS_COLUMS_GRID_APPS); // Numero de columnas que tendra la grilla.
        this.setHorizontalSpacing(GlobalSettings.SPACES_HORIZONTAL_GRID_APPS); // Espacios por item de manera horizontal.
        this.setVerticalSpacing(GlobalSettings.SPACES_VERTICAL_GRID_APPS); // Espacios por item de manera vertical.
        this.setSmoothScrollbarEnabled(true); // Suavizar el desplazamiento de la barra de movimiento de la grilla.
    }
}
