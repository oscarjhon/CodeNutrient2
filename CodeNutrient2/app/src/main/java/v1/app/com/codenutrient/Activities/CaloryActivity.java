package v1.app.com.codenutrient.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.github.mikephil.charting.charts.LineChart;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.R;

/**
 * Created by Arturo on 05/06/2017.
 * This code was made for the project CodeNutrient.
 */
public class CaloryActivity extends AppCompatActivity {

    private ImageButton prev, next;
    private LineChart graph;
    private Calendar min_date, sunday_wek, saturday_wek, today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calory);
        prev = (ImageButton) findViewById(R.id.before_calory);
        next = (ImageButton) findViewById(R.id.next_calory);
        graph = (LineChart) findViewById(R.id.calory_charts);

        today = Calendar.getInstance();
        sunday_wek = Calendar.getInstance();
        saturday_wek = Calendar.getInstance();
        min_date = Calendar.getInstance();

        sunday_wek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        saturday_wek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);


        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseRead();
            Cursor cursor = helper.fetchUserDate(MainActivity.appUser.getUid(), MainActivity.appUser.getProvider());
            if (cursor != null && cursor.moveToFirst()){
                String fecha = cursor.getString(cursor.getColumnIndex("min_fecha"));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                min_date.setTime(formatter.parse(fecha));
            }
        } catch (SQLException |ParseException ignored) {}
        CheckDate();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.before_calory:
                    break;
                case R.id.next_calory:
                    break;
            }
        }
    };

    private void CheckDate(){
        if (saturday_wek ){

        }
    }
}
