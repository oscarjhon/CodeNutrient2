package v1.app.com.codenutrient.Activities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.BNVRequest;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.InfoAppUser;
import v1.app.com.codenutrient.R;

public class InfoAppUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public Date Fecha;
    private EditText fecha, peso, estatura;
    private ProgressBar info_progress;
    private ScrollView scroll;
    private RadioButton female, male, em_afirmativo, em_negativo, lac_afirmativo, lac_negativo;
    private CheckBox dis_diabtes, dis_hipertension;
    private LinearLayout lac_fields, em_fields;
    private TextInputLayout til_date, til_estatura, til_peso, til_gender, til_pregnancy, til_lactancy;
    private Button send_button;
    private HttpManager manger;
    private int reload;
    private  BNV_response response;

    public InfoAppUserActivity() {
        reload = 0;
        Fecha = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app_user);
        if (!new HttpManager().isOnLine(getApplicationContext())) {
            Snackbar.make(findViewById(R.id.info_coordinator), "No estas conectado a internet", Snackbar.LENGTH_SHORT).show();
            finish();
        }
        ImageButton date = (ImageButton) findViewById(R.id.date_button);
        fecha = (EditText) findViewById(R.id.campo_date);
        peso = (EditText) findViewById(R.id.campo_peso);
        estatura = (EditText) findViewById(R.id.campo_estatura);
        scroll = (ScrollView) findViewById(R.id.scroll);
        info_progress = (ProgressBar) findViewById(R.id.info_progress);

        send_button = (Button) findViewById(R.id.info_send);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        em_afirmativo = (RadioButton) findViewById(R.id.em_afirmativo);
        em_negativo = (RadioButton) findViewById(R.id.em_negativo);
        lac_afirmativo = (RadioButton) findViewById(R.id.lac_afirmativo);
        lac_negativo = (RadioButton) findViewById(R.id.lac_negativo);
        dis_diabtes = (CheckBox) findViewById(R.id.dis_diabetes);
        dis_hipertension = (CheckBox) findViewById(R.id.dis_hipertension);

        em_fields = (LinearLayout) findViewById(R.id.em_fields);
        lac_fields = (LinearLayout) findViewById(R.id.lac_fields);

        til_date = (TextInputLayout) findViewById(R.id.til_date);
        til_estatura = (TextInputLayout) findViewById(R.id.til_estatura);
        til_peso = (TextInputLayout) findViewById(R.id.til_peso);
        til_gender = (TextInputLayout) findViewById(R.id.til_gender);
        til_pregnancy = (TextInputLayout) findViewById(R.id.til_pregnancy);
        til_lactancy = (TextInputLayout) findViewById(R.id.til_lactancy);

        date.setOnClickListener(listener);
        til_date.setOnClickListener(listener);
        send_button.setOnClickListener(listener);
        fecha.setOnClickListener(listener);
        male.setOnClickListener(listener);
        female.setOnClickListener(listener);

        manger = new HttpManager();
        if (manger.isOnLine(getApplicationContext())) {
            MyTaskGET myTaskGET = new MyTaskGET();
            myTaskGET.execute();
        }else {
            Toast.makeText(getApplicationContext(), "Debes tneer conexión a internet para utilizar esta función", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.date_button:
                case R.id.campo_date:
                case R.id.til_date:
                    //Mostar selector de fehca
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.YEAR, -18);
                    DatePickerDialog datePickerDialog;
                    if (Fecha != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(Fecha);
                        datePickerDialog = DatePickerDialog.newInstance(
                                InfoAppUserActivity.this,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.setMaxDate(now);
                        datePickerDialog.showYearPickerFirst(true);
                        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                        datePickerDialog.show(getFragmentManager(), "Fecha de nacimiento");
                    }else {
                        datePickerDialog = DatePickerDialog.newInstance(
                                InfoAppUserActivity.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.showYearPickerFirst(true);
                        datePickerDialog.setMaxDate(now);
                        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                        datePickerDialog.show(getFragmentManager(), "Fecha de nacimiento");
                    }
                    break;
                case R.id.male:
                    em_fields.setVisibility(View.GONE);
                    lac_fields.setVisibility(View.GONE);
                    break;
                case R.id.female:
                    em_fields.setVisibility(View.VISIBLE);
                    lac_fields.setVisibility(View.VISIBLE);
                    break;
                case R.id.info_send:
                    reload = 0;
                    disbleFiels();
                    validFields();
                    break;
            }
        }
    };

    public void validFields(){
        boolean error = false, p = true;
        InfoAppUser infoAppUser = new InfoAppUser();
        //Validar fecha
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, -18);
        Date max = maxDate.getTime();
        if(!(Fecha == null)){
            if (!(max.equals(Fecha) || max.after(Fecha))){
                til_date.setError("Debes tener al menos 18 años");
                error = true;
            }else{
                til_date.setError(null);
                infoAppUser.setFechaNacimiento(Fecha);
            }
        }else{
            til_date.setError("Introduce una fecha de nacimiento");
            error = true;
        }
        //Validar peso
        try {
            float peso1 = Float.parseFloat(String.valueOf(peso.getText()));
            p = false;
            if (peso1 < 30 || peso1 > 300) {
                til_peso.setError("El peso está fuera de los rangos permitidos 30 - 300");
                error = true;
            } else {
                til_peso.setError(null);
                infoAppUser.setPeso(peso1);
            }
            //Validar estatura
            float estatura1 = Float.parseFloat(String.valueOf(estatura.getText()));
            if (estatura1 < 30 || estatura1 > 300) {
                til_estatura.setError("La estatura está fuera de los rangos permitidos 30 -300");
                error = true;
            } else {
                til_estatura.setError(null);
                infoAppUser.setEstatura(estatura1);
            }
        }catch (NumberFormatException e1){
            if(p){
                til_peso.setError("Introduce el peso");
                error = true;
            }else{
                til_estatura.setError("Introduce la estatura");
                error = true;
            }
            if (String.valueOf(estatura.getText()).equals("")){
                til_estatura.setError("Introduce la estatura");
                error = true;
            }
        }
        //validar genero
        if (male.isChecked()){
            til_gender.setError(null);
            infoAppUser.setSexo(true);
        }else if (female.isChecked()){
            infoAppUser.setSexo(true);
            til_gender.setError(null);
            //Validar embarazo y lactancia
            if (em_afirmativo.isChecked()){
                til_pregnancy.setError(null);
                infoAppUser.setEmbarazo(true);
            }else if (em_negativo.isChecked()){
                til_pregnancy.setError(null);
                infoAppUser.setEmbarazo(false);
            }else{
                error = true;
                til_pregnancy.setError("Debes seleccionar alguna opción");
            }
            if(!infoAppUser.isEmbarazo()){
                if (lac_afirmativo.isChecked()){
                    infoAppUser.setLactancia(true);
                    til_lactancy.setError(null);
                }else if(lac_negativo.isChecked()){
                    infoAppUser.setLactancia(false);
                    til_lactancy.setError(null);
                }else{
                    error = true;
                    til_lactancy.setError("Debes seleccionar alguna opción");
                }
            }else{
                infoAppUser.setLactancia(false);
            }
        }else{
            error = true;
            til_gender.setError("Debes seleccionar alguna opción");
        }
        //Enfermedades
        int length = 0;
        if(dis_diabtes.isChecked()){
            length ++;
        }
        if(dis_hipertension.isChecked()){
            length++;
        }
        if (length != 0){
            int[] diseases = new int[length];
            int i = 0;
            if(dis_diabtes.isChecked()){
                diseases[i] = 1;
                i++;
            }
            if(dis_hipertension.isChecked()){
                diseases[i] = 2;
            }
            infoAppUser.setDiseases(diseases);
        }
        if (error){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                enableFields();
            }
            enableFields();
            ShowMessage("No se puede almacenar esta información");
        }else{
            //Calcular calorías
            infoAppUser = Calories(infoAppUser);
            //Hacer POST
            if (manger.isOnLine(getApplicationContext())) {
                MyTaskPOST myTaskPOST = new MyTaskPOST();
                myTaskPOST.execute(infoAppUser);
            }else{
                ShowMessage("Debes tener conexión a internet para enviar esta información");
            }
        }
    }

    public void validatePOSTRespose(InfoAppUser infoAppUser){
        switch (infoAppUser.getCode()){
            case 200:
                //Obtener BNV
                MainActivity.appUser.setInfoAppUser(infoAppUser);
                Calendar hoy = Calendar.getInstance();
                Calendar nacimiento = Calendar.getInstance();
                nacimiento.setTime(infoAppUser.getFechaNacimiento());
                infoAppUser.setEdad(infoAppUser.getYear(hoy, nacimiento));
                ShowMessage("Espera mientras se calcula tu información de consumo");
                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                try {
                    helper.openDataBaseRead();
                    response = new BNV_response();
                    if(infoAppUser.isSexo()){
                        response = response.passCursorToValue(infoAppUser, helper.fetchNVMale(infoAppUser.getEdad()));
                    }else {
                        response = response.passCursorToValue(infoAppUser, helper.fetchNVFemale(infoAppUser.getEdad(), infoAppUser.isEmbarazo() ? 1 : 0, infoAppUser.isLactancia() ? 1 : 0));
                    }
                    helper.close();
                    reload = 0;
                    if (response.getCode() == 200) {
                        if (manger.isOnLine(getApplicationContext())) {
                            BNVRequest request = new BNVRequest();
                            BNV_response response2 = request.execute(response).get();
                            PassBNVrespose(response2);
                        }else{
                            ShowMessage("Debes tener conexión a internet para enviar esta información");
                        }
                    }else{
                        if (response.getCode() == 0){
                            ShowMessage("Null");
                        }else{
                            ShowMessage("Vacio");
                        }
                    }
                } catch (SQLException | InterruptedException | ExecutionException e) {
                    ShowMessage("Ocurrio un error al calcular los valores optimos de consumo");
                }
                break;
            case 206:
                MainActivity.appUser.setInfoAppUser(infoAppUser);
                ShowMessage("No se han podido registrar las enfermedades, intentalo de nuevo");
                break;
            case 401:
                //repetir
                reload ++;
                if (!manger.isOnLine(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Debes tener conexión a internet para utilizar esta función", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (manger.reload(reload, getApplicationContext())) {
                    MyTaskPOST myTaskPOST = new MyTaskPOST();
                    myTaskPOST.execute(infoAppUser);
                } else {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 422:
                //Datos incorrectos
                ShowMessage("Los datos introducidos son incorrectos");
                break;
            default:
                ShowMessage("Ha ocurrido un error inesperado");
                //Error
        }
    }

    public void PassBNVrespose (BNV_response response){
        switch (response.getCode()){
            case 200:
                enableFields();
                ShowMessage("Se ha registrado tu información de consumo");
                break;
            case 401:
                reload ++;
                if (!this.manger.isOnLine(getApplicationContext())) {
                   ShowMessage("Debes tener conexión a internet para realizar esta operacion");
                } else if (manger.reload(reload, getApplicationContext())) {
                    BNVRequest request = new BNVRequest();
                    try {
                        BNV_response response2 = request.execute(this.response).get();
                        PassBNVrespose(response2);
                    } catch (InterruptedException | ExecutionException e) {
                        ShowMessage("No se pudieron registrar tus valores de consumo, vuelve a intentarlo");
                    }
                } else {
                    ShowMessage("Ha ocurrido un error al iniciar sesión");
                }
                break;
            default:
                ShowMessage("No se pudieron registrar tus valores de consumo, vuelve a intentarlo");
                break;
        }
    }

    public InfoAppUser Calories(InfoAppUser infoAppUser){
        Calendar hoy = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(infoAppUser.getFechaNacimiento());
        float RMR;
        int edad = infoAppUser.getYear(hoy, nacimiento);
        infoAppUser.setEdad(edad);
        RMR = infoAppUser.getRMR();
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseReadWrite();
            helper.updateUserAge(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad);
            helper.updateUserCalories(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), RMR);
            if (infoAppUser.isSexo()){
                helper.updateUserRangeMale(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad);
            }else{
                helper.updateUserRangeFemale(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider(), edad, infoAppUser.isEmbarazo() ? 1:0, infoAppUser.isLactancia() ? 1:0);
            }
            helper.close();
        } catch (SQLException ignored) {
           helper.close();
        }
        infoAppUser.setMax_calorias(RMR);
        return infoAppUser;
    }



    public void disbleFiels(){
        send_button.setEnabled(false);
        info_progress.setVisibility(View.VISIBLE);
    }

    public void enableFields(){
        send_button.setEnabled(true);
        info_progress.setVisibility(View.GONE);
    }

    public void showFields(){
        info_progress.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
    }


    public void fulled(InfoAppUser infoAppUser) {
        switch (infoAppUser.getCode()) {
            case 200:
                fecha.setText(infoAppUser.getFechaNacimiento().toString());
                Fecha = infoAppUser.getFechaNacimiento();
                peso.setText(infoAppUser.getPeso() + "");
                estatura.setText(infoAppUser.getEstatura() + "");
                if (infoAppUser.isSexo()){
                    //hombre
                    male.setChecked(true);
                }else{
                    //mujer
                    female.setChecked(true);
                    em_fields.setVisibility(View.VISIBLE);
                    lac_fields.setVisibility(View.VISIBLE);
                    if (infoAppUser.isLactancia()) {
                        lac_afirmativo.setChecked(true);
                    }else{
                        lac_negativo.setChecked(false);
                    }
                    if (infoAppUser.isEmbarazo()){
                        em_afirmativo.setChecked(true);
                    }else{
                        em_negativo.setChecked(true);
                    }
                }
                //Enfermedades
                if (infoAppUser.getDiseases() != null){
                    for (int i = 0; i < infoAppUser.getDiseases().length; i++){
                        switch (infoAppUser.getDiseases()[i]){
                            case 1:
                                dis_diabtes.setChecked(true);
                                break;
                            case 2:
                                dis_hipertension.setChecked(true);
                                break;
                        }
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    showFields();
                }
                showFields();
                break;
            case 404:
                showFields();
                break;
            case 401:
                reload ++;
                if (!this.manger.isOnLine(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Debes tener conexión a internet para utilizar esta función", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (manger.reload(reload, getApplicationContext())) {
                    MyTaskGET myTaskGET = new MyTaskGET();
                    myTaskGET.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    finish();
                }
            case 500:
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error en el servidor", Toast.LENGTH_SHORT).show();
                finish();
            default:
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    public void ShowMessage(String message){
        Snackbar.make(findViewById(R.id.info_coordinator), message, Snackbar.LENGTH_SHORT).show();
        enableFields();
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        try {
            this.Fecha = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fecha.setText(date);
    }

    private class MyTaskGET extends AsyncTask<String, String, InfoAppUser> {

        public InfoAppUser doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.InfoAppUser request = new v1.app.com.codenutrient.Requests.InfoAppUser();
            return request.ExecuteGET(MainActivity.appUser);
        }

        public void onPostExecute(InfoAppUser appUser) {
            fulled(appUser);
        }
    }

    private class MyTaskPOST extends AsyncTask<InfoAppUser, String, InfoAppUser>{

        @Override
        protected InfoAppUser doInBackground(InfoAppUser... params) {
            v1.app.com.codenutrient.Requests.InfoAppUser request = new v1.app.com.codenutrient.Requests.InfoAppUser();
            return request.ExecutePost(params[0], MainActivity.appUser);
        }

        @Override
        protected void onPostExecute(InfoAppUser infoAppUser) {
            validatePOSTRespose(infoAppUser);
        }
    }
}
