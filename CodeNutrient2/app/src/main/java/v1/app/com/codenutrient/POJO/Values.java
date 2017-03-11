package v1.app.com.codenutrient.POJO;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;

public class Values {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private int nutrient_id;
    private float value;

    Values(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case -227676151:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/Values"}));
        }
    }

    public static /* synthetic */ Object access$super(Values values, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/Values"}));
        }
    }

    public int getNutrient_id() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.nutrient_id;
        }
        return ((Number) incrementalChange.access$dispatch("getNutrient_id.()I", this)).intValue();
    }

    public float getValue() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.value;
        }
        return ((Number) incrementalChange.access$dispatch("getValue.()F", this)).floatValue();
    }

    public void setNutrient_id(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setNutrient_id.(I)V", this, new Integer(nutrient_id));
            return;
        }
        this.nutrient_id = nutrient_id;
    }

    public void setValue(float value) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setValue.(F)V", this, new Float(value));
            return;
        }
        this.value = value;
    }

    public Values() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/Values;)V", this);
        }
    }
}
