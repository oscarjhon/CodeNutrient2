package v1.app.com.codenutrient.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;
import java.util.Calendar;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.Helpers.ReloadBNV;
import v1.app.com.codenutrient.Helpers.ReloadUser;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.BNV_response;
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
    public int reload;
    public HttpManager manager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.camara = (Button) findViewById(R.id.camara);
        this.cuenta = (Button) findViewById(R.id.cuenta);
        this.graficas = (Button) findViewById(R.id.graficas);
        this.calendario = (Button) findViewById(R.id.calendario);
        //this.settings = (Button) findViewById(R.id.settings);
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
        manager = new HttpManager();
        if (manager.isOnLine(getApplicationContext())) {
            MyTaskGET myTaskGET = new MyTaskGET();
            myTaskGET.execute();
            reload = 1;
        }else {
            Snackbar.make(findViewById(R.id.main_coordinator), "Debes tneer conexión a internet para utilizar esta función", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void postExecute(InfoAppUser infoAppUser){
        if (infoAppUser.getCode() == 200 || infoAppUser.getCode() == 206){
            appUser.setInfoAppUser(infoAppUser);
            reload = 1;
            updateEdad();
        }else if (infoAppUser.getCode() == 401) {
            if (manager.reload(reload, getApplicationContext())){
                reload ++;
                MyTaskGET myTaskGET = new MyTaskGET();
                myTaskGET.execute();
            }else{
                ShowErrorMessage("Ocurrio un error al iniciar sesión");
            }
        }else{
            ShowErrorMessage("Registra tu información personal para utilizar esta opción");
        }
    }

    private void ShowErrorMessage(String Message){
        Snackbar.make(findViewById(R.id.main_coordinator), Message, Snackbar.LENGTH_SHORT).show();
    }

    private void lastPostExecute(){
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
    }

    public void updateEdad(){
        Calendar calendar = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(MainActivity.appUser.getInfoAppUser().getFechaNacimiento());
        DataBaseHelper helper = new DataBaseHelper(this);
        try {
            helper.openDataBaseReadWrite();
            Cursor c = helper.fetchUserEdad(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if (c != null){
                if (c.moveToFirst()){
                    int edad = c.getInt(c.getColumnIndex("id"));
                    if (edad != MainActivity.appUser.getInfoAppUser().getYear(calendar, nacimiento)){
                        /**
                         * Actualizar valores de infoAppUser y BNV
                         */
                        MainActivity.appUser.getInfoAppUser().setEdad(edad);
                        float RMR = MainActivity.appUser.getInfoAppUser().getRMR();
                        helper.updateUserCalories(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), RMR);
                        MyTaskUpdate update = new MyTaskUpdate();
                        update.execute();
                    }
                    else{
                        lastPostExecute();
                    }
                }
            }
        } catch (SQLException ignored) {}
    }

    public void postExecuteUpdate(InfoAppUser infoAppUser){
        if (infoAppUser.getCode() == 200 || infoAppUser.getCode() == 206) {
            ReloadBNV reloadBNV = new ReloadBNV();
            BNV_response response = reloadBNV.reload(MainActivity.appUser.getInfoAppUser(), getApplicationContext());
            if (response.getCode() == 200){
                lastPostExecute();
            }else if(response.getCode() == 401){
                if(manager.reload(1, getApplicationContext())){
                    response = reloadBNV.reload(MainActivity.appUser.getInfoAppUser(), getApplicationContext());
                    if (response.getCode() == 200) {
                        lastPostExecute();
                    }else{
                        ShowErrorMessage("Ocurrio un error al actualizar tu información");
                    }
                }else{
                    ShowErrorMessage("Ocurrio un error al iniciar sesión");
                }
            }
        }else if (infoAppUser.getCode() == 401){
            if (manager.reload(reload, getApplicationContext())){
                MyTaskUpdate update = new MyTaskUpdate();
                update.execute();
            }else{
                ShowErrorMessage("Ocurrio un error al iniciar sesión");
            }
        }else{
            ShowErrorMessage("Registra tu información personal para utilizar esta opción");
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

    private class MyTaskUpdate extends AsyncTask<String, String, InfoAppUser>{

        @Override
        protected InfoAppUser doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.InfoAppUser request = new v1.app.com.codenutrient.Requests.InfoAppUser();
            return request.UpdateCalories(MainActivity.appUser.getInfoAppUser(), MainActivity.appUser);
        }

        @Override
        protected void onPostExecute(InfoAppUser infoAppUser) {
            super.onPostExecute(infoAppUser);
            postExecuteUpdate(infoAppUser);

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
