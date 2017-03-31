package v1.app.com.codenutrient.Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.InfoAppUser;
import v1.app.com.codenutrient.R;

public class MainActivity extends AppCompatActivity {
    public static AppUser appUser;
    private int CAMERA_INTENT = 1;
    public Button about;
    public Button calendario;
    public Button camara;
    public Button cuenta;
    public Button graficas;
    public Button settings;
    public int selected;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.camara = (Button) findViewById(R.id.camara);
        this.cuenta = (Button) findViewById(R.id.cuenta);
        this.graficas = (Button) findViewById(R.id.graficas);
        this.calendario = (Button) findViewById(R.id.calendario);
        this.settings = (Button) findViewById(R.id.settings);
        this.about = (Button) findViewById(R.id.about);
        this.camara.setOnClickListener(onClickListener);
        this.cuenta.setOnClickListener(onClickListener);
        this.graficas.setOnClickListener(onClickListener);
        this.calendario.setOnClickListener(onClickListener);
        this.settings.setOnClickListener(onClickListener);
        this.about.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            selected = v.getId();
            switch (v.getId()) {
                case R.id.camara:
                    isManagerOnline();
                    break;
                case R.id.cuenta:
                    intent = getIntent().setClass(MainActivity.this, InfoAppUserActivity.class);
                    startActivity(intent);
                    break;
                case R.id.graficas:
                    isManagerOnline();
                    break;
                case R.id.calendario:
                    isManagerOnline();
                    break;
            }
        }
    };

    public void isManagerOnline(){
        HttpManager manager = new HttpManager();
        if (manager.isOnLine(getApplicationContext())) {
            MyTaskGET myTaskGET = new MyTaskGET();
            myTaskGET.execute();
        }else {
            Snackbar.make(findViewById(R.id.main_coordinator), "Debes tneer conexión a internet para utilizar esta función", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void postExecute(InfoAppUser infoAppUser){
        if (infoAppUser.getCode() == 200 || infoAppUser.getCode() == 206){
            appUser.setInfoAppUser(infoAppUser);
            Intent intent;
            switch (selected) {
                case R.id.camara:
                    intent = getIntent().setClass(MainActivity.this, CustomViewFinderScannerActivity.class);
                    startActivityForResult(intent, CAMERA_INTENT);
                    break;
                case R.id.graficas:
                    intent = getIntent().setClass(MainActivity.this, CalendarActivity.class);
                    intent.putExtra("Type", false);
                    startActivity(intent);
                    break;
                case R.id.calendario:
                    intent = getIntent().setClass(MainActivity.this, CalendarActivity.class);
                    intent.putExtra("Type", true);
                    startActivity(intent);
                    break;
            }
        }else{
            Snackbar.make(findViewById(R.id.main_coordinator), "Registra tu información personal para utilizar esta opción", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT) {
            if (resultCode == RESULT_OK) {
                String codigo = data.getStringExtra("code");
                Intent intent = new Intent().setClass(this, ProductActivity.class);
                intent.putExtra("code", codigo);
                startActivity(intent);
            }else{
                Snackbar.make(findViewById(R.id.main_coordinator), "No se ha obtenido ningun código", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private class MyTaskGET extends AsyncTask<String, String, InfoAppUser> {

        @Override
        protected InfoAppUser doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.InfoAppUser request = new v1.app.com.codenutrient.Requests.InfoAppUser();
            return request.ExecuteGET(MainActivity.appUser);
        }

        @Override
        protected void onPostExecute(InfoAppUser infoAppUser) {
            postExecute(infoAppUser);
        }
    }
}
