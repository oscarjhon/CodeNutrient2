package v1.app.com.codenutrient.Activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.POJO.StepEntries;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Calories;
import v1.app.com.codenutrient.Requests.HasProduct;

/**
 * Created by Arturo on 07/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class StepsActivity extends AppCompatActivity {

    private Spinner spinner;
    private LineChart lineChart;
    private int days, month;
    private HttpManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = (Spinner) findViewById(R.id.steps_spinner);
        lineChart = (LineChart) findViewById(R.id.charts);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.steps_spinner, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerEvent());
        days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);

        manager = new HttpManager();
    }

    private void PrepareRequest(int position){
        spinner.setEnabled(false);
        switch(position){
            case 1:
                if (manager.isOnLine(getApplicationContext())){
                    GetProducts task = new GetProducts();
                    task.execute();
                }else{
                    ShowErrorMessage("Debes tener conexión a internet para consultar esta información");
                }
                break;
            case 2:
                if (manager.isOnLine(getApplicationContext())){

                }else{
                    ShowErrorMessage("Debes tener conexión a internet para consultar esta información");
                }
                break;
            case 3:
                new GetSteps().steps();
                break;
        }
    }

    public void passStepEntryesToList(ArrayList<StepEntries> entries, String message){
        List<Entry> entryList = new ArrayList<>();
        if (entries != null) {
            for (int i = 1; i <= days; i++) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, i);
                boolean isSet = false;
                for (int j = 0; j < entries.size(); j++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(entries.get(j).getDay());
                    if (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                        isSet = true;
                        entryList.add(new Entry(i, entries.get(j).getValue()));
                        entries.remove(j);
                    }
                }
                if (!isSet) {
                    entryList.add(new Entry(i, 0));
                }
            }
            ShowSuccess(entryList, message);
        }else{
            ShowErrorMessage("Ocurrio un error inesperado al obtener tu información");
        }
    }

    public void ShowErrorMessage(String message){
        Snackbar.make(findViewById(R.id.steps_coordinator), message, Snackbar.LENGTH_LONG);
    }

    public String GetMonthName(){
        switch (month){
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return  "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            default:
                return "Diciembre";
        }
    }

    public void ShowSuccess(List<Entry> entries, String label){
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(R.color.colorPrimaryDark);
        dataSet.setValueTextColor(R.color.colorPrimaryLight);
        LineData data = new LineData(dataSet);
        Description description = new Description();
        description.setText(label);
        description.setTextSize(10);
        description.setTextColor(R.color.primaryText);
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.notifyDataSetChanged();
        lineChart.setDescription(description);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(true);
    }

    public class GetSteps{
        public void steps(){
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                helper.openDataBaseRead();
                Cursor cursor = helper.fetchUser(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
                if (cursor != null){
                    if (cursor.moveToFirst()){
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        cursor = helper.fetchSteps(id);
                        if (cursor != null){
                            ArrayList<StepEntries> stepEntries = new ArrayList<>();
                            if (cursor.moveToFirst()){
                                do{
                                    StepEntries entry = new StepEntries();
                                    entry.setDay(format.parse(cursor.getString(cursor.getColumnIndex("fecha"))));
                                    entry.setValue(cursor.getFloat(cursor.getColumnIndex("steps")));
                                    stepEntries.add(entry);
                                }while (cursor.moveToNext());
                                invertArray(stepEntries);
                            }else{
                                ShowErrorMessage("Aun no se han registrado ninguna actividad");
                            }
                        }else{
                            ShowErrorMessage("Ocurrio un error inesperado, vuelve a intentarlo más tarde");
                        }
                    }else{
                        ShowErrorMessage("Ocurrio un error inesperado, vuelve a intentarlo más tarde");
                    }
                }else{
                    ShowErrorMessage("Ocurrio un error inesperado, vuelve a intentarlo más tarde");
                }
            } catch (SQLException | ParseException e) {
                ShowErrorMessage("Ocurrio un error inesperado, vuelve a intentarlo más tarde");
            }
        }
    }

    public void invertArray(ArrayList<StepEntries> entries){
        ArrayList<StepEntries> entries1 = new ArrayList<>();
        for (int i = 1; i <= days; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, i);
            for (StepEntries entry : entries){
                Calendar cal = Calendar.getInstance();
                cal.setTime(entry.getDay());
                if (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
                    entries1.add(entry);
                }
            }
        }
        passStepEntryesToList(entries1, "Pasos recorridos durante " + GetMonthName());
    }

    public class GetProducts extends  AsyncTask<String, String, ArrayList<StepEntries>> {

        @Override
        protected ArrayList<StepEntries> doInBackground(String... params) {
            boolean reloaded = false;
            Calendar cal = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            HasProduct request = new HasProduct();
            ArrayList<StepEntries> entities = new ArrayList<>();
            for (int i = 1; i <= days; i++){
                v1.app.com.codenutrient.POJO.HasProduct products;
                if(cal.get(Calendar.DAY_OF_MONTH) <= today.get(Calendar.DAY_OF_MONTH)) {
                    if (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)){
                        products = request.ExecuteGET(MainActivity.appUser);
                    }else {
                        cal.set(Calendar.DAY_OF_MONTH, i);
                        products =request.ExecuteGET(MainActivity.appUser, cal.getTime());
                    }
                    if (products.getCode() == 200){
                        int calories = 0;
                        for(Product p : products.getProducts()){
                            calories += p.getCalorias();
                        }
                        StepEntries entries = new StepEntries();
                        entries.setValue(calories);
                        entries.setDay(cal.getTime());
                        entities.add(entries);
                    }else if (products.getCode() == 401){
                        if (!reloaded) {
                            if (manager.reload(1, getApplicationContext())){
                                reloaded = true;
                                i--;
                            }
                        }
                    }else{
                        return null;
                    }

                }
            }
            return entities;
        }

        @Override
        protected void onPostExecute(ArrayList<StepEntries> entries) {
            super.onPostExecute(entries);
            passStepEntryesToList(entries, "Calorías consumidas durante " + GetMonthName() );
        }
    }

    public class GetCalories extends AsyncTask<String, String, ArrayList<StepEntries>>{

        @Override
        protected ArrayList<StepEntries> doInBackground(String... params) {
            boolean reloaded = false;
            Calendar cal = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            Calories request = new Calories();
            ArrayList<StepEntries> entities = new ArrayList<>();
            for (int i = 1; i <= days; i++){
                v1.app.com.codenutrient.POJO.Calories calories;
                if(cal.get(Calendar.DAY_OF_MONTH) <= today.get(Calendar.DAY_OF_MONTH)) {
                    if (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)){
                         calories = request.ExecuteGET(MainActivity.appUser);
                    }else {
                        cal.set(Calendar.DAY_OF_MONTH, i);
                        calories =request.ExecuteGET(MainActivity.appUser, cal.getTime());
                    }
                    if (calories.getCode() == 200){
                        StepEntries entries = new StepEntries();
                        entries.setValue(calories.getGasto());
                        entries.setDay(cal.getTime());
                        entities.add(entries);
                    }else if (calories.getCode() == 401){
                        if (!reloaded) {
                            if (manager.reload(1, getApplicationContext())){
                                reloaded = true;
                                i--;
                            }else{
                                return null;
                            }
                        }
                    }else{
                        return null;
                    }

                }
            }
            return entities;
        }

        @Override
        protected void onPostExecute(ArrayList<StepEntries> entries) {
            super.onPostExecute(entries);
            passStepEntryesToList(entries, "Calorías quemadas durante " + GetMonthName() );
        }
    }

    public class SpinnerEvent implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0)
                PrepareRequest(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
