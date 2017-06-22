package v1.app.com.codenutrient.POJO;


import java.util.Calendar;

/**
 * Created by Arturo on 12/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class StepEntries {
    private Calendar day;
    private float value;

    public StepEntries(){
        day = Calendar.getInstance();
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day.setTime(day.getTime());
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
