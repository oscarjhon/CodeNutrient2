package v1.app.com.codenutrient.Activities;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.StepEntries;
import v1.app.com.codenutrient.R;

/**
 * Created by Arturo on 07/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class StepsActivity extends AppCompatActivity {

    private ImageButton prev, next;
    private LineChart graph;
    private Calendar min_date, sunday_wek, saturday_wek, today;
    private ArrayList<StepEntries> step_w, step_j, step_r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prev = (ImageButton) findViewById(R.id.before_steps);
        next = (ImageButton) findViewById(R.id.next_steps);
        graph = (LineChart) findViewById(R.id.step_charts);

        today = Calendar.getInstance();
        sunday_wek = Calendar.getInstance();
        saturday_wek = Calendar.getInstance();
        min_date = Calendar.getInstance();

        saturday_wek.roll(Calendar.DATE, -6);

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchUserDate(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if (cursor != null && cursor.moveToFirst()){
                String fecha = cursor.getString(cursor.getColumnIndex("min_fecha"));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                min_date.setTime(formatter.parse(fecha));
                helper.close();
                BeginRequests();
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void ShowErrorMessage(String message){
        Snackbar.make(findViewById(R.id.steps_coordinator), message, Snackbar.LENGTH_LONG).show();
        graph.setVisibility(View.INVISIBLE);
        CheckDate();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.before_steps:
                    sunday_wek.roll(Calendar.DATE, -7);
                    saturday_wek.roll(Calendar.DATE, -7);
                    BeginRequests();
                    break;
                case R.id.next_steps:
                    sunday_wek.roll(Calendar.DATE, 7);
                    saturday_wek.roll(Calendar.DATE, 7);
                    BeginRequests();
                    break;
            }
        }
    };

    private void CheckDate(){
        if (saturday_wek.getTimeInMillis() >= today.getTimeInMillis() ){
            next.setOnClickListener(null);
            next.setAlpha(.5f);
        }else{
            next.setOnClickListener(listener);
            next.setAlpha(1f);
        }

        if (sunday_wek.getTimeInMillis() <= min_date.getTimeInMillis()){
            prev.setOnClickListener(null);
            prev.setAlpha(.5f);
        }else{
            prev.setOnClickListener(listener);
            prev.setAlpha(1f);
        }
    }

    private void BeginRequests(){
        Calendar aux = Calendar.getInstance();
        aux.setTime(saturday_wek.getTime());
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        step_w = new ArrayList<>();
        step_j = new ArrayList<>();
        step_r = new ArrayList<>();
        try {
            helper.openDataBaseRead();
            do {
                if((aux.getTimeInMillis() < today.getTimeInMillis() || Constants.isSameDay(today, aux)) &&
                        (aux.getTimeInMillis() > min_date.getTimeInMillis() || Constants.isSameDay(aux, min_date) )) {
                    String fecha = aux.get(Calendar.YEAR) + "/" + (aux.get(Calendar.MONTH) +1) + "/" + aux.get(Calendar.DAY_OF_MONTH);
                    //Caminar
                    StepEntries walking = new StepEntries();
                    Cursor cursor = helper.fetchStepsByType(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), 1, fecha);
                    if (cursor != null && cursor.moveToFirst()){
                        walking.setValue(cursor.getInt(cursor.getColumnIndex("steps")));
                    }else{
                        walking.setValue(0);
                    }
                    walking.setDay(aux);
                    step_w.add(walking);
                    //Trotar
                    StepEntries jogging = new StepEntries();
                    cursor = helper.fetchStepsByType(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), 2, fecha);
                    Log.i("adasda", DatabaseUtils.dumpCursorToString(cursor));
                    if (cursor != null && cursor.moveToFirst()){
                        jogging.setValue(cursor.getInt(cursor.getColumnIndex("steps")));
                    }else{
                        jogging.setValue(0);
                    }
                    jogging.setDay(aux);
                    step_j.add(jogging);
                    //Correr
                    StepEntries running = new StepEntries();
                    cursor = helper.fetchStepsByType(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), 3, fecha);
                    if (cursor != null && cursor.moveToFirst()){
                        running.setValue(cursor.getInt(cursor.getColumnIndex("steps")));
                    }else{
                        running.setValue(0);
                    }
                    running.setDay(aux);
                    step_r.add(running);
                }else{
                    StepEntries def = new StepEntries();
                    def.setDay(aux);
                    def.setValue(0);
                    step_w.add(def);
                    step_j.add(def);
                    step_r.add(def);
                }
                if(Constants.isSameDay(aux, sunday_wek))
                    break;
                aux.roll(Calendar.DATE, 1);
            }while(true);
            helper.close();
            FillGraph();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void FillGraph(){
        if ((step_j.size() == step_r.size()) && (step_r.size() == step_w.size())){
            List<Entry> caminar = new ArrayList<>();
            List<Entry> trotar = new ArrayList<>();
            List<Entry> correr = new ArrayList<>();
            int i = 0;
            final String[] quarters = new String[step_j.size()];
            for (StepEntries w : step_w){
                for (StepEntries j : step_j){
                    if (Constants.isSameDay(j.getDay(), w.getDay())){
                        for (StepEntries r : step_r){
                            if (Constants.isSameDay(j.getDay(), r.getDay())){
                                String fecha = r.getDay().get(Calendar.YEAR) + "/" + (r.getDay().get(Calendar.MONTH) +1) + "/" + r.getDay().get(Calendar.DAY_OF_MONTH);
                                Entry wa = new Entry(i, w.getValue());
                                Entry jo = new Entry(i, j.getValue());
                                Entry ru = new Entry(i, r.getValue());
                                caminar.add(wa);
                                trotar.add(jo);
                                correr.add(ru);
                                quarters[i] = fecha;
                                i++;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return quarters[(int) value];
                }
            };
            LineDataSet setCaminar = new LineDataSet(caminar, "Pasos caminando");
            setCaminar.setAxisDependency(YAxis.AxisDependency.LEFT);
            setCaminar.setColors(new int[]{R.color.chart1}, getApplicationContext());
            LineDataSet setTrotar = new LineDataSet(trotar, "Pasos trotando");
            setTrotar.setAxisDependency(YAxis.AxisDependency.LEFT);
            setTrotar.setColors(new int[]{R.color.chart2}, getApplicationContext());
            LineDataSet setCorrer = new LineDataSet(correr, "Pasos corriendo");
            setCorrer.setAxisDependency(YAxis.AxisDependency.LEFT);
            setCorrer.setColors(new int[]{R.color.chart3}, getApplicationContext());
            List<ILineDataSet> lineDataSets = new ArrayList<>();
            lineDataSets.add(setCaminar);
            lineDataSets.add(setTrotar);
            lineDataSets.add(setCorrer);
            LineData lineData = new LineData(lineDataSets);
            graph.setData(lineData);
            XAxis axis = graph.getXAxis();
            axis.setGranularity(1f);
            axis.setValueFormatter(formatter);
            graph.invalidate();
            graph.notifyDataSetChanged();
            graph.setScaleXEnabled(true);
            graph.setPinchZoom(false);
            graph.setDoubleTapToZoomEnabled(true);
            graph.setVisibility(View.VISIBLE);
            CheckDate();
        }else{
            ShowErrorMessage("Ha ocurrido un error inseperado");
        }
    }
}
