package v1.app.com.codenutrient.POJO;

/**
 * Created by Arturo on 26/04/2017.
 * This code was made for the project CodeNutrient.
 */
public class StepCounters {
    public  static int walking, running, jogging;
    public  static long w_time, r_time, j_time;

    public StepCounters() {
        walking = 0;
        running = 0;
        jogging = 0;
        w_time = 0;
        r_time = 0;
        j_time = 0;
    }
}
