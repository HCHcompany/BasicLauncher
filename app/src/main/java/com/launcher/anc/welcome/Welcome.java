package com.launcher.anc.welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.launcher.anc.GlobalSettings;
import com.launcher.anc.R;
import com.launcher.anc.home.HomeView;
import com.launcher.anc.model.AppListAdapter;
import com.launcher.anc.model.AppLoader;
import com.launcher.anc.model.AppModel;

import java.util.ArrayList;

public class Welcome extends Fragment {

    private Context context;
    private int opc = 0;
    private final ArrayList<String> PACKAGES = new ArrayList<String>();
    private AppListAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        context = getActivity();

        FrameLayout frame = new FrameLayout(context);
        frame.setFitsSystemWindows(true);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        //Bienvenida.
        LinearLayout welcome = new LinearLayout(frame.getContext());
        welcome.setFitsSystemWindows(true);
        welcome.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        welcome.setBackgroundColor(Color.argb(225, 255, 255, 255));
        welcome.setGravity(Gravity.CENTER_HORIZONTAL);
        welcome.setOrientation(LinearLayout.VERTICAL);
        welcome.setVisibility(View.VISIBLE);


        TextView title = new TextView(welcome.getContext());
        title.setTextSize(30);
        title.setTextColor(Color.rgb(51, 255, 144));
        title.setText(Html.fromHtml("<b>Bienvenid@</b>"));
        title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        title.setVisibility(View.VISIBLE);
        title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        welcome.addView(title, title.getLayoutParams());

        EditText body = new EditText(welcome.getContext());
        body.setTextSize(16);
        body.setText(Html.fromHtml("Bienvenid@s, esta es una aplicacion especialmente hecha para simplificar el uso de smartphones en adultos mayores " +
                                          "dandoles una vista mas amigable para que ellos se introduzcan al mundo de la tecnologia, esta herramienta es simple y " +
                                          "facil de usar, esperamos que te ayude bastante y poder simplificar tu dia a dia con esta herramienta. A continuacion " +
                                          "necesitamos adaptarnos a tus necesidades, para esto de daremos a elegir diferentes tamaños de iconos.\n\n" +
                                          "<br><br><b>AYUDA: GUIATE POR LAS CASILLAS Y HAZ CLIC EN UNA PARA VER LAS DIFERENTES VISTAS QUE TENEMOS PARA TI, CUANDO " +
                                          "DECIDAS CUAL TE ACOMODA MAS DEBES PRESIONAR SIGUIENTE.</b>"));
        body.setGravity(Gravity.CENTER_HORIZONTAL);
        body.setClickable(false);
        body.setFocusable(false);
        body.setBackgroundColor(Color.argb(100, 255,255, 255));
        body.setTextIsSelectable(false);
        body.setTextColor(Color.BLACK);
        body.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (metrics.heightPixels - (title.getLayoutParams().height + 420))));
        body.setVisibility(View.VISIBLE);
        welcome.addView(body, body.getLayoutParams());

        Button start = new Button(welcome.getContext());
        start.setText("Comenzar");
        start.setTextColor(Color.BLACK);
        start.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 2, 150));
        start.setY(50);
        start.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        welcome.addView(start, start.getLayoutParams());

        frame.addView(welcome, welcome.getLayoutParams());

        //Preferencias
        LinearLayout prefer = new LinearLayout(frame.getContext());
        prefer.setFitsSystemWindows(true);
        prefer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        prefer.setBackgroundColor(Color.argb(225, 255, 255, 255));
        prefer.setGravity(Gravity.CENTER_HORIZONTAL);
        prefer.setOrientation(LinearLayout.VERTICAL);
        prefer.setVisibility(View.GONE);

        TextView title1 = new TextView(prefer.getContext());
        title1.setTextSize(30);
        title1.setTextColor(Color.rgb(51, 255, 144));
        title1.setText(Html.fromHtml("<b>Preferencias</b>"));
        title1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        title1.setVisibility(View.VISIBLE);
        title1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        prefer.addView(title1, title1.getLayoutParams());

        ImageView img = new ImageView(prefer.getContext());
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (metrics.heightPixels / 2) - 200));
        img.setImageResource(R.drawable.icon);
        img.setVisibility(View.VISIBLE);
        prefer.addView(img, img.getLayoutParams());

        LinearLayout checks0 = new LinearLayout(prefer.getContext());
        checks0.setFitsSystemWindows(true);
        checks0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checks0.setBackgroundColor(Color.argb(0, 255, 255, 255));
        checks0.setGravity(Gravity.CENTER_HORIZONTAL);
        checks0.setOrientation(LinearLayout.VERTICAL);
        checks0.setVisibility(View.VISIBLE);

        TextView comment = new TextView(checks0.getContext());
        comment.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        comment.setTextColor(Color.BLACK);
        comment.setText(Html.fromHtml("<b>Selecciona una de las casillas para ver los diferentes tamaños de iconos disponibles:</b>"));
        comment.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        checks0.addView(comment, comment.getLayoutParams());

        LinearLayout checks1 = new LinearLayout(checks0.getContext());
        checks1.setFitsSystemWindows(true);
        checks1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checks1.setBackgroundColor(Color.argb(0, 255, 255, 255));
        checks1.setGravity(Gravity.CENTER_HORIZONTAL);
        checks1.setOrientation(LinearLayout.HORIZONTAL);
        checks1.setVisibility(View.VISIBLE);

        CheckBox opc1 = new CheckBox(checks1.getContext());
        opc1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        opc1.setText("grande");
        opc1.setTextColor(Color.BLACK);
        opc1.setVisibility(View.VISIBLE);
        checks1.addView(opc1, opc1.getLayoutParams());

        CheckBox opc2 = new CheckBox(checks1.getContext());
        opc2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        opc2.setText("Mediano");
        opc2.setTextColor(Color.BLACK);
        opc2.setVisibility(View.VISIBLE);
        opc2.setX(20);
        checks1.addView(opc2, opc2.getLayoutParams());

        checks0.addView(checks1, checks1.getLayoutParams());

        CheckBox opc3 = new CheckBox(checks1.getContext());
        opc3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        opc3.setText("Normal");
        opc3.setTextColor(Color.BLACK);
        opc3.setVisibility(View.VISIBLE);
        opc3.setX(20);
        checks0.addView(opc3, opc3.getLayoutParams());
        prefer.addView(checks0, checks0.getLayoutParams());

        TextView comment1 = new TextView(prefer.getContext());
        comment1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        comment1.setTextColor(Color.BLACK);
        comment1.setText(Html.fromHtml("<b>Luego de elegir una opcion presiona siguiente:</b>"));
        comment1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        prefer.addView(comment1, comment1.getLayoutParams());

        Button sig0 = new Button(prefer.getContext());
        sig0.setText("Siguiente");
        sig0.setTextColor(Color.BLACK);
        sig0.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 2, 150));
        sig0.setY(50);
        sig0.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        prefer.addView(sig0, sig0.getLayoutParams());

        frame.addView(prefer, prefer.getLayoutParams());

        //Seleccionar apps.
        LinearLayout select = new LinearLayout(frame.getContext());
        select.setFitsSystemWindows(true);
        select.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        select.setBackgroundColor(Color.argb(225, 255, 255, 255));
        select.setGravity(Gravity.CENTER_HORIZONTAL);
        select.setOrientation(LinearLayout.VERTICAL);
        select.setVisibility(View.GONE);

        TextView title2 = new TextView(select.getContext());
        title2.setTextSize(30);
        title2.setTextColor(Color.rgb(51, 255, 144));
        title2.setText(Html.fromHtml("<b>Seleccionar</b>"));
        title2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        title2.setVisibility(View.VISIBLE);
        title2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        select.addView(title2, title2.getLayoutParams());

        TextView comment2 = new TextView(select.getContext());
        comment2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        comment2.setTextColor(Color.BLACK);
        comment2.setText(Html.fromHtml("<b>Selecciona las aplicaciones que mas usas:</b>"));
        comment2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        select.addView(comment2, comment2.getLayoutParams());

        int x = android.R.layout.simple_list_item_multiple_choice;
        GridView apps = new GridView(select.getContext());
        apps.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (metrics.heightPixels / 2) + ((metrics.heightPixels / 2) / 4)));
        apps.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        apps.setDrawSelectorOnTop(false); // No dibujar selector en parte superior de la grilla.
        apps.setColumnWidth(GlobalSettings.WIDTH_COLUMS_GRID_APPS); // Tamaño de ancho de columnas que tendra la grilla.
        apps.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); // Modo de vista estrecho, puede ser igual "STRETCH_SPACING_UNIFORM" esta por verse.
        apps.setHorizontalSpacing(GlobalSettings.SPACES_HORIZONTAL_GRID_APPS); // Espacios por item de manera horizontal.
        apps.setVerticalSpacing(GlobalSettings.SPACES_VERTICAL_GRID_APPS); // Espacios por item de manera vertical.
        apps.setSmoothScrollbarEnabled(true); // Suavizar el desplazamiento de la barra de movimiento de la grilla.
        apps.setBackgroundColor(Color.argb(40, 0, 0, 0));
        select.addView(apps, apps.getLayoutParams());


        Button ok = new Button(select.getContext());
        ok.setText("Terminar");
        ok.setTextColor(Color.BLACK);
        ok.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 2, 150));
        ok.setY(50);
        ok.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        select.addView(ok, ok.getLayoutParams());

        frame.addView(select, select.getLayoutParams());

        //Listeners
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcome.setVisibility(View.GONE);
                prefer.setVisibility(View.VISIBLE);
            }
        });

        opc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opc = 1;
                opc2.setChecked(false);
                opc3.setChecked(false);
                img.setImageResource(R.drawable.grande);
            }
        });

        opc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opc = 2;
                opc1.setChecked(false);
                opc3.setChecked(false);
                img.setImageResource(R.drawable.mediano);
            }
        });

        opc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opc = 3;
                opc1.setChecked(false);
                opc2.setChecked(false);
                img.setImageResource(R.drawable.normal);
            }
        });

        sig0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(opc != 0){
                    comment.setTextColor(Color.BLACK);
                    prefer.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);
                    GlobalSettings.adapterIcons((opc + 1), getActivity());
                    apps.setNumColumns(GlobalSettings.NUMS_COLUMS_GRID_APPS); // Numero de columnas que tendra la grilla.
                    AppLoader loader = new AppLoader(getActivity(), GlobalSettings.WAGON_INSTANCE);
                    adapter = new AppListAdapter(getActivity());
                    adapter.setData(loader.loadInBackground());
                    apps.setAdapter(adapter);
                }else{
                    comment.setTextColor(Color.RED);
                }
            }
        });

        apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                AppModel app = (AppModel)adapterView.getItemAtPosition(i);
                if(app != null){
                    PACKAGES.add(app.getApplicationPackageName());
                    adapter.remove(adapter.getItem(i));
                    apps.setAdapter(adapter);
                    Toast.makeText(getContext(), "Applicacion " + app.getLabel() + " ha sido fijada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalSettings.saveSettings(GlobalSettings.NUMS_COLUMS_GRID_APPS, PACKAGES, getContext())){
                    Toast.makeText(getContext(), "Datos guardados.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(GlobalSettings.MAIN_ACTIVITY, HomeView.class);
                    startActivity(i);
                    GlobalSettings.WELCOME_ACTIVITY.finish();
                }else{
                    Toast.makeText(getContext(), "Error al guardar datos.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return frame;
    }
}
