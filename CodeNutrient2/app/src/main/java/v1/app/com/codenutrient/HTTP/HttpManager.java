package v1.app.com.codenutrient.HTTP;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import v1.app.com.codenutrient.Activities.MainActivity;
import v1.app.com.codenutrient.Helpers.ReloadUser;
import v1.app.com.codenutrient.Helpers.UserRequests;
import v1.app.com.codenutrient.POJO.AppUser;

public class HttpManager {

    public Response getData(RequestPackage requestPackage) {
        String uri = requestPackage.getUri();
        Response response = new Response();
        response.code = 0;
        if (requestPackage.getMethod().equals("GET")) {
            uri = uri + "?" + requestPackage.getEncodeParams();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.setRequestMethod(requestPackage.getMethod());
            connection.setConnectTimeout(60000);
            if (requestPackage.getMethod().equals("POST")) {
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestPackage.getEncodeParams());
                writer.flush();
            }
            response.code = connection.getResponseCode();
            StringBuilder stringBuilder;
            BufferedReader reader;
            String line;
            if (response.code < 200 || response.code >= 300) {
                stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line + "\n");
                }
                response.response = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line + "\n");
                }
                response.response = stringBuilder.toString();
            }
            return response;
        } catch (Exception e4) {
            response.code = 0;
            return response;
        }
    }

    public boolean isOnLine(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public boolean reload(int reload, Context context) {
        if (reload != 1) {
            return false;
        }
        UserRequests userRequests = new UserRequests();
        AppUser appUser = MainActivity.appUser;
        try {
            ReloadUser reloadUser = new ReloadUser();
            appUser = userRequests.execute(MainActivity.appUser).get();
            reloadUser.postExecute(appUser, context);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return appUser.getToken().equals(MainActivity.appUser.getToken());
    }
}
