package v1.app.com.codenutrient.Activity;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Products;

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


    public ProductActivity() {
        product = null;
        reload = 0;
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
        if (this.manager.isOnLine(getApplicationContext())) {
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
                    Toast.makeText(getApplicationContext(), "Te recomendamos consumir este producto dado su alto contenido de calcio", Toast.LENGTH_SHORT).show();
                case R.id.register:
                    Toast.makeText(getApplicationContext(), "Se ha registrado el producto como consumido", Toast.LENGTH_SHORT).show();
                default:
            }
        }
    };

    public void setData() {
        reload ++;
        HttpManager manager = new HttpManager();
        switch (this.product.getCode()) {
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
                    Toast.makeText(getApplicationContext(), "Error al iniciar sesi\u00f3n", Toast.LENGTH_SHORT).show();
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

    public void setData(Product product, Context context) throws SQLException {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dataBaseHelper.openDataBaseReadWrite();
        Cursor cursor = dataBaseHelper.fetchMeasure(product.getMeasure_id());
        if (cursor != null && cursor.moveToFirst()) {
            presentacion.setText("Presentación: " + product.getCantidad() + cursor.getString(cursor.getColumnIndex("abreviacion")));
        }
        product_name.setText(product.getNombre());
        product_calories.setText("Calorias: " + product.getCalorias());
        product_portions.setText("Porciones: " + product.getPorcion());
        producct_equivalencia.setText("Equivalencia: " + product.getEquivalencia());
        Picasso.with(context).load(Constants.ip_addr + product.getImageURL()).into(this.imageView);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);
        product_name.setVisibility(View.VISIBLE);
        product_calories.setVisibility(View.VISIBLE);
        product_portions.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        presentacion.setVisibility(View.VISIBLE);
        evaluar.setVisibility(View.VISIBLE);
        registrar.setVisibility(View.VISIBLE);
    }

    private class MyTask extends AsyncTask<String, String, Product> {

        public Product doInBackground(String... param) {
            Products request = new Products();
            AppUser appUser = new AppUser();
            appUser.setUid(MainActivity.appUser.getUid());
            appUser.setToken(MainActivity.appUser.getToken());
            appUser.setProvider(MainActivity.appUser.getProvider());
            return request.ExecuteGET(code, appUser);
        }

        public void onPostExecute(Product result) {
            product = result;
            setData();
        }
    }


}
