package v1.app.com.codenutrient.POJO;

import android.content.ContentValues;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import java.util.Date;

public class InfoAppUser {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private ContentValues Errors;
    private Date FechaNacimiento;
    private int code;
    private int[] diseases;
    private boolean embarazo;
    private float estatura;
    private boolean lactancia;
    private float max_calorias;
    private float min_calorias;
    private float peso;
    private boolean sexo;

    InfoAppUser(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -1968665286:
            case 1622955813:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/POJO/InfoAppUser"}));
        }
    }

    public static /* synthetic */ Object access$super(InfoAppUser infoAppUser, String str, Object... objArr) {
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
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/POJO/InfoAppUser"}));
        }
    }

    public int getCode() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.code;
        }
        return ((Number) incrementalChange.access$dispatch("getCode.()I", this)).intValue();
    }

    public int[] getDiseases() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.diseases;
        }
        return (int[]) incrementalChange.access$dispatch("getDiseases.()[I", this);
    }

    public ContentValues getErrors() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.Errors;
        }
        return (ContentValues) incrementalChange.access$dispatch("getErrors.()Landroid/content/ContentValues;", this);
    }

    public float getEstatura() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.estatura;
        }
        return ((Number) incrementalChange.access$dispatch("getEstatura.()F", this)).floatValue();
    }

    public Date getFechaNacimiento() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.FechaNacimiento;
        }
        return (Date) incrementalChange.access$dispatch("getFechaNacimiento.()Ljava/util/Date;", this);
    }

    public float getMax_calorias() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.max_calorias;
        }
        return ((Number) incrementalChange.access$dispatch("getMax_calorias.()F", this)).floatValue();
    }

    public float getMin_calorias() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.min_calorias;
        }
        return ((Number) incrementalChange.access$dispatch("getMin_calorias.()F", this)).floatValue();
    }

    public float getPeso() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return this.peso;
        }
        return ((Number) incrementalChange.access$dispatch("getPeso.()F", this)).floatValue();
    }

    public int isEmbarazo() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return !this.embarazo ? 0 : 1;
        } else {
            return ((Number) incrementalChange.access$dispatch("isEmbarazo.()I", this)).intValue();
        }
    }

    public int isLactancia() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return !this.lactancia ? 0 : 1;
        } else {
            return ((Number) incrementalChange.access$dispatch("isLactancia.()I", this)).intValue();
        }
    }

    public int isSexo() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange == null) {
            return !this.sexo ? 0 : 1;
        } else {
            return ((Number) incrementalChange.access$dispatch("isSexo.()I", this)).intValue();
        }
    }

    public void setCode(int code) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setCode.(I)V", this, new Integer(code));
            return;
        }
        this.code = code;
    }

    public void setDiseases(int[] diseases) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setDiseases.([I)V", this, diseases);
            return;
        }
        this.diseases = diseases;
    }

    public void setEmbarazo(boolean embarazo) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setEmbarazo.(Z)V", this, new Boolean(embarazo));
            return;
        }
        this.embarazo = embarazo;
    }

    public void setErrors(ContentValues errors) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setErrors.(Landroid/content/ContentValues;)V", this, errors);
            return;
        }
        this.Errors = errors;
    }

    public void setEstatura(float estatura) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setEstatura.(F)V", this, new Float(estatura));
            return;
        }
        this.estatura = estatura;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setFechaNacimiento.(Ljava/util/Date;)V", this, fechaNacimiento);
            return;
        }
        this.FechaNacimiento = fechaNacimiento;
    }

    public void setLactancia(boolean lactancia) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setLactancia.(Z)V", this, new Boolean(lactancia));
            return;
        }
        this.lactancia = lactancia;
    }

    public void setMax_calorias(float max_calorias) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setMax_calorias.(F)V", this, new Float(max_calorias));
            return;
        }
        this.max_calorias = max_calorias;
    }

    public void setMin_calorias(float min_calorias) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setMin_calorias.(F)V", this, new Float(min_calorias));
            return;
        }
        this.min_calorias = min_calorias;
    }

    public void setPeso(float peso) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setPeso.(F)V", this, new Float(peso));
            return;
        }
        this.peso = peso;
    }

    public void setSexo(boolean sexo) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setSexo.(Z)V", this, new Boolean(sexo));
            return;
        }
        this.sexo = sexo;
    }

    public InfoAppUser() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/POJO/InfoAppUser;)V", this);
            return;
        }
        this.diseases = null;
        this.Errors = null;
    }
}
