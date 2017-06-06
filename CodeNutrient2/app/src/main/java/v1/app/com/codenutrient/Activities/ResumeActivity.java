package v1.app.com.codenutrient.Activities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import v1.app.com.codenutrient.Adapters.MyNutrientAdapter;
import v1.app.com.codenutrient.Adapters.MyProductAdapter;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.EatenNutrients;
import v1.app.com.codenutrient.POJO.HasProduct;
import v1.app.com.codenutrient.POJO.Item_nutrient;
import v1.app.com.codenutrient.POJO.Measure;
import v1.app.com.codenutrient.POJO.Nutrient;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Values;

/**
 * Created by Arturo on 01/06/2017.
 * This code was made for the project CodeNutrient.
 */
public class ResumeActivity extends AppCompatActivity {

    private ImageButton prev, next;
    private TextView date_text;
    private RecyclerView recyclerView;
    private boolean product;
    private int reload;
    private HttpManager manager;
    private Calendar today, min_date, actual_date;
    private LinearLayoutManager lManager;
    private BNV_response bnv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prev = (ImageButton) findViewById(R.id.prev_date);
        next = (ImageButton) findViewById(R.id.next_date);

        date_text = (TextView) findViewById(R.id.date_text);

        recyclerView = (RecyclerView) findViewById(R.id.resume_recycler);

        product = getIntent().getBooleanExtra("Type", true);
        manager = new HttpManager();

        today = Calendar.getInstance();
        min_date = Calendar.getInstance();
        actual_date = Calendar.getInstance();

        date_text.setText(actual_date.get(Calendar.YEAR) + "/" + (actual_date.get(Calendar.MONTH) + 1) + "/" + actual_date.get(Calendar.DAY_OF_MONTH));

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchUserDate(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if (cursor != null && cursor.moveToFirst()){
                String fecha = cursor.getString(cursor.getColumnIndex("min_fecha"));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                min_date.setTime(formatter.parse(fecha));
            }
        } catch (SQLException |ParseException ignored) {}
        chechDate();
        if(manager.isOnLine(getApplicationContext())){
            reload = 0;
            if (product) {
                new MyTaskProduct().execute();
            }else{
                new MyTaskNutrient().execute();
            }
        }else{
            ShowMesagge("Debes tener conexión a internet para utilizar esta funcionalidad.");
        }
    }

    private void ShowMesagge(String message){
        Snackbar.make(findViewById(R.id.resume_coordinator), message, Snackbar.LENGTH_LONG).show();
    }

    private void Error(String message){
        recyclerView.setVisibility(View.GONE);
        ShowMesagge(message);
        EndRequest();
    }

    private void StartRequest(){
        recyclerView.setAlpha(0.5f);
        if(manager.isOnLine(getApplicationContext())){
            reload = 0;
            prev.setOnClickListener(null);
            prev.setOnClickListener(null);
            if (product) {
                new MyTaskProduct().execute();
            }else{
                new MyTaskNutrient().execute();
            }
        }else{
            Error("Debes tener conexión a internet para utilizar esta funcionalidad.");
        }
    }

    private void EndRequest(){
        chechDate();
    }

    private void chechDate(){
        if(Constants.isSameDay(min_date, actual_date)) {
            prev.setOnClickListener(null);
        }else {
            prev.setOnClickListener(listener);
        }

        if (Constants.isSameDay(actual_date, today)) {
            next.setOnClickListener(null);
        }else {
            next.setOnClickListener(listener);
        }
        recyclerView.setAlpha(1);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.prev_date:
                    actual_date.add(Calendar.DAY_OF_MONTH, -1);
                    date_text.setText(actual_date.get(Calendar.YEAR) + "/" + (actual_date.get(Calendar.MONTH) + 1) + "/" + actual_date.get(Calendar.DAY_OF_MONTH));
                    StartRequest();
                    break;
                case R.id.next_date:
                    actual_date.add(Calendar.DAY_OF_MONTH, 1);
                    date_text.setText(actual_date.get(Calendar.YEAR) + "/" + (actual_date.get(Calendar.MONTH) + 1) + "/" + actual_date.get(Calendar.DAY_OF_MONTH));
                    StartRequest();
                    break;
            }
        }
    };

    private void RecyclerProduct(HasProduct products){
        if (products.getProducts() == null || products.getProducts().size() ==0){
            Error("No se han regisrado productos este día");
            return;
        }
        lManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lManager);

        MyProductAdapter adapter = new MyProductAdapter(SetMeasureName(products.getProducts()), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        EndRequest();
    }

    private ArrayList<Product> SetMeasureName(ArrayList<Product> products){
        ArrayList<Product> aux = new ArrayList<>();
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchMeasures();
            ArrayList<Measure> measures = new ArrayList<>();
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        Measure m = new Measure();
                        m.setMeasure_id(cursor.getInt(cursor.getColumnIndex("id")));
                        m.setMeasure_name(cursor.getString(cursor.getColumnIndex("abreviacion")));
                        measures.add(m);
                    }while (cursor.moveToNext());
                }else{
                    return products;
                }
            }else{
                return products;
            }
            for (Product p : products){
                for (Measure m : measures){
                    if (m.getMeasure_id() == p.getMeasure_id()){
                        p.setMeasure_name(m.getMeasure_name());
                        aux.add(p);
                        break;
                    }
                }
            }
            return aux;
        } catch (SQLException e) {
            return products;
        }
    }

    private void productPostExecute(HasProduct products){
        switch (products.getCode()){
            case 200:
                RecyclerProduct(products);
                break;
            case 404:
                Error("No se han encontrado productos registrados");
                break;
            case 422:
                Error("Ha ocurrido un error inesperado");
                break;
            case 401:
                reload ++;
                if (manager.reload(reload, getApplicationContext()))
                    new MyTaskProduct().execute();
                else
                    Error("Ha ocurrido un error al inicar sesión");
                break;
            default:
                Error("Ha ocurrido un error inesperado");
                break;
        }
    }

    private void RecyclerNutrient(EatenNutrients eatenNutrients){
        reload ++;
        switch (eatenNutrients.getCode()){
            case 200:
                lManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(lManager);
                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                try {
                    helper.openDataBaseRead();
                    Cursor cursorNutrients = helper.fetchNutrients();
                    Cursor cursorMeasures = helper.fetchMeasures();
                    if (cursorNutrients == null || cursorMeasures == null){
                        Error("Ocurrio un error en la base de datos");
                        return;
                    }
                    if (!cursorMeasures.moveToFirst() || !cursorMeasures.moveToFirst()){
                        Error("Ocurrio un error en la base de datos");
                        return;
                    }
                    ArrayList<Nutrient> nutrients = new ArrayList<>();
                    do {
                        Nutrient n = new Nutrient();
                        n.setNutrient_id(cursorNutrients.getInt(cursorNutrients.getColumnIndex("id")));
                        n.setNombre(cursorNutrients.getString(cursorNutrients.getColumnIndex("name")));
                        n.setMeasure_id(cursorNutrients.getInt(cursorNutrients.getColumnIndex("measure_id")));
                        nutrients.add(n);
                    }while (cursorNutrients.moveToNext());
                    ArrayList<Measure> measures = new ArrayList<>();
                    do{
                        Measure m = new Measure();
                        m.setMeasure_id(cursorMeasures.getInt(cursorMeasures.getColumnIndex("id")));
                        m.setMeasure_name(cursorMeasures.getString(cursorMeasures.getColumnIndex("abreviacion")));
                        measures.add(m);
                    }while(cursorMeasures.moveToNext());
                    ArrayList<Item_nutrient> items = new ArrayList<>();
                    for (v1.app.com.codenutrient.POJO.Values v : bnv.getValues()){
                        Item_nutrient item = new Item_nutrient();
                        boolean finded = false;
                        item.setNutrient_recomended(v.getValue());
                        for (Nutrient eaten : eatenNutrients.getNutrients()) {
                            if (eaten.getNutrient_id() == v.getNutrient_id()) {
                                finded = true;
                                item.setNutrient_consumed(eaten.getCantidad());
                                break;
                            }
                        }
                        for (Nutrient n : nutrients) {
                            if(v.getNutrient_id() == n.getNutrient_id()) {
                                item.setNutrient_name(n.getNombre());
                                for (Measure m : measures) {
                                    if (m.getMeasure_id() == n.getMeasure_id()) {
                                        item.setNutrient_measure_name(m.getMeasure_name());
                                    }
                                }
                            }
                        }
                        if (!finded) {
                            item.setNutrient_consumed(0);
                        }
                        if((v.getNutrient_id() <= 9 || v.getNutrient_id() == 19) && (v.getNutrient_id() != 1 && v.getNutrient_id() != 3 && v.getNutrient_id() !=8)){
                            if ((int)item.getNutrient_consumed() > (int)item.getNutrient_recomended()){
                                switch (MainActivity.appUser.getInfoAppUser().getIMCNormal()){
                                    case "BAJO":
                                        item.setNutrient_resume("No es muy recomendable que consumas en exceso este nutriente");
                                        break;
                                    case "NORMAL":
                                        item.setNutrient_resume("Te recomendamos reducir el consumo de este nutriente si quieres evitar padecer de obesidad");
                                        break;
                                    case "SOBREPESO":
                                        item.setNutrient_resume("Es muy recomendable que disminuyas el consumo de este nutriente con el fin de reducir tu IMC");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Debes procurar consumir lo mínimo de este nutriente, no es adecuado para tu salud");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que evites el consumo de este nutriente ya que podría llegar a causarte serios problemas de salud");
                                        break;
                                }
                            }else if ((int)item.getNutrient_consumed() < (int) item.getNutrient_recomended()){
                                switch (MainActivity.appUser.getInfoAppUser().getIMCNormal()){
                                    case "BAJO":
                                        item.setNutrient_resume("Tu consumo de este nutriente esta por debajo del adecuado produra aumentar ligeramente el consumo para evitar algunas enfermedades");
                                        break;
                                    case "NORMAL":
                                        item.setNutrient_resume("Te recomendamos evitar la falta de este nutriente podría ser perjudicial a largo plazo");
                                        break;
                                    case "SOBREPESO":
                                        item.setNutrient_resume("Es recomendable que sigas disminuyendo ligeramente el consumo de este nutriente");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Te sugerimos que disminuyas adecuadamente el consumo de este nutriente");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que continues disminuyendo ligeramente el consumo de este nutriente");
                                        break;
                                }
                            }else{
                                switch (MainActivity.appUser.getInfoAppUser().getIMCNormal()){
                                    case "BAJO":
                                        item.setNutrient_resume("Procura mantener o aumenter ligeramente el consumo de esta sustancia");
                                        break;
                                    case "NORMAL":
                                        item.setNutrient_resume("Bien, has consumido tu valor adecuado");
                                        break;
                                    case "SOBREPESO":
                                        item.setNutrient_resume("Bien has consumido lo adecuado para este nutriente, procura mantener y disminuir ligeramente tu consumo");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Te sugerimos que disminuyas adecuadamente el consumo de este nutriente");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que continues disminuyendo ligeramente el consumo de este nutriente");
                                        break;
                                }
                            }
                        }else if (v.getNutrient_id() == 8) {
                            item.setNutrient_resume("Esta sustancia debe ser evitada por completo, el consumo recomendado debe ser tu máximo consumo, es MUY importante que te mantegas por debajo del mismo");
                        }else{
                            if(item.getNutrient_recomended() <= 5){
                                if (item.getNutrient_consumed() > item.getNutrient_recomended()){
                                    item.setNutrient_resume("El consumo excesivo de este nutriente puede llegar a cuasar algunas enfermedades, te recomendamos que disminuyas el consumo del mismo");
                                }else if (item.getNutrient_consumed() < item.getNutrient_recomended()){
                                    item.setNutrient_resume("Es recomendable que consumas la cantidad adecuada de este nutriente para mantenerte saludable");
                                }else{
                                    item.setNutrient_resume("Has consumido la cantidad adecuada de este nutriente sigue así");
                                }
                            }else{
                                if ((int)item.getNutrient_consumed() > (int)item.getNutrient_recomended()){
                                    item.setNutrient_resume("El consumo excesivo de este nutriente puede llegar a cuasar algunas enfermedades, te recomendamos que disminuyas el consumo del mismo");
                                }else if ((int)item.getNutrient_consumed() < (int) item.getNutrient_recomended()){
                                    item.setNutrient_resume("Es recomendable que consumas la cantidad adecuada de este nutriente para mantenerte saludable");
                                }else{
                                    item.setNutrient_resume("Has consumido la cantidad adecuada de este nutriente sigue así");
                                }
                            }
                        }
                        items.add(item);
                    }
                    EndRequest();
                    MyNutrientAdapter adapter = new MyNutrientAdapter(items);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    helper.close();
                } catch (SQLException e) {
                    Error("Ocurrio un error en la base de datos");
                    return;
                }
                break;
            case 401:
                reload ++;
                if (manager.reload(reload, getApplicationContext())){
                    new MyTaskNutrient().execute();
                }
                break;
            case 404:
                Error("No se ha encontrado tu historial de consumo de nutrientes");
                break;
            case 422:
                Error("La fecha que has enviado esta fuera de las fecha permitidas, vuelve a intentarlo con una fecha valida");
                break;
            default:
                Error("Ocurrio un error inesperado");

        }
    }

    private class MyTaskProduct extends AsyncTask<String, String, HasProduct>{

        @Override
        protected HasProduct doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.HasProduct request = new v1.app.com.codenutrient.Requests.HasProduct();
            if (Constants.isSameDay(actual_date, today))
                return request.ExecuteGET(MainActivity.appUser);
            else
                return request.ExecuteGET(MainActivity.appUser, actual_date.getTime());
        }

        @Override
        protected void onPostExecute(HasProduct hasProduct) {
            super.onPostExecute(hasProduct);
            productPostExecute(hasProduct);
        }
    }

    private class MyTaskNutrient extends AsyncTask<String, String, EatenNutrients>{

        @Override
        protected EatenNutrients doInBackground(String... params) {
            Values v_request = new Values();
            bnv = v_request.ExecuteGET(MainActivity.appUser);
            v1.app.com.codenutrient.Requests.EatenNutrients request = new v1.app.com.codenutrient.Requests.EatenNutrients();
            if (Constants.isSameDay(actual_date, today))
                return request.ExecuteGET(MainActivity.appUser);
            else
                return request.ExecuteGET(MainActivity.appUser, actual_date.getTime());
        }

        @Override
        protected void onPostExecute(EatenNutrients eatenNutrients) {
            super.onPostExecute(eatenNutrients);
            RecyclerNutrient(eatenNutrients);
        }
    }
}
