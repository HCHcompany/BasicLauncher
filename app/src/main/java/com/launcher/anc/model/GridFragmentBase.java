package com.launcher.anc.model;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.listeners.HomeListener;
import com.launcher.anc.listeners.WagonListener;

public class GridFragmentBase extends Fragment{

    private Handler mHandler = new Handler(); // Controla un Runnable.
    private AppGridView mGrid = null; // Objeto grilla global.
    private TextView mStandardEmptyView = null; // Objeto texto default global.
    private View mProgressContainer = null; // Contenedor de progreso global.
    private View mGridContainer = null; // Contenedor de grilla global.
    private View mEmptyView = null; //Vista en blanco o vista que se muestra por defecto.
    private CharSequence mEmptyText = null; // Texto de mensaje "empty text default".
    private boolean mGridShown = false; // Estado vista grilla mostrar/no mostrar.
    private ListAdapter mAdapter = null; // Adaptador de lista para mostrar los items en la grilla.

    private final Runnable mRequestFocus = new Runnable() { // Solicitar enfoque de la grilla.
        @Override
        public void run() {
            mGrid.focusableViewAvailable(mGrid);
        }
    };

    private final int INSTANCE; //Tipo de clase Home o Wagon.

    public GridFragmentBase(int instance){
         this.INSTANCE = instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getActivity(); //Contexto de la actividad es como "this".

        FrameLayout root = new FrameLayout(context); //Se añade un nuevo marco como base a esta actividad o fragmento.

        //------------------------------------Frame para barra de carga o progreso-------------------------------------------------
        LinearLayout pframe = new LinearLayout(context); //marco base para la barra de progreso.
        pframe.setOrientation(LinearLayout.VERTICAL); // Orientacion vertical.
        pframe.setVisibility(View.GONE); // Vista visible.
        pframe.setGravity(Gravity.CENTER); // Punto de gravedad de los objetos en el centro del marco.

        ProgressBar progress = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge); // Nueva barra de progreso.
        pframe.addView(progress, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)); // Añadir barra de progreso a el marco de carga como W: WrapContent && H: WrapContent.
        root.addView(pframe, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Añadir la vista de pantalla de carga al marco base de la actividad o fragmento.

        //--------------------------------------Frame para la vista de aplicaciones-------------------------------------------------

        FrameLayout lframe = new FrameLayout(context); // Marco base para la lista o grilla de aplicaciones.
        TextView tv = new TextView(context); // Nuevo texto que se mostrara en el caso que no existan aplicaciones.
        lframe.addView(tv, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Añadir vista de texto por defecto al marco base de la lista.

        AppGridView gridapps = new AppGridView(context); // Grilla para listar las aplicaciones.
        //Dependiendo de la version del SDK se desactivara overscroll.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            gridapps.setOverScrollMode(ListView.OVER_SCROLL_NEVER); // desabilitar overscroll.
        }

        //Añadir ID.
        if(INSTANCE == GlobalSettings.HOME_INSTANCE){
            gridapps.setId(GlobalSettings.ID_HOME_VIEW_FRAGMENT_GRID); // Se asgina ID.
            tv.setId(GlobalSettings.ID_HOME_INTERNAL_EMPTY); // Asignar el ID para el texto.
            lframe.setId(GlobalSettings.ID_HOME_INTERNAL_LIST_CONTAINER); // Asignar ID a la lista.
            pframe.setId(GlobalSettings.ID_HOME_PROGRESS_CONTAINER); // Asignar ID.
        }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
            gridapps.setId(GlobalSettings.ID_WAGON_VIEW_FRAGMENT_GRID); // Se asgina ID.
            tv.setId(GlobalSettings.ID_WAGON_INTERNAL_EMPTY); // Asignar el ID para el texto.
            lframe.setId(GlobalSettings.ID_WAGON_INTERNAL_LIST_CONTAINER); // Asignar ID a la lista.
            pframe.setId(GlobalSettings.ID_WAGON_PROGRESS_CONTAINER); // Asignar ID.
        }



        lframe.addView(gridapps, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Se añade la grilla al marco de vista de aplicaciones.
        root.addView(lframe, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Se añade el marco de vista de aplicaciones al marco root de la actividad.

        //----------------------------------------------------------------------------------------------------------------------------------

        root.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // El marco root se mostrara en vista completa W/H.
        return root;
    }

    //Control de la barra de progreso y animaciones.
    private void setGridShown(boolean shown, boolean animate) {
        ensureGrid();
        if (mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (mGridShown == shown) {
            return;
        }
        mGridShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mGridContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mGridContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mGridContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mGridContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mGridContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mGridContainer.setVisibility(View.GONE);
        }
    }

    public void setGridShown(boolean shown) {
        this.setGridShown(shown, true);
    }

    public void setGridShownNoAnimation(boolean shown) {
        this.setGridShown(shown, false);
    }

    //Añadir nueva adaptacion a la grilla o lista.
    public void setGridAdapter(ListAdapter adapter){
        final boolean hadAdapter = (mAdapter != null);
        mAdapter = adapter;
        if(mGrid != null){
            mGrid.setAdapter(adapter); // Añadir nuevo adapter a la grilla.
            if(!mGridShown && !hadAdapter){
                setGridShown(true, (getView().getWindowToken() != null)); //Barra de progreso.
            }
        }
    }

    //Añadir texto por defecto.
    public void setEmptyText(CharSequence text){
        ensureGrid();
        if(mStandardEmptyView == null){
            throw new IllegalStateException("TextView Default grid home is null in setEmptyText."); // Lanzar excepcion diciendo que el objeto en nulo.
        }else{
            mStandardEmptyView.setText(text); // Mostrar texto en vista por defecto.
            if(mEmptyText == null){
                mGrid.setEmptyView(mStandardEmptyView);
            }
            mEmptyText = text;
        }
    }

    //Asegurar la grilla para evitar errores o actualizacion de esta si es nula.
    private void ensureGrid(){
        if(mGrid != null){
            return;
        }else{
            View root = getView(); //Obtener vista/marco base de actividad.
            if(root == null){
                throw new IllegalStateException("Contenido no creado."); // Lanzar una excepcion especificando que la vista principal no ha sido creada.
            }else{
                if(root instanceof AppGridView){ // Si la vista raiz es la instancia de la clase u objeto GridView.
                    mGrid = (AppGridView)root; // Se añade a la variable global de grilla.
                }else{
                    mStandardEmptyView = (TextView) root.findViewById(GlobalSettings.INTERNAL_EMPTY_ID); // Se obtiene objeto texto por defecto por medio de su ID.
                    if(mStandardEmptyView == null){ // Si el objeto de texto es nulo.
                        mEmptyText = root.findViewById(android.R.id.empty); // Se recurre al ID por defecto de android empty.
                    }else{ // De lo contrario.
                        mStandardEmptyView.setVisibility(View.GONE); // Se muestra el texto por defecto que contiene un mensaje en la vista de la grilla.
                    }

                    mProgressContainer = root.findViewById(GlobalSettings.INTERNAL_PROGRESS_CONTAINER_ID); // Se obtiene el marco que contiene la barra de carga o de progreso.
                    mGridContainer = root.findViewById(GlobalSettings.INTERNAL_LIST_CONTAINER_ID);
                    View rawGrid = root.findViewById(android.R.id.list); //Obtener grilla por medio de ID asignada.
                    if(!(rawGrid instanceof AppGridView)){
                        if(rawGrid == null){
                            throw new RuntimeException("En el id \"android.R.id.list\", no existe la vista de la grilla es nula."); // Lanzar una excepcion de tiempo de ejecucion que muestre un mesaje de que la grilla es nula.
                        }else{
                            throw new RuntimeException("En el id \"android.R.id.list\", no se encuentra un objeto de vista GridView."); // Lanzar una excepcion de tiempo de ejecucion que muestre un mensaje diciendo que la vista no es una grilla.
                        }
                    }

                    mGrid = (AppGridView) rawGrid;
                    if(mEmptyView != null){ // Si la vista en blanco no es nula o contiene algo.
                        mGrid.setEmptyView(mEmptyView); // Mostrar la vista guardada en grilla.
                    }else{ // Si no
                        mStandardEmptyView.setText(mEmptyText); // Mostrar mensaje por medio de texto por defecto.
                        mGrid.setEmptyView(mStandardEmptyView); // Mostrar texto por defecto como vista por defecto en grilla.
                    }
                }

                mGridShown = true; // Cambia el estado de vista a "mostrar".

                if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                    mGrid.setOnItemClickListener(new HomeListener()); // Se añade una accion para cada item en la grilla.
                }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                    mGrid.setOnItemClickListener(new WagonListener()); // Se añade una accion para cada item en la grilla.
                }

                if(mAdapter != null){
                    ListAdapter adapter = mAdapter;
                    mAdapter = null;
                    setGridAdapter(adapter);
                }else{
                    //No existe un adaptador, se mostrara el indicador de progreso.
                    if(mProgressContainer != null){
                        setGridShown(false, false);
                    }
                }
            }
        }
        mHandler.post(mRequestFocus);
    }
}
