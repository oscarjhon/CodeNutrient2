package v1.app.com.codenutrient.Helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME;
    private static String DB_PATH;
    private final Context myContext;
    private SQLiteDatabase myDatabase;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).getPath();
    }

    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists() && !dbFile.isDirectory();
    }

    public boolean checkMeasure() {
        Cursor cursor = myDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'measures'", null);
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
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    public void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[1024];
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
        if (!checkDataBase()) {
            getReadableDatabase().close();
            copyDataBase();
        }
    }

    public void delete(){
        myContext.deleteDatabase(DB_NAME);
    }

    public void reloadUser(){
        //myDatabase.execSQL("DROP TABLE Users");
        myDatabase.execSQL("CREATE TABLE Users (id INTEGER PRIMARY KEY  NOT NULL, " +
                "email TEXT NOT NULL, " +
                "provider TEXT NOT NULL, " +
                "token TEXT NOT NULL, " +
                "uid TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "range_id INTEGER DEFAULT (null), " +
                "calories FLOAT DEFAULT (2000), " +
                "edad INT DEFAULT (18), " +
                "FOREIGN KEY (range_id) REFERENCES Range(id))");
    }

    public void executeMeasure() {
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS measures (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , nombre TEXT, abreviacion TEXT);");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Kilogramos','kg');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Mililitros','ml');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Litros','lt');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Onzas','oz');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Gramos','gr');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Miligramos','mg');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('kilocalorias/calorias','Kcal');");
        myDatabase.execSQL("INSERT INTO measures(nombre, abreviacion) VALUES('Microgramos','mcg');");
    }

    public Cursor fetchBeneficiosName(int nutrient_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT r.beneficios AS beneficios, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.beneficios != 'NONE'", new String[]{"" + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchCalorieHistoryByDate(int user_id, String date) {
        Cursor cursor = myDatabase.rawQuery("SELECT calories, Date(datetime) as fecha, id FROM CalorieHistory WHERE fecha = ? AND user_id = ?", new String[]{date, "" + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchDeficienciaName(int nutrient_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT r.deficiencia AS deficiencia, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.deficiencia != 'NONE'", new String[]{"" + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchExcesoName(int nutrient_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT r.exceso AS exceso, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.exceso != 'NONE'", new String[]{"" + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchMeasure(int measure_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM measures WHERE id = ?", new String[]{"" + measure_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchMeasures() {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM measures", new String[0]);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNutrients() {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Nutrients", new String[0]);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNVFemale(int edad, int em, int lac) throws SQLException {
        Cursor cursor = myDatabase.rawQuery("SELECT nutrient_id, value FROM NutrientValues WHERE gender = 0 AND em = ? AND lac = ? AND range_id = (SELECT id FROM Range WHERE min <=? ORDER BY min DESC Limit 1)", new String[]{"" + em, "" + lac, "" + edad});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNVMale(int edad) throws SQLException {
        Cursor cursor = myDatabase.rawQuery("SELECT nutrient_id, value FROM NutrientValues " +
                "WHERE gender = 1 AND range_id = (SELECT id FROM Range WHERE min <= ? ORDER BY min DESC Limit 1)", new String[]{"" + edad});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUserDate(String uid, String provider) throws SQLException{
        Cursor cursor = myDatabase.rawQuery("SELECT min_fecha FROM Users WHERE uid = ? AND provider = ?", new String[] {uid, provider});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchRecomendacionName(int nutrient_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT r.recomendacion AS recomendacion, n.name AS name FROM Recomendations r INNER JOIN Nutrients n ON r.nutrient_id = n.id WHERE n.id = ? AND r.recomendacion != 'NONE'", new String[]{"" + nutrient_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchStepsByType(String uid, String provider, int type, String fecha) {
        Log.i("DATABASE", fecha);
        Cursor cursor = myDatabase.rawQuery("SELECT SUM(steps) AS steps FROM Steps WHERE type = ? AND strftime('%Y-%m-%d', fecha) = ? AND user_id = 1  GROUP BY user_id", new String[]{"" + type, fecha});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchCaloriesNotSended(int user_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT SUM(calories) AS calories, strftime('%Y-%m-%d', datetime) AS fecha FROM CalorieHistory WHERE sended = 0 AND user_id = ? ORDER  BY fecha", new String[]{"" + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUserId(String provider, String uid){
        return myDatabase.rawQuery("SELECT id FROM Users WHERE uid = ? AND provider = ?", new String[]{uid, provider});
    }

    public Cursor fetchUserEdad(String uid, String provider){
        return myDatabase.rawQuery("SELECT edad, id FROM Users WHERE uid = ? AND provider = ?", new String[]{uid, provider});
    }


    public Cursor fetchUser(String uid, String provider) {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Users WHERE uid = ? AND provider = ?", new String[]{uid, provider});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchWeigthHistory(int user_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT weigth, date, id FROM WeigthHistory WHERE user_id  = ? ORDER BY id DESC limit 30", new String[]{"" + user_id});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchConectedUser(){
        return  myDatabase.rawQuery("SELECT id, uid, provider, token FROM Users WHERE active_user = 1", null);
    }

    public Cursor fetchMETS(String provider, String uid){
        return myDatabase.rawQuery("SELECT * FROM Mets WHERE user_id = (SELECT id FROM Users WHERE uid = ? AND provider = ?)", new String[]{uid, provider});
    }

    public long insertCalorieHistory(int user_id, float calories) {
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("calories", calories);
        return myDatabase.insert("CalorieHistory", null, values);
    }

    public long insertSteps(int user_id, int steps, int type) {
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("steps", steps);
        values.put("type", type);
        return myDatabase.insert("Steps", null, values);
    }

    public long insertUsers(String email, String provider, String token, String uid, String name) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("provider", provider);
        values.put("token", token);
        values.put("uid", uid);
        values.put("name", name);
        return myDatabase.insert("Users", null, values);
    }

    public long insertWeigthHistory(int user_id, float weigth) {
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("weigth", weigth);
        return myDatabase.insert("WeigthHistory", null, values);
    }

    public long insertMETS(long id){
       ContentValues values = new ContentValues();
        values.put("user_id", id);
        return myDatabase.insert("Mets", null, values);
    }

    public long insertMETS(long id, float caminar, float trotar, float correr){
        ContentValues values = new ContentValues();
        values.put("user_id", id);
        values.put("caminar", caminar);
        values.put("trotar", trotar);
        values.put("correr", correr);
        return myDatabase.insert("Mets", null, values);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDataBaseRead() throws SQLException {
        myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void openDataBaseReadWrite() throws SQLException {
        myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public int updateCaloriesSended(String fecha, int user_id) {
        ContentValues values = new ContentValues();
        values.put("sended", true);
        return myDatabase.update("CalorieHistory", values, "user_id = ? AND Date(datetime) = ? ", new String[]{"" + user_id, fecha});
    }

    public int updateUserCalories(String uid, String provider, float calories) {
        ContentValues values = new ContentValues();
        values.put("calories", calories);
        return myDatabase.update("Users", values, "uid = ? AND provider = ?", new String[]{uid, provider});
    }

    public void updateUserRangeFemale(String uid, String provider, int edad, int em, int lac) {
        myDatabase.execSQL("UPDATE Users SET range_id = (SELCT id FROM range WHERE min <= ? AND em = ? AND lac = ?ORDER BY min DESC Limit 1) WHERE uid = ? AND provider = ?", new String[]{"" + edad, "" + em, "" + lac, uid, provider});
    }

    public void updateUserRangeMale(String uid, String provider, int edad) {
        myDatabase.execSQL("UPDATE Users SET range_id = (SELECT id FROM Range WHERE min <= ?ORDER BY min DESC Limit 1) WHERE uid = ? AND provider = ?", new String[]{"" + edad, uid, provider});
    }

    public int updateUsers(String token, String uid, String provider, String email, String name) {

        ContentValues values = new ContentValues();
        values.put("token", token);
        values.put("email", email);
        values.put("name", name);
        values.put("active_user", true);
        return myDatabase.update("Users", values, "uid = ? AND provider = ?", new String[]{uid, provider});
    }

    public int updateUsersStatus(){
        ContentValues values = new ContentValues();
        values.put("active_user", false);
        return myDatabase.update("Users", values, null, null);
    }

    public int updateUserAge(String uid, String provider, int edad) {
        ContentValues values = new ContentValues();
        values.put("edad", edad);
        return myDatabase.update("Users", values, "uid = ? AND provider = ?", new String[]{uid, provider});
    }

    public int updateMETS(String uid, String provider, float caminar, float trotar, float correr){
        ContentValues values = new ContentValues();
        values.put("caminar", caminar);
        values.put("trotar", trotar);
        values.put("correr", correr);
        return myDatabase.update("METS", values, "user_id = (SELECT id FROM Users WHERE uid = ? AND provider = ?)", new String[]{uid, provider});
    }

    public boolean checkMETS(String provider, String uid){
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM Mets WHERE user_id = (SELECT id FROM users WHERE uid = ? AND provider = ?)", new String[]{uid, provider});
        if (cursor != null){
            if (cursor.moveToFirst()){
                return true;
            }
        }
        return false;
    }

    static {
        DB_NAME = "CodeNutrients.sqlite";
    }


}