package v1.app.com.codenutrient.Requests;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import v1.app.com.codenutrient.BuildConfig;
import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.POJO.Constants;

public class Calories {

    public v1.app.com.codenutrient.POJO.Calories ExecuteGET(AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.calories);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.Calories().parser(response);
        }
        v1.app.com.codenutrient.POJO.Calories calories = new v1.app.com.codenutrient.POJO.Calories();
        calories.setCode(response.code);
        return calories;
    }

    public v1.app.com.codenutrient.POJO.Calories ExecuteGET(AppUser appUser, Date fecha) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.calories);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("fecha", formatter.format(fecha));
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.Calories().parser(response);
        }
        v1.app.com.codenutrient.POJO.Calories calories = new v1.app.com.codenutrient.POJO.Calories();
        calories.setCode(response.code);
        return calories;
    }

    public int ExecutePOST(AppUser appUser, float calories, Date fecha) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.calories);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("calories[gasto]", calories + "");
        requestPackage.setParams("calories[fecha]",fecha.toString());
        return new HttpManager().getData(requestPackage).code;
    }
}
