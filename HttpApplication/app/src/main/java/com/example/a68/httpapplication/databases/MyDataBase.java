package com.example.a68.httpapplication.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.a68.httpapplication.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MyDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "ExampleDB";

    public static final String TABLE_NAME = "Place";

    //columns
    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_ID_MODEL = "id";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LON = "lon";
    public static final String COLUMN_CIUDAD = "ciudad";
    private static final String COLUMN_CELULAR = "celular";
    private static final String COLUMN_PAIS = "pais";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_HORARIOS = "horarios";
    public static final String COLUMN_NAME = "nombre";

    private static final String[] cols = {COLUMN_ID, COLUMN_ID_MODEL, COLUMN_LAT,
            COLUMN_LON, COLUMN_CIUDAD, COLUMN_CELULAR, COLUMN_PAIS, COLUMN_DIRECCION,
            COLUMN_HORARIOS, COLUMN_NAME
    };
    private static final String TAG = MyDataBase.class.getSimpleName();

    private final SQLiteDatabase db;

    public MyDataBase(Context context) {
        super(context, DB_NAME, null, 1);
        db = getWritableDatabase();
    }

    private void add(Place place) {
        db.insert(TABLE_NAME, null, generateValues(place));
    }

    public void bulkInsert(ContentValues[] values){
        db.beginTransaction();
        try {
            for(ContentValues value : values){
                db.insert(TABLE_NAME, null, value);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @SuppressWarnings("unused")
    public int update(int id, Place place) {
        return db.update(TABLE_NAME, generateValues(place),
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    @SuppressWarnings("unused")
    public int delete(int id) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ? ",
                new String[]{String.valueOf(id)});
    }

    public void addAll(ArrayList<Place> places) {
        for (Place place : places) {
            add(place);
        }
    }

    private ContentValues generateValues(Place place) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_MODEL, place.id);
        values.put(COLUMN_LAT, place.lat);
        values.put(COLUMN_LON, place.lon);
        values.put(COLUMN_CIUDAD, place.ciudad);
        values.put(COLUMN_CELULAR, place.celular);
        values.put(COLUMN_PAIS, place.pais);
        values.put(COLUMN_DIRECCION, place.direccion);
        values.put(COLUMN_HORARIOS, place.horarios);
        values.put(COLUMN_NAME, place.nombre);
        return values;
    }


    public void addJArray(JSONArray jsonArray) throws JSONException {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ContentValues cv = new ContentValues();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Iterator<String> keysIterator = jsonObj.keys();
            while (keysIterator.hasNext())
            {
                String key = keysIterator.next();
                String value = jsonObj.getString(key);
                if (value == null) value = "";
                /*if (i == 0)
                    Logger.d("key: " + key + ", value: " + value);*/
                if (key.equals("point") || key.equals("telefono")
                        || key.equals("is_active")) continue;
                cv.put(key, value);
            }
            contentValues.add(cv);

            //db.insert(TABLE_NAME, null, cv);
        }
        bulkInsert(contentValues.toArray(new ContentValues[contentValues.size()]));
    }
    public Cursor getAll() {
        return db.query(TABLE_NAME, cols, null, null, null, null, COLUMN_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " ("
                + COLUMN_ID + " integer PRIMARY KEY not null, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ID_MODEL + " integer, "
                + COLUMN_LAT + " REAL, "
                + COLUMN_LON + " REAL, "
                + COLUMN_CIUDAD + " TEXT, "
                + COLUMN_CELULAR + " integer, "
                + COLUMN_PAIS + " TEXT, "
                + COLUMN_DIRECCION + " TEXT, "
                + COLUMN_HORARIOS + " TEXT "
                + ")";
        db.execSQL(query);
        Log.d(TAG, "onCreate DB");

        String query2 = "CREATE TABLE test "
                + " ("
                + COLUMN_ID + " integer PRIMARY KEY not null, "
                + COLUMN_NAME + " TEXT "
                + ")";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
