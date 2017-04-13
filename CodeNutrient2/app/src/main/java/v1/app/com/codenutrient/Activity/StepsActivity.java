package v1.app.com.codenutrient.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;

import java.util.Calendar;

import v1.app.com.codenutrient.R;

/**
 * Created by Arturo on 07/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class StepsActivity extends AppCompatActivity {

    private Spinner spinner;
    private LineChart lineChart;
    private int days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = (Spinner) findViewById(R.id.steps_spinner);
        lineChart = (LineChart) findViewById(R.id.charts);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.steps_spinner, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerEvent());
        days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void PrepareRequest(int position){
        spinner.setEnabled(false);
        switch(position){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public class SpinnerEvent implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0)
                PrepareRequest(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
