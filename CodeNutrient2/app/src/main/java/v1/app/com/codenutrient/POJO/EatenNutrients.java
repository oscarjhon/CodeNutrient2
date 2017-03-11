package v1.app.com.codenutrient.POJO;

import java.util.ArrayList;

public class EatenNutrients {
    private int code;
    private ArrayList<Nutrient> nutrients = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }
}
