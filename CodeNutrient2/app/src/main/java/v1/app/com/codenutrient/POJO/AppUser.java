package v1.app.com.codenutrient.POJO;

import android.net.Uri;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.io.Serializable;

public class AppUser implements Serializable {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private String Token;
    private int code;
    private String email;
    private String name;
    private Uri photo;
    private String provider;
    private String uid;

    AppUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -1707377485:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/AppUser"}));
        }
    }

    public static /* synthetic */ Object access$super(AppUser appUser, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/AppUser"}));
        }
    }

    public int getCode() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.code;
        }
        return ((Number) incrementalChange.access$dispatch("getCode.()I", this)).intValue();
    }

    public String getEmail() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.email;
        }
        return (String) incrementalChange.access$dispatch("getEmail.()Ljava/lang/String;", this);
    }

    public String getName() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.name;
        }
        return (String) incrementalChange.access$dispatch("getName.()Ljava/lang/String;", this);
    }

    public Uri getPhoto() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.photo;
        }
        return (Uri) incrementalChange.access$dispatch("getPhoto.()Landroid/net/Uri;", this);
    }

    public String getProvider() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.provider;
        }
        return (String) incrementalChange.access$dispatch("getProvider.()Ljava/lang/String;", this);
    }

    public String getToken() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.Token;
        }
        return (String) incrementalChange.access$dispatch("getToken.()Ljava/lang/String;", this);
    }

    public String getUid() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.uid;
        }
        return (String) incrementalChange.access$dispatch("getUid.()Ljava/lang/String;", this);
    }

    public void setCode(int code) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCode.(I)V", this, new Integer(code));
            return;
        }
        this.code = code;
    }

    public void setEmail(String email) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setEmail.(Ljava/lang/String;)V", this, email);
            return;
        }
        this.email = email;
    }

    public void setName(String name) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setName.(Ljava/lang/String;)V", this, name);
            return;
        }
        this.name = name;
    }

    public void setPhoto(Uri photo) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setPhoto.(Landroid/net/Uri;)V", this, photo);
            return;
        }
        this.photo = photo;
    }

    public void setProvider(String provider) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setProvider.(Ljava/lang/String;)V", this, provider);
            return;
        }
        this.provider = provider;
    }

    public void setToken(String token) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setToken.(Ljava/lang/String;)V", this, token);
            return;
        }
        this.Token = token;
    }

    public void setUid(String uid) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setUid.(Ljava/lang/String;)V", this, uid);
            return;
        }
        this.uid = uid;
    }

    public AppUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/AppUser;)V", this);
        }
    }
}
