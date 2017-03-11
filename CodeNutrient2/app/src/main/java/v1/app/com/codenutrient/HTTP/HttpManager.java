package v1.app.com.codenutrient.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import v1.app.com.codenutrient.Activity.MainActivity;
import v1.app.com.codenutrient.Helpers.ReloadUser;
import v1.app.com.codenutrient.Helpers.UserRequests;
import v1.app.com.codenutrient.POJO.AppUser;

public class HttpManager {
    public static volatile transient /* synthetic */ IncrementalChange $change;

    HttpManager(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 272025008:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/HTTP/HttpManager"}));
        }
    }

    public static /* synthetic */ Object access$super(HttpManager httpManager, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2128160755:
                return super.toString();
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1166127280:
                super.notify();
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 2025021518:
                return super.clone();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/HTTP/HttpManager"}));
        }
    }

    public Response getData(RequestPackage requestPackage) {
        Exception e;
        Throwable th;
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Response) incrementalChange.access$dispatch("getData.(Lv1/app/com/codenutrient/HTTP/RequestPackage;)Lv1/app/com/codenutrient/HTTP/Response;", this, requestPackage);
        }
        BufferedReader bufferedReader = null;
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
            if (response.code < Callback.DEFAULT_DRAG_ANIMATION_DURATION || response.code >= 300) {
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
                bufferedReader = reader;
            } else {
                stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while (true) {
                    try {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuilder.append(line + "\n");
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader = reader;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = reader;
                    }
                }
                response.response = stringBuilder.toString();
                bufferedReader = reader;
            }
            if (bufferedReader == null) {
                return response;
            }
            try {
                bufferedReader.close();
                return response;
            } catch (IOException e3) {
                e3.printStackTrace();
                return response;
            }
        } catch (Exception e4) {
            e = e4;
            try {
                e.printStackTrace();
                if (bufferedReader == null) {
                    return response;
                }
                try {
                    bufferedReader.close();
                    return response;
                } catch (IOException e32) {
                    e32.printStackTrace();
                    return response;
                }
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                        return response;
                    }
                }
                throw th;
            }
        }
    }

    public boolean isOnLine(Context context) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Boolean) incrementalChange.access$dispatch("isOnLine.(Landroid/content/Context;)Z", this, context)).booleanValue();
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public HttpManager() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/HTTP/HttpManager;)V", this);
        }
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
