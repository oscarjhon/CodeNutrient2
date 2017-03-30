package v1.app.com.codenutrient.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.R;

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

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_calendar);
        mtv = (MaterialCalendarView) findViewById(R.id.calendarView);
        tittle = (TextView) findViewById(R.id.calendar_tittle);
        send = (Button) findViewById(R.id.send_calendar);

        send.setOnClickListener(listener);

        product = getIntent().getBooleanExtra("Type", true);
        setCalendarType(product);
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
    }

    private void beginNutrientRequest(CalendarDay selected_date){
        beginTransaction();
    }

    private void ShowErrorMesaage(String message){
        Snackbar.make(findViewById(R.id.calendar_coordinator), message, Snackbar.LENGTH_LONG);
        send.setEnabled(true);
    }

    private void beginTransaction(){
        Snackbar.make(findViewById(R.id.calendar_coordinator), "Espera un poco mientas se obtiene tu información", Snackbar.LENGTH_LONG);
        send.setEnabled(false);
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

    private Date DefaultDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try {
            date = formatter.parse("2017/01/01");
        } catch (ParseException ignored) {}
        return date;
    }
}
