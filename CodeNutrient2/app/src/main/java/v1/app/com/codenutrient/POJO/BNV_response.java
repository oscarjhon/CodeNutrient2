package v1.app.com.codenutrient.POJO;

import android.database.Cursor;

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

    public BNV_response passCursorToValue(InfoAppUser infoAppUser, Cursor cursor){
        BNV_response response = new BNV_response();
        boolean diabetes = false, hipertension = false;
        if (infoAppUser.getDiseases() != null){
            for (int i = 0; i < infoAppUser.getDiseases().length; i ++){
                switch (infoAppUser.getDiseases()[i]){
                    case 1:
                        diabetes = true;
                        break;
                    case 2:
                        hipertension = true;
                        break;
                }
            }
        }
        values = getFirsts(infoAppUser, diabetes, hipertension);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Values values = new Values();
                    int id = cursor.getInt(cursor.getColumnIndex("nutrient_id"));
                    int disminucion = 0;
                    switch (id){
                        case 9:

                            break;
                        case 18:
                            break;
                    }
                    values.setValue(cursor.getFloat(cursor.getColumnIndex("value")) - disminucion);
                } while (cursor.moveToNext());
            } else {
                response.code = 0;
            }
        }else{
            response.code = 0;
        }
        return response;
    }

    public ArrayList<Values> getFirsts(InfoAppUser infoAppUser, boolean diabetes, boolean hipertension){
        ArrayList<Values> values = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Values value = new Values();
            switch (i){
                case 1:
                    value.setNutrient_id(i);
                    value.setValue((float) (infoAppUser.getPeso() * .8));
                    break;
                case 2:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }
        }
        return values;
    }

    public ArrayList<Values> getValues() {
        return values;
    }

    public void setValues(ArrayList<Values> values) {
        this.values = values;
    }
}
