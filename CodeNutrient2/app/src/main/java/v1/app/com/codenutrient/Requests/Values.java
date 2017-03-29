package v1.app.com.codenutrient.Requests;

import java.util.ArrayList;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.*;

public class Values {

    public BNV_response ExecuteGET(v1.app.com.codenutrient.POJO.AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.values);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code != 200) {
            BNV_response bnv_response = new BNV_response();
            bnv_response.setCode(response.code);
            return bnv_response;
        }
        return new v1.app.com.codenutrient.Parser.Values().parser(response);
    }

    public BNV_response ExecutePost(BNV_response values, v1.app.com.codenutrient.POJO.AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.values);
        ArrayList aux_values = values.getValues();
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        for (int i = 0; i < aux_values.size(); i++) {
            v1.app.com.codenutrient.POJO.Values value = (v1.app.com.codenutrient.POJO.Values) aux_values.get(i);
            requestPackage.setParams("best[_"+ value.getNutrient_id() + "][value]", value.getValue() + "");
        }
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.Values().parser(response);
        }
        BNV_response bnv_response = new BNV_response();
        bnv_response.setCode(response.code);
        return bnv_response;
    }
}
