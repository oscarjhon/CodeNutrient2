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
                    values.setNutrient_id(id);
                    switch (id){
                        case 19:
                            values.setValue(((diabetes || hipertension) ? 1500 : cursor.getFloat(cursor.getColumnIndex("value"))));
                            break;
                        default:
                            values.setValue(cursor.getFloat(cursor.getColumnIndex("value")));
                    }
                    this.values.add(values);
                } while (cursor.moveToNext());
                response.setValues(values);
                response.setCode(200);
            } else {
                response.code = 1;
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
            value.setNutrient_id(i);
            switch (i){
                case 1:
                    value.setValue((float) (infoAppUser.getPeso() * 0.8));
                    values.add(value);
                    break;
                case 2:
                    value.setValue((float) (infoAppUser.getMax_calorias() * 0.55));
                    values.add(value);
                    break;
                case 4:
                    value.setValue((float) (infoAppUser.getMax_calorias() * 0.27));
                    values.add(value);
                    break;
                case 5:
                    value.setValue((float) (infoAppUser.getMax_calorias() * ((diabetes || hipertension) ? 0.08 : 0.12)));
                    values.add(value);
                    break;
                case 6:
                    value.setValue((float) (infoAppUser.getMax_calorias() * ((diabetes || hipertension) ? 0.05 : 0.08)));
                    values.add(value);
                    break;
                case 7:
                    value.setValue((float) (infoAppUser.getMax_calorias() * 0.09));
                    values.add(value);
                    break;
                case 8:
                    value.setValue((float) (infoAppUser.getMax_calorias() * 0.01));
                    values.add(value);
                    break;
                case 9:
                    value.setValue((float) (infoAppUser.getMax_calorias() * ((diabetes || hipertension) ? 0.05 : 0.1)));
                    values.add(value);
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
