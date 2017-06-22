package v1.app.com.codenutrient.Activities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import java.util.Locale;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.CaloryEntries;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.POJO.StepEntries;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Calories;
import v1.app.com.codenutrient.Requests.HasProduct;

/**
 * Created by Arturo on 05/06/2017.
 * This code was made for the project CodeNutrient.
 */
public class CaloryActivity extends AppCompatActivity {

    private ImageButton prev, next;
    private LineChart graph;
    private Calendar min_date, sunday_wek, saturday_wek, today;
    private HttpManager manager;
    private ArrayList<CaloryEntries> calories;
    private ArrayList<StepEntries> pro_calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calory);
        prev = (ImageButton) findViewById(R.id.before_calory);
        next = (ImageButton) findViewById(R.id.next_calory);
        graph = (LineChart) findViewById(R.id.calory_charts);

        today = Calendar.getInstance();
        sunday_wek = Calendar.getInstance();
        saturday_wek = Calendar.getInstance();
        min_date = Calendar.getInstance();

        saturday_wek.roll(Calendar.DATE, -6);
        manager = new HttpManager();

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchUserDate(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if (cursor != null && cursor.moveToFirst()){
                String fecha = cursor.getString(cursor.getColumnIndex("min_fecha"));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                min_date.setTime(formatter.parse(fecha));
                helper.close();
                if (manager.isOnLine(getApplicationContext()))
                    BeginRequests();
                else
                    ShowErrorMessage("Debes tener conexión a internet para utilizar esta opción");
            }
        } catch (Exception ignored) {
            ShowErrorMessage("Ocurrio un error inesperado");
        }
    }

    private void ShowErrorMessage(String message){
        Snackbar.make(findViewById(R.id.calory_coordinator), message, Snackbar.LENGTH_LONG).show();
        graph.setVisibility(View.INVISIBLE);
        CheckDate();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.before_calory:
                    sunday_wek.roll(Calendar.DATE, -7);
                    saturday_wek.roll(Calendar.DATE, -7);
                    BeginRequests();
                    break;
                case R.id.next_calory:
                    sunday_wek.roll(Calendar.DATE, 7);
                    saturday_wek.roll(Calendar.DATE, 7);
                    BeginRequests();
                    break;
            }
        }
    };

    private void BeginRequests(){
        new GetCalories().execute();
    }

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

    private void PostExecuteCalories(ArrayList<CaloryEntries> calories){
        if (calories != null){
            this.calories = calories;
            new GetProducts().execute();
        }else{
            ShowErrorMessage("Ha ocurrido un error inesperado");
        }
    }

    private void PostExecuteProducts (ArrayList<StepEntries> entries){
        if (entries != null){
            pro_calories = entries;
            FillChart();
        }else{
            ShowErrorMessage("Ha ocurrido un error inesperado");
        }
    }

    private void FillChart(){
        if (pro_calories.size() == calories.size()) {
            List<Entry> gasto = new ArrayList<>();
            List<Entry> consumo = new ArrayList<>();
            int i = 0;
            final String[] quarters = new String[pro_calories.size()];
            for (StepEntries c : pro_calories) {
                for (CaloryEntries g : calories) {
                    if (Constants.isSameDay(c.getDay(), g.getDay())) {
                        Entry ga = new Entry(i, g.getValue());
                        gasto.add(ga);
                        Entry co = new Entry(i, c.getValue());
                        consumo.add(co);
                        quarters[i] = c.getDay().get(Calendar.YEAR) + "/" + (c.getDay().get(Calendar.MONTH) + 1) +
                                "/" + c.getDay().get(Calendar.DAY_OF_MONTH);
                        i++;
                    }
                }
            }

            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return quarters[(int) value];
                }
            };
            LineDataSet setGasto = new LineDataSet(gasto, "Calorías quemadas");
            setGasto.setAxisDependency(YAxis.AxisDependency.LEFT);
            setGasto.setColors(new int[]{R.color.chart1}, getApplicationContext());
            LineDataSet setConsumo = new LineDataSet(consumo, "Calorías consumidas");
            setConsumo.setAxisDependency(YAxis.AxisDependency.LEFT);
            setConsumo.setColors(new int[]{R.color.chart2}, getApplicationContext());

            List<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(setGasto);
            dataSets.add(setConsumo);
            LineData data = new LineData(dataSets);
            graph.setData(data);
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
        }
        else{
            ShowErrorMessage("Ha ocurrido un error inseperado");
        }
    }

    public class GetCalories extends AsyncTask<String, String, ArrayList<CaloryEntries>> {

        @Override
        protected ArrayList<CaloryEntries> doInBackground(String... params) {
            boolean reloaded = false;
            float kcal = MainActivity.appUser.getInfoAppUser().getMax_calorias();
            Calories request = new Calories();
            ArrayList<CaloryEntries> entities = new ArrayList<>();
            Calendar aux = Calendar.getInstance();
            aux.setTime(saturday_wek.getTime());
            do{
                if((aux.getTimeInMillis() < today.getTimeInMillis() || Constants.isSameDay(today, aux)) &&
                        (aux.getTimeInMillis() > min_date.getTimeInMillis() || Constants.isSameDay(aux, min_date) )){
                    v1.app.com.codenutrient.POJO.Calories calories;
                    if (Constants.isSameDay(today, aux))
                        calories = request.ExecuteGET(MainActivity.appUser);
                    else
                        calories = request.ExecuteGET(MainActivity.appUser, aux.getTime());
                    CaloryEntries entry = new CaloryEntries();
                    switch (calories.getCode()){
                        case 200:
                            entry.setValue(calories.getGasto() + kcal);
                            break;
                        case 404:
                            entry.setValue(kcal);
                            break;
                        case 422:
                            return  null;
                        case 401:
                            if (!reloaded){
                                reloaded = true;
                                if (manager.reload(1, getApplicationContext())){
                                    if (Constants.isSameDay(today, aux))
                                        calories = request.ExecuteGET(MainActivity.appUser);
                                    else
                                        calories = request.ExecuteGET(MainActivity.appUser, aux.getTime());
                                    if (calories.getCode() == 200)
                                        entry.setValue(calories.getGasto() + kcal);
                                    else if(calories.getCode() == 404)
                                        entry.setValue(kcal);
                                    else
                                        return null;
                                }else{
                                    return null;
                                }
                            }
                            break;
                        default:
                            return null;
                    }
                    entry.setDay(aux);
                    entities.add(entry);
                }else {
                    CaloryEntries entry = new CaloryEntries();
                    entry.setValue(0);
                    entry.setDay(aux);
                    entities.add(entry);
                }
                if(Constants.isSameDay(aux, sunday_wek))
                    break;
                aux.roll(Calendar.DATE, 1);
            }while(true);
            return entities;
        }

        @Override
        protected void onPostExecute(ArrayList<CaloryEntries> entries) {
            super.onPostExecute(entries);
            PostExecuteCalories(entries);
        }
    }

    public class GetProducts extends  AsyncTask<String, String, ArrayList<StepEntries>> {

        @Override
        protected ArrayList<StepEntries> doInBackground(String... params) {
            boolean reloaded = false;
            ArrayList<StepEntries> entities = new ArrayList<>();
            HasProduct request = new HasProduct();
            Calendar aux = Calendar.getInstance();
            aux.setTime(saturday_wek.getTime());
            do{
                if((aux.getTimeInMillis() < today.getTimeInMillis() || Constants.isSameDay(today, aux)) &&
                        (aux.getTimeInMillis() > min_date.getTimeInMillis() || Constants.isSameDay(aux, min_date) )){
                    v1.app.com.codenutrient.POJO.HasProduct products;
                    if (Constants.isSameDay(today, aux))
                        products = request.ExecuteGET(MainActivity.appUser);
                    else
                        products = request.ExecuteGET(MainActivity.appUser, aux.getTime());
                    StepEntries entries = new StepEntries();
                    int calories = 0;
                    switch (products.getCode()){
                        case 200:
                            for (Product p : products.getProducts()) {
                                calories += p.getCalorias();
                            }
                            entries.setValue(calories);
                            break;
                        case 404:
                            entries.setValue(0);
                            break;
                        case 422:
                            return null;
                        case 401:
                            if(!reloaded){
                                reloaded = true;
                                if (manager.reload(1, getApplicationContext())){
                                    if (Constants.isSameDay(today, aux))
                                        products = request.ExecuteGET(MainActivity.appUser);
                                    else
                                        products = request.ExecuteGET(MainActivity.appUser, aux.getTime());
                                    if (products.getCode() == 200){
                                        for (Product p : products.getProducts()) {
                                            calories += p.getCalorias();
                                        }
                                        entries.setValue(calories);
                                    }else if (products.getCode() == 404)
                                        entries.setValue(0);
                                    else
                                        return null;
                                }
                            }
                            break;
                        default:
                            return null;
                    }
                    entries.setDay(aux);
                    entities.add(entries);
                }else{
                    StepEntries entries = new StepEntries();
                    entries.setValue(0);
                    entries.setDay(aux);
                    entities.add(entries);
                }
                if(Constants.isSameDay(aux, sunday_wek))
                    break;
                aux.roll(Calendar.DATE, 1);
            }while(true);
            return entities;
        }

        @Override
        protected void onPostExecute(ArrayList<StepEntries> entries) {
            super.onPostExecute(entries);
            PostExecuteProducts(entries);
        }
    }
}
