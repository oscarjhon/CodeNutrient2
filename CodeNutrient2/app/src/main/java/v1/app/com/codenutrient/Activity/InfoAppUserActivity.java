package v1.app.com.codenutrient.Activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.POJO.InfoAppUser;
import v1.app.com.codenutrient.R;

public class InfoAppUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public Date Fecha;
    private LinearLayout em_fields;
    private EditText fecha;
    private RadioButton female;
    private LinearLayout lac_fields;
    private RadioButton male;
    private HttpManager manger;
    private EditText peso;
    private int reload;

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
        TextInputLayout til_date = (TextInputLayout) findViewById(R.id.til_date);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        em_fields = (LinearLayout) findViewById(R.id.em_fields);
        lac_fields = (LinearLayout) findViewById(R.id.lac_fields);
        date.setOnClickListener(listener);
        fecha.setOnClickListener(listener);
        til_date.setOnClickListener(listener);
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
                    now.set(Calendar.YEAR, -18);
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

            }
        }
    };


    public void fulled(InfoAppUser infoAppUser) {
        switch (infoAppUser.getCode()) {
            case 0:
                break;
            case 200:
                fecha.setText(infoAppUser.getFechaNacimiento().toString());
                Fecha = infoAppUser.getFechaNacimiento();
                break;
            case 404:
                break;
            case 401:
                reload ++;
                if (!this.manger.isOnLine(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Debes tneer conexión a internet para utilizar esta función", Toast.LENGTH_SHORT).show();
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

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        try {
            this.Fecha = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fecha.setText(date);
    }

    public class MyTaskGET extends AsyncTask<String, String, InfoAppUser> {

        public InfoAppUser doInBackground(String... params) {
            v1.app.com.codenutrient.Requests.InfoAppUser request = new v1.app.com.codenutrient.Requests.InfoAppUser();
            return request.ExecuteGET(MainActivity.appUser);
        }

        public void onPostExecute(InfoAppUser appUser) {
            fulled(appUser);
        }
    }
}
