package v1.app.com.codenutrient.POJO;

import java.util.Calendar;

/**
 * Created by Arturo on 06/06/2017.
 * This code was made for the project CodeNutrient.
 */
public class CaloryEntries {
    private Calendar day;
    private float value;

    public CaloryEntries(){
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
