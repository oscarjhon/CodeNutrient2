package v1.app.com.codenutrient.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.Helpers.ReloadBNV;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.InfoAppUser;
import v1.app.com.codenutrient.R;
import v1.app.com.codenutrient.Requests.Calories;
import v1.app.com.codenutrient.Services.Pedometer;

public class MainActivity extends AppCompatActivity {
    public static AppUser appUser;
    private int CAMERA_INTENT = 1;
    public Button calory;
    public Button calendario;
    public Button camara;
    public Button cuenta;
    public Button graficas;
    public Button steps;
    public CoordinatorLayout coordinator;
    public int selected;
    public int reload;
    public HttpManager manager;
    public Intent mServiceIntent;
    private Pedometer service;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camara = (Button) findViewById(R.id.camara);
        cuenta = (Button) findViewById(R.id.cuenta);
        graficas = (Button) findViewById(R.id.graficas);
        calendario = (Button) findViewById(R.id.calendario);
        coordinator = (CoordinatorLayout) findViewById(R.id.main_coordinator);
        steps = (Button) findViewById(R.id.steps);
        calory = (Button) findViewById(R.id.calory);
        camara.setOnClickListener(onClickListener);
        cuenta.setOnClickListener(onClickListener);
        graficas.setOnClickListener(onClickListener);
        calendario.setOnClickListener(onClickListener);
        steps.setOnClickListener(onClickListener);
        calory.setOnClickListener(onClickListener);
        service = new Pedometer();
        mServiceIntent = new Intent(this, service.getClass());
        checkNSendCalories();
        coordinator.setVisibility(View.VISIBLE);
        //Enviar pasos y calorias si las hay
        //Habilitar campos (Los campos deben estar inactivos)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            Toast.makeText(getApplicationContext(), "Cerrar sesión", Toast.LENGTH_SHORT).show();
            signOut();

        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(LoginActivity.googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent().setClass(MainActivity.this, LoginActivity.class);

                        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                        try {
                            helper.openDataBaseReadWrite();
                            helper.updateUsersStatus();
                            helper.close();
                            MainActivity.appUser = null;
                            startActivity(intent);
                            finish();
                        } catch (SQLException e) {
                            ShowErrorMessage("Ha ocurrido un error inesperado");
                        }
                    }
                });
    }

    public void checkNSendCalories(){
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Espera mientras se actualiza tu información", Toast.LENGTH_SHORT).show();
        try {
            helper.openDataBaseReadWrite();
            Cursor cursor = helper.fetchUserId(appUser.getProvider(), appUser.getUid());
            if (cursor != null && cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Cursor cursor2 = helper.fetchCaloriesNotSended(id);
                if(cursor2 != null && cursor2.moveToFirst()){
                    do {
                        if(cursor2.getFloat(cursor2.getColumnIndex("calories")) != 0)
                        {
                            Calories calories = new Calories();
                            switch (calories.ExecutePOST(appUser, cursor2.getFloat(cursor2.getColumnIndex("calories")), new SimpleDateFormat("yyyy-MM-dd").parse(cursor2.getString(cursor2.getColumnIndex("fecha"))))) {
                                case 200:
                                    helper.updateCaloriesSended(cursor2.getString(cursor2.getColumnIndex("fecha")), id);
                                    break;
                                case 401:
                                    if (manager.reload(1, getApplicationContext())) {
                                        switch (calories.ExecutePOST(appUser, cursor2.getFloat(cursor2.getColumnIndex("calories")), new SimpleDateFormat("yyyy-MM-dd").parse(cursor2.getString(cursor2.getColumnIndex("fecha"))))) {
                                            case 200:
                                                helper.updateCaloriesSended(cursor.getString(cursor.getColumnIndex("fecha")), id);
                                                break;
                                        }
                                    }
                            }
                        }
                    }while (cursor.moveToNext());
                    helper.close();
                }
            }
        } catch (SQLException ignored) {} catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
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
                case R.id.steps:
                    isManagerOnline();
                    break;
                case R.id.calory:
                    isManagerOnline();
                    break;
            }
        }
    };

    public void isManagerOnline(){
        if (MainActivity.appUser.getInfoAppUser() != null){
            lastPostExecute();
        }else {
            manager = new HttpManager();
            if (manager.isOnLine(getApplicationContext())) {
                MyTaskGET myTaskGET = new MyTaskGET();
                myTaskGET.execute();
                reload = 1;
            } else {
                Snackbar.make(findViewById(R.id.main_coordinator), "Debes tneer conexión a internet para utilizar esta función", Snackbar.LENGTH_SHORT).show();
            }
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
                intent = getIntent().setClass(MainActivity.this, ResumeActivity.class);
                intent.putExtra("Type", false);
                startActivity(intent);
                break;
            case R.id.calendario:
                intent = getIntent().setClass(MainActivity.this, ResumeActivity.class);
                intent.putExtra("Type", true);
                startActivity(intent);
                break;
            case R.id.steps:
                intent = getIntent().setClass(MainActivity.this, StepsActivity.class);
                startActivity(intent);
                break;
            case R.id.calory:
                intent = getIntent().setClass(MainActivity.this, CaloryActivity.class);
                startActivity(intent);
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
                    int edad = c.getInt(c.getColumnIndex("edad"));
                    if (edad != MainActivity.appUser.getInfoAppUser().getYear(calendar, nacimiento)){
                        /**
                         * Actualizar valores de infoAppUser y BNV
                         */
                        MainActivity.appUser.getInfoAppUser().setEdad(edad);
                        float RMR = MainActivity.appUser.getInfoAppUser().getRMR();
                        /**
                         * Actuializar METS
                         */
                        double RMR_passed = (((RMR/1440)/5)/MainActivity.appUser.getInfoAppUser().getPeso()) * 1000;
                        float caminar = (float) ((float) (3.3 * 3.5) / RMR_passed);
                        float trotar = (float) ((float) (5 * 3.5) / RMR_passed);
                        float correr = (float) ((float) (9 * 3.5) / RMR_passed);
                        helper.updateUserCalories(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), RMR);
                        if(helper.checkMETS(MainActivity.appUser.getProvider(), MainActivity.appUser.getUid())){
                            //Actualizar METS
                            helper.updateMETS(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), caminar, trotar, correr);
                        }else{
                            //REGISTRAR METS
                            Cursor cursor = helper.fetchUserId(MainActivity.appUser.getProvider(), MainActivity.appUser.getUid());
                            if (cursor != null && cursor.moveToFirst()){
                                long id = cursor.getLong(cursor.getColumnIndex("id"));
                                helper.insertMETS(id, caminar, trotar, correr);
                            }
                        }
                        helper.updateUserAge(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad);
                        if (MainActivity.appUser.getInfoAppUser().isSexo()){
                            helper.updateUserRangeMale(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad);
                        }else{
                            helper.updateUserRangeFemale(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad, MainActivity.appUser.getInfoAppUser().isEmbarazo() ? 1:0, MainActivity.appUser.getInfoAppUser().isLactancia() ? 1:0);
                        }
                        helper.close();
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
            MainActivity.appUser.setInfoAppUser(infoAppUser);
            Calendar calendar = Calendar.getInstance();
            Calendar nacimiento = Calendar.getInstance();
            nacimiento.setTime(MainActivity.appUser.getInfoAppUser().getFechaNacimiento());
            switch (MainActivity.appUser.getInfoAppUser().getYear(calendar, nacimiento)){
                case 19:
                case 31:
                case 51:
                case 70:
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
                    break;
                default:
                    lastPostExecute();

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

    @Override
    protected void onPause() {
        super.onPause();
        if (!isMyServiceRunning(service.getClass())){
            startService(mServiceIntent);
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
