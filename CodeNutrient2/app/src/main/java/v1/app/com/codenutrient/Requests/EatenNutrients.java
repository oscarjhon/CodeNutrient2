package v1.app.com.codenutrient.Requests;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.Constants;

public class EatenNutrients {

    public v1.app.com.codenutrient.POJO.EatenNutrients ExecuteGET(AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.eaten_nutrients);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.EatenNutrients().parser(response);
        }
        v1.app.com.codenutrient.POJO.EatenNutrients eatenNutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
        eatenNutrients.setCode(response.code);
        return eatenNutrients;
    }

    public v1.app.com.codenutrient.POJO.EatenNutrients ExecuteGET(AppUser appUser, Date fecha) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.eaten_nutrients);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("fecha", formatter.format(fecha));
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.EatenNutrients().parser(response);
        }
        v1.app.com.codenutrient.POJO.EatenNutrients eatenNutrients = new v1.app.com.codenutrient.POJO.EatenNutrients();
        eatenNutrients.setCode(response.code);
        return eatenNutrients;
    }
}
