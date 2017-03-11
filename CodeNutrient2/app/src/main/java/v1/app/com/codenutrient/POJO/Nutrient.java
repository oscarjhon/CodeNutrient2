package v1.app.com.codenutrient.POJO;

import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;

public class Nutrient {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private float cantidad;
    private int nutrient_id;

    Nutrient(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -2066395058:
                this();
            case -1968665286:
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/Nutrient"}));
        }
    }

    public static /* synthetic */ Object access$super(Nutrient nutrient, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/Nutrient"}));
        }
    }

    public float getCantidad() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.cantidad;
        }
        return ((Number) incrementalChange.access$dispatch("getCantidad.()F", this)).floatValue();
    }

    public int getNutrient_id() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.nutrient_id;
        }
        return ((Number) incrementalChange.access$dispatch("getNutrient_id.()I", this)).intValue();
    }

    public void setCantidad(float cantidad) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCantidad.(F)V", this, new Float(cantidad));
            return;
        }
        this.cantidad = cantidad;
    }

    public void setNutrient_id(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setNutrient_id.(I)V", this, new Integer(nutrient_id));
            return;
        }
        this.nutrient_id = nutrient_id;
    }

    public Nutrient() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/Nutrient;)V", this);
        }
    }
}
