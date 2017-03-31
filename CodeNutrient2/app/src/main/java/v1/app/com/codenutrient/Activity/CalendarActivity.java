package v1.app.com.codenutrient.Activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import v1.app.com.codenutrient.Adapters.MyNutrientAdapter;
import v1.app.com.codenutrient.Adapters.MyProductAdapter;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.Helpers.ReloadUser;
import v1.app.com.codenutrient.Helpers.UserRequests;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.EatenNutrients;
import v1.app.com.codenutrient.POJO.HasProduct;
import v1.app.com.codenutrient.POJO.Item_nutrient;
import v1.app.com.codenutrient.POJO.Measure;
import v1.app.com.codenutrient.POJO.Nutrient;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Values;

/**
 * Created by Arturo on 27/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView mtv;
    public boolean product;
    private TextView tittle;
    private Button send;
    private Calendar today;
    private BNV_response bnv;
    private int reload;
    private HttpManager manager;
    private RecyclerView recycler;
    private LinearLayoutManager lManager;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_calendar);
        mtv = (MaterialCalendarView) findViewById(R.id.calendarView);
        tittle = (TextView) findViewById(R.id.calendar_tittle);
        send = (Button) findViewById(R.id.send_calendar);

        recycler = (RecyclerView) findViewById(R.id.calendar_recycler);

        send.setOnClickListener(listener);

        product = getIntent().getBooleanExtra("Type", true);
        setCalendarType(product);
        manager = new HttpManager();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.send_calendar){
                if (product){
                    //Obtener productos en el rango de fechas no mayor a 28 días
                    List<CalendarDay> selected_dates = mtv.getSelectedDates();
                    if (selected_dates.size() > 28){
                        ShowErrorMesaage("Selecciona un rango menor a 28 días");
                    }else{
                        beginProductRequest(selected_dates);
                    }
                }
                else{
                    //Obtener resumen nutrimental
                    CalendarDay selcted_date = mtv.getSelectedDate();
                    beginNutrientRequest(selcted_date);
                }
            }
        }
    };

    private void beginProductRequest(List<CalendarDay> selected_dates){
        beginTransaction();
        if(manager.isOnLine(this)) {
            MyTaskProduct myTaskProduct = new MyTaskProduct();
            myTaskProduct.execute(selected_dates);
        }else{
            ShowErrorMesaage("Debes tener conexión a internet para realizar esta acción");
        }
    }

    private void beginNutrientRequest(CalendarDay selected_date){
        beginTransaction();
        if (manager.isOnLine(this)) {
            MyTaskNutrient myTaskNutrient = new MyTaskNutrient();
            myTaskNutrient.execute(selected_date);
        }else{
            ShowErrorMesaage("Debes tener conexión a internet para realizar esta acción");
        }
    }

    private void ShowErrorMesaage(String message){
        Snackbar.make(findViewById(R.id.calendar_coordinator), message, Snackbar.LENGTH_LONG);
        send.setEnabled(true);
        mtv.setEnabled(true);
    }

    private void beginTransaction(){
        Snackbar.make(findViewById(R.id.calendar_coordinator), "Espera un poco mientas se obtiene tu información", Snackbar.LENGTH_LONG);
        send.setEnabled(false);
        reload = 0;
        mtv.setEnabled(false);
    }

    private void setCalendarType(boolean product){
        if (product){
            tittle.setText(R.string.range_dates);
            mtv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        }else{
            tittle.setText(R.string.single_date);
            mtv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        }

        today = Calendar.getInstance();
        Calendar Minimum_date = Calendar.getInstance();
        Date date;
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        mtv.setSelectedDate(today);
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchUserDate(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if(cursor != null){
                cursor.moveToFirst();
                String fecha = cursor.getString(cursor.getColumnIndex("min_fecha"));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                try {
                    date = formatter.parse(fecha);
                } catch (ParseException ignored) {
                    date = DefaultDate();
                }
            }else{
                date = DefaultDate();
            }
        } catch (SQLException e) {
            date = DefaultDate();
        }
        Minimum_date.setTime(date);
        mtv.state().edit()
                .setMinimumDate(Minimum_date)
                .setMaximumDate(today)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
    }

    private void RecyclerProduct(ArrayList<HasProduct> hasProducts){
        reload ++;
        if (hasProducts.size() == 0){
            ShowErrorMesaage("No se ha obtenido ningun producto");
            return;
        }
        if (hasProducts.size() == 1){
            switch (hasProducts.get(0).getCode()){
                case 200:
                    EndRequest();
                    lManager = new LinearLayoutManager(this);
                    recycler.setLayoutManager(lManager);
                    MyProductAdapter adapter = new MyProductAdapter(hasProducts.get(0).getProducts(), this);
                    recycler.setAdapter(adapter);
                    break;
                case 401:
                    if (reload != 1){
                        ShowErrorMesaage("Ha ocurrido un error al iniciar sesión");
                        return;
                    }else{
                        if (manager.reload(reload, getApplicationContext())) {
                            List<CalendarDay> calendarDays = mtv.getSelectedDates();
                            MyTaskProduct myTaskProduct = new MyTaskProduct();
                            myTaskProduct.execute(calendarDays);
                        }
                    }
                    break;
                case 422:
                    ShowErrorMesaage("Selecciona otras fechas, estas están fuera del rango permitido");
                    return;
                default:
                    ShowErrorMesaage("Ha ocurrido un error inesperado");
            }
        }else{
            ArrayList<Product> products = new ArrayList<>();
            for (HasProduct product : hasProducts){
                if (product.getCode() == 200){
                    ArrayList<Product> productos = product.getProducts();
                    products.addAll(productos);
                }
            }
            EndRequest();
            lManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(lManager);
            MyProductAdapter adapter = new MyProductAdapter(products, this);
            recycler.setAdapter(adapter);
        }

    }

    private void RecyclerNutrient(EatenNutrients eatenNutrients){
        reload ++;
        switch (eatenNutrients.getCode()){
            case 200:
                lManager = new LinearLayoutManager(this);
                recycler.setLayoutManager(lManager);
                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                try {
                    helper.openDataBaseRead();
                    Cursor cursorNutrients = helper.fetchNutrients();
                    Cursor cursorMeasures = helper.fetchMeasures();
                    if (cursorNutrients == null || cursorMeasures == null){
                        ShowErrorMesaage("Ocurrio un error en la base de datos");
                        return;
                    }
                    if (!cursorMeasures.moveToFirst() || !cursorMeasures.moveToFirst()){
                        ShowErrorMesaage("Ocurrio un error en la base de datos");
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
                        if(v.getNutrient_id() <= 9 || v.getNutrient_id() == 19 && v.getNutrient_id() != 1 && v.getNutrient_id() != 3 && v.getNutrient_id() !=8){
                            if ((int)item.getNutrient_consumed() > (int)item.getNutrient_recomended()){
                                switch (MainActivity.appUser.getInfoAppUser().getIMCNormal()){
                                    case "BAJO":
                                        item.setNutrient_resume("No es muy recomendable que consumas en exceso esta sustancias");
                                        break;
                                    case "NORMAL":
                                        item.setNutrient_resume("Te recomendamos reducir el consumo de esta sustancias si quieres evitar padecer de obesidad");
                                        break;
                                    case "SOBREPESO":
                                        item.setNutrient_resume("Es muy recomendable que disminuyas el consumo de esta sustancias con el fin de reducir tu IMC");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Debes procurar consumir lo mínimo de esta sustancia, no es adecuado para tu salud");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que evites el consumo de esta sustancia ya que podría llegar a causarte serios problemas de salud");
                                        break;
                                }
                            }else if ((int)item.getNutrient_consumed() < (int) item.getNutrient_recomended()){
                                switch (MainActivity.appUser.getInfoAppUser().getIMCNormal()){
                                    case "BAJO":
                                        item.setNutrient_resume("Tu consumo de esta sustancia esta por debajo del adecuado produra aumentar ligeramente el consumo para evitar algunas enfermedades");
                                        break;
                                    case "NORMAL":
                                        item.setNutrient_resume("Te recomendamos evitar la falta de esta sustancia podría ser perjudicial a largo plazo");
                                        break;
                                    case "SOBREPESO":
                                        item.setNutrient_resume("Es recomendable que sigas disminuyendo ligeramente el consumo de esta sustancia");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Te sugerimos que disminuyas adecuadamente el consumo de esta sustancia");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que continues disminuyendo ligeramente el consumo de esta sustencia");
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
                                        item.setNutrient_resume("Bien has consumido lo adecuado para esta sustencia, procura mantener y disminuir ligeramente tu consumo");
                                        break;
                                    case "OBESIDAD":
                                        item.setNutrient_resume("Te sugerimos que disminuyas adecuadamente el consumo de esta sustancia");
                                        break;
                                    default:
                                        item.setNutrient_resume("Es imprecindible que continues disminuyendo ligeramente el consumo de esta sustencia");
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
                    recycler.setAdapter(adapter);
                    helper.close();
                } catch (SQLException e) {
                    ShowErrorMesaage("Ocurrio un error en la base de datos");
                    return;
                }
                break;
            case 401:
                if (manager.reload(reload, getApplicationContext())){
                    MyTaskNutrient myTaskNutrient = new MyTaskNutrient();
                    myTaskNutrient.execute(mtv.getSelectedDate());
                }
                break;
            case 404:
                ShowErrorMesaage("Te recomendamos que consumas la cantidad adecuada de nutrientes");
                break;
            case 422:
                ShowErrorMesaage("La fecha que has enviado esta fuera de las fecha permitidas, vuelve a intentarlo con una fecha valida");
                break;
            default:
                ShowErrorMesaage("Ocurrio un error inesperado");

        }
    }

    private void EndRequest(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
        send.setEnabled(true);
        mtv.setEnabled(true);
    }

    private Date DefaultDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try {
            date = formatter.parse("2017/01/01");
        } catch (ParseException ignored) {}
        return date;
    }

    private class MyTaskProduct extends AsyncTask<List<CalendarDay>, String, ArrayList<HasProduct>>{

        @Override
        protected final ArrayList<HasProduct> doInBackground(List<CalendarDay>... params) {
            v1.app.com.codenutrient.Requests.HasProduct request = new v1.app.com.codenutrient.Requests.HasProduct();
            ArrayList<HasProduct> hasProducts = new ArrayList<>();
            for (CalendarDay day : params[0]){
                HasProduct hasProduct;
                if (day.getCalendar().equals(today)) {
                    hasProduct = request.ExecuteGET(MainActivity.appUser);
                    if (hasProduct.getCode() == 200 || hasProduct.getCode() == 404){
                        hasProducts.add(hasProduct);
                    }else{
                        ArrayList<HasProduct> aux = new ArrayList<>();
                        aux.add(hasProduct);
                        return aux;
                    }
                }else {
                    hasProduct = request.ExecuteGET(MainActivity.appUser, day.getDate());
                    if (hasProduct.getCode() == 200 || hasProduct.getCode() == 404){
                        hasProducts.add(hasProduct);
                    }else{
                        ArrayList<HasProduct> aux = new ArrayList<>();
                        aux.add(hasProduct);
                        return aux;
                    }
                }
            }
            return hasProducts;
        }

        @Override
        protected void onPostExecute(ArrayList<HasProduct> hasProducts) {
            RecyclerProduct(hasProducts);
        }
    }

    private class MyTaskNutrient extends AsyncTask<CalendarDay, String, EatenNutrients>{

        @Override
        protected EatenNutrients doInBackground(CalendarDay... params) {
            Values v_request = new Values();
            bnv = v_request.ExecuteGET(MainActivity.appUser);
            v1.app.com.codenutrient.Requests.EatenNutrients request = new v1.app.com.codenutrient.Requests.EatenNutrients();
            if (params[0].getCalendar().equals(today))
                return request.ExecuteGET(MainActivity.appUser);
            else
                return request.ExecuteGET(MainActivity.appUser, params[0].getDate());
        }

        @Override
        protected void onPostExecute(EatenNutrients eatenNutrients) {
            RecyclerNutrient(eatenNutrients);
        }
    }
}
