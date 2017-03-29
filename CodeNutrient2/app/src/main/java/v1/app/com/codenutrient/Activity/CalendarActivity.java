package v1.app.com.codenutrient.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_calendar);
        mtv = (MaterialCalendarView) findViewById(R.id.calendarView);
        product = getIntent().getBooleanExtra("Type", true);
        setCalendarType(product);
    }

    private OnDateSelectedListener listener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        }
    };

    private void setCalendarType(boolean product){
        if (product){
            mtv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        }else{
            mtv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
            mtv.setOnDateChangedListener(listener);
        }
        Calendar today = Calendar.getInstance();
        Calendar Minimum_date = Calendar.getInstance();
        Date date;
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
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
