package v1.app.com.codenutrient.POJO;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.ArrayList;

public class BNV_response {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private int code;
    private ArrayList<Values> values;

    BNV_response(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 518663197:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/BNV_response"}));
        }
    }

    public static /* synthetic */ Object access$super(BNV_response bNV_response, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/BNV_response"}));
        }
    }

    public int getCode() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.code;
        }
        return ((Number) incrementalChange.access$dispatch("getCode.()I", this)).intValue();
    }

    public ArrayList<Values> getValues() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.values;
        }
        return (ArrayList) incrementalChange.access$dispatch("getValues.()Ljava/util/ArrayList;", this);
    }

    public void setCode(int code) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCode.(I)V", this, new Integer(code));
            return;
        }
        this.code = code;
    }

    public void setValues(ArrayList<Values> values) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setValues.(Ljava/util/ArrayList;)V", this, values);
            return;
        }
        this.values = values;
    }

    public BNV_response() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/BNV_response;)V", this);
            return;
        }
        this.values = null;
    }
}
