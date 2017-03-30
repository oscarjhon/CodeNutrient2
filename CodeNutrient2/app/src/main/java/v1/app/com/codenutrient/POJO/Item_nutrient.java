package v1.app.com.codenutrient.POJO;

/**
 * Created by Arturo on 30/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class Item_nutrient {
    private String nutrient_name;
    private String nutrient_measure_name;
    private String nutrient_resume;
    private float nutrient_consumed;
    private float nutrient_recomended;

    public String getNutrient_name() {
        return nutrient_name;
    }

    public void setNutrient_name(String nutrient_name) {
        this.nutrient_name = nutrient_name;
    }

    public String getNutrient_measure_name() {
        return nutrient_measure_name;
    }

    public void setNutrient_measure_name(String nutrient_measure_name) {
        this.nutrient_measure_name = nutrient_measure_name;
    }

    public String getNutrient_resume() {
        return nutrient_resume;
    }

    public void setNutrient_resume(String nutrient_resume) {
        this.nutrient_resume = nutrient_resume;
    }

    public float getNutrient_consumed() {
        return nutrient_consumed;
    }

    public void setNutrient_consumed(float nutrient_consumed) {
        this.nutrient_consumed = nutrient_consumed;
    }

    public float getNutrient_recomended() {
        return nutrient_recomended;
    }

    public void setNutrient_recomended(float nutrient_recomended) {
        this.nutrient_recomended = nutrient_recomended;
    }
}
