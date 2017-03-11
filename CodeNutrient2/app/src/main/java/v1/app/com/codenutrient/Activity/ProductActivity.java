package v1.app.com.codenutrient.Activity;


import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.sql.SQLException;

import v1.app.com.codenutrient.Fragments.Product_i;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.Product;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Products;

public class ProductActivity extends AppCompatActivity {
    public String code;
    public FragmentManager fm;
    public Product_i fragment;
    public HttpManager manager;
    public Product product;
    public int reload;


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
        fm = getFragmentManager();
        fragment = (Product_i) fm.findFragmentById(R.id.product_iz);
        if (this.manager.isOnLine(getApplicationContext())) {
            new MyTask().execute(new String[0]);
            return;
        }
        Toast.makeText(getApplicationContext(), "No estas conectado a internet", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void setData() {
        reload ++;
        HttpManager manager = new HttpManager();
        switch (this.product.getCode()) {
            case 0:
                Toast.makeText(getApplicationContext(), "Error del servidor", Toast.LENGTH_SHORT).show();
            case 200:
                try {
                    fragment.setData(product, this);
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
