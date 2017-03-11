package v1.app.com.codenutrient.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPackage {
    private String method;
    private Map<String, String> params;
    private String uri;

    public RequestPackage() {
        method = "GET";
        params = new HashMap();
    }

    public String getEncodeParams() {
        StringBuilder sb = new StringBuilder();
        for (String key : this.params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUri() {
        return uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParams(String key, String value) {
        this.params.put(key, value);
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
