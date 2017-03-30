package v1.app.com.codenutrient.Activity;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.BNVRequest;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.EatenNutrients;
import v1.app.com.codenutrient.POJO.HasProduct;
import v1.app.com.codenutrient.POJO.Nutrient;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.POJO.n2l;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Products;
import v1.app.com.codenutrient.Requests.Values;

public class ProductActivity extends AppCompatActivity {
    public String code;
    public HttpManager manager;
    public Product product;
    public int reload;
    public Button evaluar;
    public ImageView imageView;
    public TextView presentacion;
    public TextView product_calories;
    public TextView product_name;
    public TextView product_portions;
    public TextView producct_equivalencia;
    public ProgressBar progressBar;
    public Button registrar;
    public v1.app.com.codenutrient.Fragments.Product fragment;
    public boolean showed;
    public int selected_portions = 0;
    private AlertDialog alertDialog1;


    public ProductActivity() {
        product = null;
        reload = 0;
        showed = false;
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manager = new HttpManager();
        code = getIntent().getStringExtra("code");
        product_name = (TextView) findViewById(R.id.product_name);
        product_calories = (TextView) findViewById(R.id.product_calories);
        product_portions = (TextView) findViewById(R.id.product_portions);
        presentacion = (TextView) findViewById(R.id.presentation);
        producct_equivalencia = (TextView) findViewById(R.id.product_equivalencia);
        imageView = (ImageView) findViewById(R.id.product_image);
        progressBar = (ProgressBar) findViewById(R.id.product_progress);
        evaluar = (Button) findViewById(R.id.Evaluate);
        registrar = (Button) findViewById(R.id.register);
        evaluar.setOnClickListener(listener);
        registrar.setOnClickListener(listener);
        fragment = (v1.app.com.codenutrient.Fragments.Product) getFragmentManager().findFragmentById(R.id.product_fragment);
        if (manager.isOnLine(getApplicationContext())) {
            new MyTask().execute(new String[0]);
            return;
        }
        Toast.makeText(getApplicationContext(), "No estas conectado a internet", Toast.LENGTH_SHORT).show();
        finish();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Evaluate:
                    showBiginingMessage();
                    beginEvaliation();
                    break;
                case R.id.register:
                    showed = true;
                    fragment.setData(product);
                default:
            }
        }
    };

    public void setData() {
        reload ++;
        HttpManager manager = new HttpManager();
        switch (product.getHttpcode()) {
            case 0:
                Toast.makeText(getApplicationContext(), "Error del servidor", Toast.LENGTH_SHORT).show();
            case 200:
                try {
                    setData(product, this);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 401:
                if (!this.manager.isOnLine(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "No estas conectado a internet", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (manager.reload(reload, getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    new MyTask().execute();
                }
                break;
            case 404:
                Toast.makeText(getApplicationContext(), "El producto no fue encontrado", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 500:
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error en el servidor", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "No se pudo obtener el producto", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    public void beginEvaliation(){
        if (product.getPorcion() != 1){
            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View spinner_layout = li.inflate(R.layout.my_spinner, null);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductActivity.this);
            alertDialog.setView(spinner_layout);
            final Spinner my_spinner = (Spinner) spinner_layout.findViewById(R.id.spinner_portions);
            ArrayList<String> portions = new ArrayList<>();
            for (int i = 1; i < product.getPorcion(); i++){
                portions.add("" + i);
            }
            String [] aux_portions = new String[portions.size()];
            aux_portions = portions.toArray(aux_portions);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_item, aux_portions);
            my_spinner.setAdapter(adapter);
            alertDialog.setPositiveButton("Evaluar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selected_portions = Integer.parseInt(my_spinner.getSelectedItem().toString());
                    alertDialog1.dismiss();
                    evaluate();

                }
            });
            alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showErrorMessage("Proceso cancelado");
                    alertDialog1.dismiss();
                }
            });
            alertDialog.setTitle("Selecciona el numero de porciones a evaluar");
            alertDialog1 = alertDialog.create();
            alertDialog1.show();
            alertDialog1.setCanceledOnTouchOutside(false);

        }else{
            selected_portions = 1;
            evaluate();
        }
    }

    private void evaluate (){
        EatenNutrients eatenNutrients;
        HasProduct hasProduct;
        BNV_response bnv_response;
        reload = 0;
        if (manager.isOnLine(getApplicationContext())){
            eatenNutrients = ExecuteEatenNutrient();
            if (eatenNutrients != null){
                reload = 0;
                hasProduct = ExecuteProducts();
                if (hasProduct != null){
                    reload = 0;
                    bnv_response = ExecuteBNV();
                    if (bnv_response != null) {
                        String calories_message = EvaluateCalories(hasProduct.getProducts(), product);
                        //Comparar BNV con los nutrientes consumidos
                        ArrayList<Nutrient> nombres = GetNutrientName();
                        if (nombres!= null){
                            switch (calories_message){
                                case "FINE":
                                    int nutrient = EvaluateNutrients(eatenNutrients.getNutrients(), bnv_response.getValues(), product.getNutrients());
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException ignored) {}
                                    if (nutrient == 0){
                                        if (selected_portions != 1)
                                            showSuccessMessage("Es recomendable que consumas "+ new n2l().convertirLetras(selected_portions) + " porciones de este producto");
                                        else
                                            showSuccessMessage("Es recomendable que consumas una porción de este producto");
                                    }else{
                                        String nombre = "";
                                        for (Nutrient nutrient1 : nombres){
                                            if (nutrient == nutrient1.getNutrient_id()){
                                                nombre = nutrient1.getNombre();
                                                break;
                                            }
                                        }
                                        if (selected_portions != 1) {
                                            showSuccessMessage("No es recomendable que consumas " + new n2l().convertirLetras(selected_portions) + " porciones de este producto debido a su alto contenido de " + nombre);
                                        }else{
                                            showSuccessMessage("No es recomendable que consumas ni una porcion de este producto debido a su alto contenido de " + nombre);
                                        }
                                    }
                                    break;
                                default:
                                    showSuccessMessage("Ya has consumido más calorías de las adecuadas, puedes realizar actividad física con el fin de evitar el desbalance");
                            }
                        }else {
                            showErrorMessage();
                        }
                    }else{
                        showErrorMessage();
                    }
                }else{
                    showErrorMessage();
                }
            }else{
                showErrorMessage();
            }
        }else{
            showErrorMessage("Debes tener conexión a internet");
        }
    }

    private EatenNutrients ExecuteEatenNutrient(){
        if (reload != 1){
            reload ++;
            MyGETEaten myGETEaten = new MyGETEaten();
            EatenNutrients eatenNutrients;
            try {
                eatenNutrients = myGETEaten.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
            switch (eatenNutrients.getCode()){
                case 200:
                    return eatenNutrients;
                case 206:
                    eatenNutrients = new EatenNutrients();
                    ArrayList<Nutrient> nutrients = new ArrayList<>();
                    eatenNutrients.setNutrients(nutrients);
                    return eatenNutrients;
                case 401:
                    reload ++;
                    if (manager.reload(reload, getApplicationContext())) {
                        return ExecuteEatenNutrient();
                    } else {
                        return null;
                    }

                default:
                    return null;
            }
        }else{
            return null;
        }
    }

    private HasProduct ExecuteProducts(){
        if (reload != 1){
            reload ++;
            MyGETProducts myGETEaten = new MyGETProducts();
            HasProduct hasProduct;
            try {
                hasProduct = myGETEaten.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
            switch (hasProduct.getCode()){
                case 200:
                    return hasProduct;
                case 206:
                    hasProduct.setProducts(new ArrayList<Product>());
                    return hasProduct;
                case 401:
                    reload ++;
                    if (manager.reload(reload, getApplicationContext())) {
                        return ExecuteProducts();
                    } else {
                        return null;
                    }
                default:
                    return null;
            }
        }else{
            return null;
        }
    }

    private BNV_response ExecuteBNV(){
        if (reload != 1){
            reload ++;
            MyGETBNVTask myGETBNVTask = new MyGETBNVTask();
            BNV_response bnv_response;
            try {
                bnv_response = myGETBNVTask.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
            switch (bnv_response.getCode()){
                case 200:
                    return bnv_response;
                case 401:
                    reload ++;
                    if (manager.reload(reload, getApplicationContext())) {
                        return ExecuteBNV();
                    } else {
                        return null;
                    }
                default:
                    return null;
            }
        }else{
            return null;
        }
    }

    private int EvaluateNutrients(ArrayList<Nutrient> nutrients_consumed, ArrayList<v1.app.com.codenutrient.POJO.Values> BNV, ArrayList<Nutrient> product_nutrients){
        for (Nutrient nutrient3 : product_nutrients){
            float cantidad = 0;
            for (Nutrient nutrient1 : nutrients_consumed){
                if (nutrient3.getNutrient_id() == nutrient1.getNutrient_id()) {
                    cantidad = nutrient1.getCantidad();
                    break;
                }
            }
            for (v1.app.com.codenutrient.POJO.Values nutrient2 : BNV) {
                if (nutrient3.getNutrient_id() == nutrient2.getNutrient_id()){
                    if (nutrient2.getValue() < (nutrient3.getCantidad() * selected_portions) + cantidad){
                        return nutrient2.getNutrient_id();
                    }
                }
            }
        }
        return 0;
    }

    private ArrayList<Nutrient> GetNutrientName(){
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchNutrients();
            if (cursor != null){
                if(cursor.moveToFirst()){
                    ArrayList<Nutrient> nutrients = new ArrayList<>();
                    do{
                        Nutrient nutrient = new Nutrient();
                        nutrient.setNutrient_id(cursor.getInt(cursor.getColumnIndex("id")));
                        nutrient.setNombre(cursor.getString(cursor.getColumnIndex("name")));
                        nutrients.add(nutrient);
                    }while (cursor.moveToNext());
                    return nutrients;
                }else{
                    return null;
                }
            }else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private String EvaluateCalories(ArrayList<Product> products, Product product){
        float calories_consumed = 0;
        for (Product product1 : products){
            calories_consumed += product1.getCalorias();
        }
        if (MainActivity.appUser.getInfoAppUser().getMax_calorias() > calories_consumed + (product.getCalorias() * selected_portions)){
            return "FINE";
        }
        return "BAD";
    }

    private void showBiginingMessage(){
        Snackbar.make(findViewById(R.id.productCoordinator), "El proceso de evaluación tomara un momento, espera por favor", Snackbar.LENGTH_LONG).show();
        evaluar.setEnabled(false);
        registrar.setEnabled(false);
        if (showed){
            fragment.hide();
        }
    }

    private void showSuccessMessage(String message){
        Snackbar.make(findViewById(R.id.productCoordinator), message, Snackbar.LENGTH_LONG).show();
        evaluar.setEnabled(true);
        registrar.setEnabled(true);
        if (showed){
            fragment.show();
        }
    }

    private void showErrorMessage(){
        Snackbar.make(findViewById(R.id.productCoordinator), "Ocurrio un error al evaluar este producto", Snackbar.LENGTH_LONG).show();
        evaluar.setEnabled(true);
        registrar.setEnabled(true);
        if (showed){
            fragment.show();
        }
    }
    private void showErrorMessage(String message){
        Snackbar.make(findViewById(R.id.productCoordinator), message, Snackbar.LENGTH_LONG).show();
        evaluar.setEnabled(true);
        registrar.setEnabled(true);
        if (showed){
            fragment.show();
        }
    }

    public void setData(Product product, Context context) throws SQLException {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dataBaseHelper.openDataBaseReadWrite();
        Cursor cursor = dataBaseHelper.fetchMeasure(product.getMeasure_id());
        if (cursor != null && cursor.moveToFirst()) {
            presentacion.setText("Presentación: " + product.getCantidad() + cursor.getString(cursor.getColumnIndex("abreviacion")));
        }
        product_name.setText(product.getNombre());
        product_calories.setText("Calorias por porción: " + product.getCalorias());
        product_portions.setText("Porciones: " + product.getPorcion());
        producct_equivalencia.setText("Equivalencia: " + product.getEquivalencia());
        Picasso.with(context).load(product.getImageURL()).into(imageView);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);
        product_name.setVisibility(View.VISIBLE);
        product_calories.setVisibility(View.VISIBLE);
        product_portions.setVisibility(View.VISIBLE);
        producct_equivalencia.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        presentacion.setVisibility(View.VISIBLE);
        evaluar.setVisibility(View.VISIBLE);
        registrar.setVisibility(View.VISIBLE);
    }

    private class MyTask extends AsyncTask<String, String, Product> {

        @Override
        protected Product doInBackground(String... param) {
            Products request = new Products();
            AppUser appUser = new AppUser();
            appUser.setUid(MainActivity.appUser.getUid());
            appUser.setToken(MainActivity.appUser.getToken());
            appUser.setProvider(MainActivity.appUser.getProvider());
            return request.ExecuteGET(code, appUser);
        }

        @Override
        protected void onPostExecute(Product result) {
            product = result;
            setData();
        }
    }

    private class MyGETBNVTask extends AsyncTask<String, String, BNV_response> {

        @Override
        protected BNV_response doInBackground(String... params) {
            Values request = new Values();
            return request.ExecuteGET(MainActivity.appUser);
        }
    }

    private class MyGETProducts extends AsyncTask<String, String, HasProduct>{

        @Override
        protected HasProduct doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.HasProduct request = new v1.app.com.codenutrient.Requests.HasProduct();
            return request.ExecuteGET(MainActivity.appUser);
        }
    }

    private class MyGETEaten extends AsyncTask<String, String, EatenNutrients>{

        @Override
        protected EatenNutrients doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.EatenNutrients request = new v1.app.com.codenutrient.Requests.EatenNutrients();
            return request.ExecuteGET(MainActivity.appUser);
        }
    }


}
