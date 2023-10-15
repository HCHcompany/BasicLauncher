package com.launcher.anc.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import androidx.fragment.app.FragmentActivity;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;
import com.launcher.anc.listeners.HomeListener;
import com.launcher.anc.listeners.WagonListener;
import com.launcher.anc.wagon.WagonView;

import java.util.ArrayList;

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
    private boolean isTouch = false;
    private boolean isViewKeyboard = false;
    private Thread updateThread = null;

    private final Runnable mRequestFocus = new Runnable() { // Solicitar enfoque de la grilla.
        @Override
        public void run() {
            mGrid.focusableViewAvailable(mGrid);
        }
    };

    private final int INSTANCE; //Tipo de clase Home o Wagon.
    private final FragmentActivity activity;

    public GridFragmentBase(FragmentActivity activity, int instance){
         this.INSTANCE = instance;
         this.activity = activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getActivity(); //Contexto de la actividad es como "this".

        //LoadSettings.
        GlobalSettings.adapterIcons(GlobalSettings.NUMS_COLUMS_GRID_APPS, context);

        //Create fragment.
        FrameLayout root = new FrameLayout(context); //Se añade un nuevo marco como base a esta actividad o fragmento.
        root.setFitsSystemWindows(true);

        //------------------------------------Frame para barra de carga o progreso-------------------------------------------------
        LinearLayout pframe = new LinearLayout(context); //marco base para la barra de progreso.
        pframe.setOrientation(LinearLayout.VERTICAL); // Orientacion vertical.
        pframe.setVisibility(View.GONE); // Vista visible.
        pframe.setGravity(Gravity.CENTER); // Punto de gravedad de los objetos en el centro del marco.
        pframe.setFitsSystemWindows(true);

        ProgressBar progress = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge); // Nueva barra de progreso.
        pframe.addView(progress, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)); // Añadir barra de progreso a el marco de carga como W: WrapContent && H: WrapContent.
        root.addView(pframe, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Añadir la vista de pantalla de carga al marco base de la actividad o fragmento.

        //--------------------------------------Frame para la vista de aplicaciones-------------------------------------------------

        LinearLayout lframe = new LinearLayout(context); // Marco base para la lista o grilla de aplicaciones.
        lframe.setOrientation(LinearLayout.VERTICAL); // Orientacion vertical.
        lframe.setVisibility(View.GONE); // Vista visible.
        lframe.setGravity(Gravity.CENTER); // Punto de gravedad de los objetos en el centro del marco.
        lframe.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        lframe.setFitsSystemWindows(true);

        TextView tv = new TextView(context); // Nuevo texto que se mostrara en el caso que no existan aplicaciones.
        lframe.addView(tv, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Añadir vista de texto por defecto al marco base de la lista.

        AppGridView gridapps = new AppGridView(context); // Grilla para listar las aplicaciones.
        gridapps.setFitsSystemWindows(true);

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

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int pixm = 0;
        if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
            pixm = 110;
            EditText txt = new EditText(context);
            ViewGroup.LayoutParams lp1 = new LinearLayout.LayoutParams(((metrics.widthPixels / 2) + 250), pixm);
            txt.setLayoutParams(lp1);
            txt.setHint("Buscar app");
            txt.setTextSize(12);
            txt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            txt.setFocusable(true);
            txt.setSelectAllOnFocus(true);

            txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //if(!txt.getText().toString().isEmpty()){
                        ArrayList<AppModel> tmp_model = ((AppListAdapter)mAdapter).getData();
                        if(tmp_model != null){
                            String indicio = txt.getText().toString();
                            ArrayList<AppModel> apps = new ArrayList<AppModel>();
                            for(AppModel m : tmp_model){
                                if(m.getLabel().contains(indicio) || m.getLabel().contains(indicio.toLowerCase())){
                                    apps.add(m);
                                }
                            }

                            AppListAdapter tmp_adapter = new AppListAdapter(context);
                            tmp_adapter.setData(apps);
                            gridapps.setAdapter(tmp_adapter);
                            if(!mGridShown && !(tmp_adapter != null)){
                                setGridShown(true, (getView().getWindowToken() != null)); //Barra de progreso.
                            }

                            if(isViewKeyboard){
                                if(GlobalSettings.NUMS_COLUMS_GRID_APPS < 3){
                                    gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                                }else{
                                    gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID * 2) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                                }
                            }else{
                                gridapps.getLayoutParams().height = GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT;
                            }
                            isTouch = true;
                        }else{
                            //gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID * 2) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                            isTouch = true;
                        }
                    //}
                }
                @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override public void afterTextChanged(Editable editable) {}
            });


            txt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT == 0){
                        GlobalSettings.WAGON_GRID_VIEW = gridapps;
                        GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT = gridapps.getLayoutParams().height;
                    }
                    if(GlobalSettings.NUMS_COLUMS_GRID_APPS < 3){
                        gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                    }else{
                        gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID * 2) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                    }
                    isTouch = true;
                    return false;
                }
            });

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT == 0){
                        GlobalSettings.WAGON_GRID_VIEW = gridapps;
                        GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT = gridapps.getLayoutParams().height;
                    }
                    if(GlobalSettings.NUMS_COLUMS_GRID_APPS < 3){
                        gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                    }else{
                        gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID * 2) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                    }
                    isTouch = true;
                }
            });

            updateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        while(true){
                              if(isTouch){
                                  InputMethodManager mi = (InputMethodManager)GlobalSettings.WAGON_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE);
                                  if(mi.isActive()){
                                      if(GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT != 0){
                                          gridapps.getLayoutParams().height =  GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT;
                                      }
                                      isTouch = false;
                                      isViewKeyboard = false;
                                  }else{
                                      isViewKeyboard = true;
                                      if(GlobalSettings.NUMS_COLUMS_GRID_APPS < 3){
                                          gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                                      }else{
                                          gridapps.getLayoutParams().height = (int)(GlobalSettings.YDPI_ICON_GRID * 2) + (int)(GlobalSettings.YDPI_ICON_GRID / 2);
                                      }
                                  }
                              }else{
                                  isViewKeyboard = true;
                              }
                            Thread.sleep(850);
                        }
                    }catch (Exception e){}
                }
            });
            updateThread.start();
            lframe.addView(txt, lp1);
        }

        //Boton para abrir la grilla.
        Button ini = new Button(context);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(280, 280);
        ini.setLayoutParams(lp);
        ini.setY(10);
        ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                   Intent i = new Intent(activity, WagonView.class);
                   startActivity(i);
                }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                    activity.finish();//Terminar vagon.
                }
            }
        });

        if(INSTANCE == GlobalSettings.HOME_INSTANCE){
           ini.setBackgroundResource(R.drawable.menu);
        }else{
            ini.setBackgroundResource(R.drawable.back);
        }

        //Añadir al marco.
        lframe.addView(gridapps, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (metrics.heightPixels - ((lp.height + (lp.height / 2))) - (pixm)))); // Se añade la grilla al marco de vista de aplicaciones.
        lframe.addView(ini, lp); // Se añade la grilla al marco de vista de aplicaciones.

        GlobalSettings.WAGON_GRID_VIEW_DEFAULT_SIZE_HEIGHT = gridapps.getLayoutParams().height;
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
                    if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                        mStandardEmptyView = (TextView) root.findViewById(GlobalSettings.ID_HOME_INTERNAL_EMPTY); // Se obtiene objeto texto por defecto por medio de su ID.
                    }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                        mStandardEmptyView = (TextView) root.findViewById(GlobalSettings.ID_WAGON_INTERNAL_EMPTY); // Se obtiene objeto texto por defecto por medio de su ID.
                    }

                    if(mStandardEmptyView == null){ // Si el objeto de texto es nulo.
                        if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                            mEmptyText = root.findViewById(GlobalSettings.ID_HOME_INTERNAL_EMPTY); // Se recurre al ID por defecto de android empty.
                        }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                            mEmptyText = root.findViewById(GlobalSettings.ID_WAGON_INTERNAL_EMPTY); // Se recurre al ID por defecto de android empty.
                        }

                    }else{ // De lo contrario.
                        mStandardEmptyView.setVisibility(View.GONE); // Se muestra el texto por defecto que contiene un mensaje en la vista de la grilla.
                    }

                    if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                        mProgressContainer = root.findViewById(GlobalSettings.ID_HOME_PROGRESS_CONTAINER); // Se obtiene el marco que contiene la barra de carga o de progreso.
                        mGridContainer = root.findViewById(GlobalSettings.ID_HOME_INTERNAL_LIST_CONTAINER);
                    }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                        mProgressContainer = root.findViewById(GlobalSettings.ID_WAGON_PROGRESS_CONTAINER); // Se obtiene el marco que contiene la barra de carga o de progreso.
                        mGridContainer = root.findViewById(GlobalSettings.ID_WAGON_INTERNAL_LIST_CONTAINER);
                    }


                    View rawGrid = null;
                    if(INSTANCE == GlobalSettings.HOME_INSTANCE){
                        rawGrid = root.findViewById(GlobalSettings.ID_HOME_VIEW_FRAGMENT_GRID); //Obtener grilla por medio de ID asignada.
                    }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                        rawGrid = root.findViewById(GlobalSettings.ID_WAGON_VIEW_FRAGMENT_GRID); //Obtener grilla por medio de ID asignada.
                    }

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
                    mGrid.setOnItemClickListener(new HomeListener(getActivity())); // Se añade una accion para cada item en la grilla.
                }else if(INSTANCE == GlobalSettings.WAGON_INSTANCE){
                    mGrid.setOnItemClickListener(new WagonListener(getActivity())); // Se añade una accion para cada item en la grilla.
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
