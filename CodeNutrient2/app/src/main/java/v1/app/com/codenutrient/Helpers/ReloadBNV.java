package v1.app.com.codenutrient.Helpers;

import android.content.Context;

import v1.app.com.codenutrient.POJO.BNV_response;
import v1.app.com.codenutrient.POJO.InfoAppUser;

/**
 * Created by Arturo on 14/03/2017.
 * This code was made for the project CodeNutrient.
 */
public class ReloadBNV {
    public BNV_response reload (InfoAppUser infoAppUser, Context context){
        BNV_response response = new BNV_response();
        response.setCode(0);
        DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.openDataBaseRead();
            if (infoAppUser.isSexo()) {
                response = response.passCursorToValue(infoAppUser, helper.fetchNVMale(infoAppUser.getEdad()));
            } else {
                response = response.passCursorToValue(infoAppUser, helper.fetchNVFemale(infoAppUser.getEdad(), infoAppUser.isEmbarazo() ? 1 : 0, infoAppUser.isLactancia() ? 1 : 0));
            }
            if (response.getCode() == 200) {
                BNVRequest request = new BNVRequest();
                response = request.execute(response).get();
            }
        }catch (Exception e4) {
            return response;
        }
        return response;
    }
}
