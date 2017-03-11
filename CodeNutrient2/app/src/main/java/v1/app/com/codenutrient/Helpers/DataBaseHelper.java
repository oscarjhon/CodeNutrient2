package v1.app.com.codenutrient.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import com.google.android.gms.common.Scopes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import v1.app.com.codenutrient.BuildConfig;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    private static String DB_NAME;
    private static String DB_PATH;
    private final Context myContext;
    private SQLiteDatabase myDatabase;

    DataBaseHelper(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -466470621:
                this((Context) objArr[1]);
            case -376888114:
                super((Context) objArr[1], (String) objArr[2], (CursorFactory) objArr[3], ((Number) objArr[4]).intValue(), (DatabaseErrorHandler) objArr[5]);
            case 1289736834:
                super((Context) objArr[1], (String) objArr[2], (CursorFactory) objArr[3], ((Number) objArr[4]).intValue());
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Helpers/DataBaseHelper"}));
        }
    }

    public static /* synthetic */ Object access$super(DataBaseHelper dataBaseHelper, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2128160755:
                return super.toString();
            case -2000015223:
                super.onOpen((SQLiteDatabase) objArr[0]);
                return null;
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1166127280:
                super.notify();
                return null;
            case -1100993913:
                super.onConfigure((SQLiteDatabase) objArr[0]);
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case -483678593:
                super.close();
                return null;
            case -241799050:
                super.onDowngrade((SQLiteDatabase) objArr[0], ((Number) objArr[1]).intValue(), ((Number) objArr[2]).intValue());
                return null;
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 305025623:
                return super.getWritableDatabase();
            case 342041543:
                super.setWriteAheadLoggingEnabled(((Boolean) objArr[0]).booleanValue());
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1452388359:
                return super.getReadableDatabase();
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 2025021518:
                return super.clone();
            case 2084723549:
                return super.getDatabaseName();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Helpers/DataBaseHelper"}));
        }
    }

    public boolean checkDataBase() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Boolean) incrementalChange.access$dispatch("checkDataBase.()Z", this)).booleanValue();
        }
        File dbFile = new File(DB_PATH);
        return dbFile.exists() && !dbFile.isDirectory();
    }

    public boolean checkMeasure() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Boolean) incrementalChange.access$dispatch("checkMeasure.()Z", this)).booleanValue();
        }
        Cursor cursor = this.myDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'measures'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public synchronized void close() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("close.()V", this);
        } else {
            if (this.myDatabase != null) {
                this.myDatabase.close();
            }
            super.close();
        }
    }

    public void copyDataBase() throws IOException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("copyDataBase.()V", this);
            return;
        }
        InputStream myInput = this.myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int length = myInput.read(buffer);
            if (length == -1) {
                myOutput.flush();
                myOutput.close();
                myInput.close();
                return;
            } else if (length > 0) {
                myOutput.write(buffer, 0, length);
            }
        }
    }

    public void createDataBase() throws IOException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("createDataBase.()V", this);
        } else if (!checkDataBase()) {
            Log.e("DATABASE", "no existe");
            getReadableDatabase().close();
            copyDataBase();
        }
    }

    public void executeMeasure() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("executeMeasure.()V", this);
            return;
        }
        this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS measures (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , nombre TEXT, abreviacion TEXT);");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Kilogramos','kg');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Mililitros','ml');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Litros','lt');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Onzas','oz');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Gramos','gr');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Miligramos','mg');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('kilocalorias/calorias','Kcal');");
        this.myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Microgramos','mcg');");
    }

    public Cursor fetchBeneficiosName(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchBeneficiosName.(I)Landroid/database/Cursor;", this, new Integer(nutrient_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT r.beneficios AS beneficios, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.beneficios != 'NONE'", new String[]{BuildConfig.FLAVOR + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchCalorieHistoryByDate(int user_id, String min_date, String max_date) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchCalorieHistoryByDate.(ILjava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", this, new Integer(user_id), min_date, max_date);
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT calories, datetime, id FROM CalorieHistory WHERE datetime >= '?' AND datetime <= '?'", new String[]{min_date + " 00:00:00", max_date + " 23:59:59", BuildConfig.FLAVOR + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchDeficienciaName(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchDeficienciaName.(I)Landroid/database/Cursor;", this, new Integer(nutrient_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT r.deficiencia AS deficiencia, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.deficiencia != 'NONE'", new String[]{BuildConfig.FLAVOR + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchExcesoName(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchExcesoName.(I)Landroid/database/Cursor;", this, new Integer(nutrient_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT r.exceso AS exceso, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.exceso != 'NONE'", new String[]{BuildConfig.FLAVOR + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchMeasure(int measure_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchMeasure.(I)Landroid/database/Cursor;", this, new Integer(measure_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM measures WHERE id = ?", new String[]{BuildConfig.FLAVOR + measure_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchMeasures() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchMeasures.()Landroid/database/Cursor;", this);
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM measures", new String[0]);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNVFemale(int edad, int em, int lac) throws SQLException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchNVFemale.(III)Landroid/database/Cursor;", this, new Integer(edad), new Integer(em), new Integer(lac));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT nutrient_id, value FROM NutrientValues WHERE gender = 0 and em = ? and lac = ? and range_id = (SELECT id FROM Range WHERE min <=? ORDER BY min DESC Limit 1)", new String[]{BuildConfig.FLAVOR + em, BuildConfig.FLAVOR + lac, BuildConfig.FLAVOR + edad});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNVMale(int edad) throws SQLException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchNVMale.(I)Landroid/database/Cursor;", this, new Integer(edad));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT nutrient_id, value FROM NutrientValues WHERE gender = 1 range_id = (SELECT id FROM Range WHERE min <=? ORDER BY min DESC Limit 1)", new String[]{BuildConfig.FLAVOR + edad});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchRecomendacionName(int nutrient_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchRecomendacionName.(I)Landroid/database/Cursor;", this, new Integer(nutrient_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT r.recomendacion AS recomendacion, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.recomendacion != 'NONE'", new String[]{BuildConfig.FLAVOR + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchSteps(int user_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchSteps.(I)Landroid/database/Cursor;", this, new Integer(user_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT SUM(steps) AS steps FROM Steps WHERE user_id = ? GROUP BY fecha ORDER BY fecha DESC limit 30", new String[]{BuildConfig.FLAVOR + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchStepsNotSended(int user_id, String date) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchStepsNotSended.(ILjava/lang/String;)Landroid/database/Cursor;", this, new Integer(user_id), date);
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT SUM(calories) AS Calories FROM Steps WHERE sended = 0 AND fecha = '?' AND user_id = ?", new String[]{date, BuildConfig.FLAVOR + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUser(String uid, String provider) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchUser.(Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", this, uid, provider);
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM Users WHERE uid = ? AND provider = ?", new String[]{uid, provider});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchWeigthHistory(int user_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (Cursor) incrementalChange.access$dispatch("fetchWeigthHistory.(I)Landroid/database/Cursor;", this, new Integer(user_id));
        }
        Cursor cursor = this.myDatabase.rawQuery("SELECT weigth, date, id FROM WeigthHistory WHERE user_id  = ? ORDER BY id DESC limit 30", new String[]{BuildConfig.FLAVOR + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long insertCalorieHistory(int user_id, float calories) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("insertCalorieHistory.(IF)J", this, new Integer(user_id), new Float(calories))).longValue();
        }
        ContentValues values = new ContentValues();
        values.put("user_id", Integer.valueOf(user_id));
        values.put("calories", Float.valueOf(calories));
        return this.myDatabase.insert("CalorieHistory", null, values);
    }

    public long insertSteps(int user_id, int steps, float calories) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("insertSteps.(IIF)J", this, new Integer(user_id), new Integer(steps), new Float(calories))).longValue();
        }
        ContentValues values = new ContentValues();
        values.put("user_id", Integer.valueOf(user_id));
        values.put("steps", Integer.valueOf(steps));
        values.put("calories", Float.valueOf(calories));
        return this.myDatabase.insert("Steps", null, values);
    }

    public long insertUsers(String email, String provider, String token, String uid, String name) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("insertUsers.(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J", this, email, provider, token, uid, name)).longValue();
        }
        ContentValues values = new ContentValues();
        values.put(Scopes.EMAIL, email);
        values.put("provider", provider);
        values.put("token", token);
        values.put("uid", uid);
        values.put("name", name);
        return this.myDatabase.insert("Users", null, values);
    }

    public long insertWeigthHistory(int user_id, float weigth) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("insertWeigthHistory.(IF)J", this, new Integer(user_id), new Float(weigth))).longValue();
        }
        ContentValues values = new ContentValues();
        values.put("user_id", Integer.valueOf(user_id));
        values.put("weigth", Float.valueOf(weigth));
        return this.myDatabase.insert("WeigthHistory", null, values);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("onCreate.(Landroid/database/sqlite/SQLiteDatabase;)V", this, sqLiteDatabase);
            return;
        }
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("onUpgrade.(Landroid/database/sqlite/SQLiteDatabase;II)V", this, sqLiteDatabase, new Integer(i), new Integer(i1));
            return;
        }
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDataBaseRead() throws SQLException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("openDataBaseRead.()V", this);
            return;
        }
        this.myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, 1);
    }

    public void openDataBaseReadWrite() throws SQLException {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("openDataBaseReadWrite.()V", this);
            return;
        }
        this.myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, 0);
    }

    public int updateStepsSended(Date fecha, int user_id) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("updateStepsSended.(Ljava/util/Date;I)I", this, fecha, new Integer(user_id))).intValue();
        }
        ContentValues values = new ContentValues();
        values.put("sended", Integer.valueOf(1));
        Format formatter = new SimpleDateFormat("yyyy-MM-DD");
        return this.myDatabase.update("Steps", values, "user_id = ? AND fecha = ? ", new String[]{BuildConfig.FLAVOR + user_id, formatter.format(fecha)});
    }

    public int updateUserCalories(String uid, String provider, float calories) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("updateUserCalories.(Ljava/lang/String;Ljava/lang/String;F)I", this, uid, provider, new Float(calories))).intValue();
        }
        ContentValues values = new ContentValues();
        values.put("calories", Float.valueOf(calories));
        return this.myDatabase.update("Users", values, "uid = ? AND provider = ?", new String[]{uid, provider});
    }

    public void updateUserRangeFemale(String uid, String provider, int edad, int em, int lac) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("updateUserRangeFemale.(Ljava/lang/String;Ljava/lang/String;III)V", this, uid, provider, new Integer(edad), new Integer(em), new Integer(lac));
            return;
        }
        this.myDatabase.execSQL("UPDATE Users SET range_id = (SELCT id FROM range WHERE min <= ? AND em = ? AND lac = ?ORDER BY min DESC Limit 1) WHERE uid = ? AND provider = ?", new String[]{BuildConfig.FLAVOR + edad, BuildConfig.FLAVOR + em, BuildConfig.FLAVOR + lac, uid, provider});
    }

    public void updateUserRangeMale(String uid, String provider, int edad) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("updateUserRangeMale.(Ljava/lang/String;Ljava/lang/String;I)V", this, uid, provider, new Integer(edad));
            return;
        }
        this.myDatabase.execSQL("UPDATE Users SET range_id = (SELECT id FROM Range WHERE min <= ?ORDER BY min DESC Limit 1) WHERE uid = ? AND provider = ?", new String[]{BuildConfig.FLAVOR + edad, uid, provider});
    }

    public int updateUsers(String token, String uid, String provider, String email, String name) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return ((Number) incrementalChange.access$dispatch("updateUsers.(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", this, token, uid, provider, email, name)).intValue();
        }
        ContentValues values = new ContentValues();
        values.put("token", token);
        values.put(Scopes.EMAIL, email);
        values.put("name", name);
        return this.myDatabase.update("Users", values, "uid = ? AND provider = ?", new String[]{uid, provider});
    }

    static {
        DB_NAME = "CodeNutrients.sqlite";
    }

    public DataBaseHelper(Context context) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            Object[] objArr = new Object[]{objArr, context};
            context = objArr[1];
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;Landroid/content/Context;)Ljava/lang/Object;", objArr), null);
        } else {
            super(context, DB_NAME, null, 2);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Helpers/DataBaseHelper;Landroid/content/Context;)V", this, context);
            return;
        }
        this.myContext = context;
        DB_PATH = this.myContext.getDatabasePath(DB_NAME).getPath();
    }
}
