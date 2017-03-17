package v1.app.com.codenutrient.Requests;

import java.util.Arrays;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.HTTP.RequestPackage;
import v1.app.com.codenutrient.HTTP.Response;
import v1.app.com.codenutrient.POJO.*;

public class InfoAppUser {

    public v1.app.com.codenutrient.POJO.InfoAppUser ExecuteGET(v1.app.com.codenutrient.POJO.AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.info_app_users);
        requestPackage.setParams("uid", appUser.getUid() + "");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        Response response = new HttpManager().getData(requestPackage);
        if (response.code == 200) {
            return new v1.app.com.codenutrient.Parser.InfoAppUser().parser(response);
        }
        v1.app.com.codenutrient.POJO.InfoAppUser info = new v1.app.com.codenutrient.POJO.InfoAppUser();
        info.setCode(response.code);
        return info;
    }

    public v1.app.com.codenutrient.POJO.InfoAppUser ExecutePost(v1.app.com.codenutrient.POJO.InfoAppUser infoAppUser, v1.app.com.codenutrient.POJO.AppUser appUser) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(Constants.ip_addr + Constants.service_version + Constants.info_app_users);
        requestPackage.setParams("uid", appUser.getUid() +"");
        requestPackage.setParams("provider", appUser.getProvider());
        requestPackage.setParams("token", appUser.getToken());
        requestPackage.setParams("info[fecha_nacimiento]", infoAppUser.getFechaNacimiento().toString());
        requestPackage.setParams("info[peso]", infoAppUser.getPeso() + "");
        requestPackage.setParams("info[estatura]", infoAppUser.getEstatura() + "");
        requestPackage.setParams("info[sexo]", infoAppUser.isSexo() + "");
        requestPackage.setParams("info[max_calorias]", infoAppUser.getMax_calorias() + "");
        requestPackage.setParams("info[min_calorias]", infoAppUser.getMin_calorias() + "");
        requestPackage.setParams("info[embarazo]", infoAppUser.isEmbarazo() + "");
        requestPackage.setParams("info[lactancia]", infoAppUser.isLactancia() + "");
        if (infoAppUser.getDiseases() != null) {
            requestPackage.setParams("diseases", Arrays.toString(infoAppUser.getDiseases()));
        }
        Response response = new HttpManager().getData(requestPackage);
        v1.app.com.codenutrient.Parser.InfoAppUser parser = new v1.app.com.codenutrient.Parser.InfoAppUser();
        switch (response.code) {
            case 200:
                return parser.parser(response);
            case 206:
                return parser.parser(response);
            case 422:
                return parser.parserError(response);
            default:
                v1.app.com.codenutrient.POJO.InfoAppUser info = new v1.app.com.codenutrient.POJO.InfoAppUser();
                info.setCode(response.code);
                return info;
        }
    }
}
