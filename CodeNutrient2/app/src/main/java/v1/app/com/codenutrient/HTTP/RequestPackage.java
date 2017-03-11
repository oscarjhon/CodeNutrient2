package v1.app.com.codenutrient.HTTP;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPackage {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private String method;
    private Map<String, String> params;
    private String uri;

    RequestPackage(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 1650856858:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/HTTP/RequestPackage"}));
        }
    }

    public static /* synthetic */ Object access$super(RequestPackage requestPackage, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/HTTP/RequestPackage"}));
        }
    }

    public String getEncodeParams() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (String) incrementalChange.access$dispatch("getEncodeParams.()Ljava/lang/String;", this);
        }
        StringBuilder sb = new StringBuilder();
        for (String key : this.params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode((String) this.params.get(key), "UTF-8");
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
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.method;
        }
        return (String) incrementalChange.access$dispatch("getMethod.()Ljava/lang/String;", this);
    }

    public Map<String, String> getParams() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.params;
        }
        return (Map) incrementalChange.access$dispatch("getParams.()Ljava/util/Map;", this);
    }

    public String getUri() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.uri;
        }
        return (String) incrementalChange.access$dispatch("getUri.()Ljava/lang/String;", this);
    }

    public void setMethod(String method) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setMethod.(Ljava/lang/String;)V", this, method);
            return;
        }
        this.method = method;
    }

    public void setParams(String key, String value) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setParams.(Ljava/lang/String;Ljava/lang/String;)V", this, key, value);
            return;
        }
        this.params.put(key, value);
    }

    public void setParams(Map<String, String> params) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setParams.(Ljava/util/Map;)V", this, params);
            return;
        }
        this.params = params;
    }

    public void setUri(String uri) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setUri.(Ljava/lang/String;)V", this, uri);
            return;
        }
        this.uri = uri;
    }

    public RequestPackage() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/HTTP/RequestPackage;)V", this);
            return;
        }
        this.method = "GET";
        this.params = new HashMap();
    }
}
