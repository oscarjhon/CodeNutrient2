package v1.app.com.codenutrient.POJO;

import java.util.ArrayList;

public class BNV_response {
    private int code;
    private ArrayList<Values> values;

    public BNV_response() {
        values = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Values> getValues() {
        return values;
    }

    public void setValues(ArrayList<Values> values) {
        this.values = values;
    }
}
