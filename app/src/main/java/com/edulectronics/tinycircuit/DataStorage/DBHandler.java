package com.edulectronics.tinycircuit.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.edulectronics.tinycircuit.Circuit.CircuitModel;
import com.edulectronics.tinycircuit.Models.Components.Component;

/**
 * Created by bernd on 30/11/2016.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "circuits.db";

    public static final String CIRCUIT_TABLE = "circuit";
    public static final String CIRCUIT_ID = "id";
    public static final String CIRCUIT_NAME = "name";

    public static final String COMPONENT_TABLE = "component";
    public static final String COMPONENT_ID = "id";
    public static final String COMPONENT_TYPE = "type";

    public static final String CIRCUIT_COMPONENTS_TABLE = "circuit_components";
    public static final String COMPONENT_X_POSITION = "x_pos";
    public static final String COMPONENT_Y_POSITION = "y_pos";

    public static final String COMPONENT_SETTINGS_TABLE = "component_settings";
    public static final String COMPONENT_SETTINGS = "settings";

    public static final String INPUT_TABLE = "input";
    public static final String INPUT_ID = "id";
    public static final String INPUT_LOCATION = "location";

    public static final String OUTPUT_TABLE = "output";
    public static final String OUTPUT_ID = "id";
    public static final String OUTPUT_LOCATION = "location";

    public static final String INPUT_OUTPUT_TABLE = "input_output";

    public static final String FOREIGN_KEY_COMPONENT = "component_id";
    public static final String FOREIGN_KEY_CIRCUIT = "circuit_id";
    public static final String FOREIGN_KEY_INPUT = "input_id";
    public static final String FOREIGN_KEY_OUTPUT = "output_id";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCircuitQuery = "CREATE TABLE " + CIRCUIT_TABLE + "(" +
                CIRCUIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIRCUIT_NAME + " TEXT " +
                ");";
        String createComponentQuery = "CREATE TABLE " + COMPONENT_TABLE + "(" +
                COMPONENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COMPONENT_TYPE + " TEXT " +
                ")";
        String createCircuitComponentsQuery = "CREATE TABLE " + CIRCUIT_COMPONENTS_TABLE + "(" +
                COMPONENT_X_POSITION + " INTEGER, " +
                COMPONENT_Y_POSITION + " INTEGER, " +
                "FOREIGN KEY(" + FOREIGN_KEY_CIRCUIT + ") REFERENCES " + CIRCUIT_TABLE + "(" + CIRCUIT_ID + "), " +
                "FOREIGN KEY(" + FOREIGN_KEY_COMPONENT + ") REFERENCES " + COMPONENT_TABLE + "(" + COMPONENT_ID + ") " +
                ")";
        String createComponentSettings = "CREATE TABLE " + COMPONENT_SETTINGS_TABLE + "(" +
                COMPONENT_SETTINGS + " TEXT, " +
                "FOREIGN KEY(" + FOREIGN_KEY_COMPONENT + ") REFERENCES " + COMPONENT_TABLE + "(" + COMPONENT_ID + ") " +
                ");";
        String createInput = "CREATE TABLE " + INPUT_TABLE + "(" +
                INPUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INPUT_LOCATION + " INTEGER " +
                ");";
        String createOutput = "CREATE TABLE " + OUTPUT_TABLE + "(" +
                OUTPUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OUTPUT_LOCATION + " INTEGER " +
                ");";
        String createInputOutput = "CREATE TABLE " + INPUT_OUTPUT_TABLE + "(" +
                "FOREIGN KEY(" + FOREIGN_KEY_INPUT + ") REFERENCES " + INPUT_TABLE + "(" + INPUT_ID + "), " +
                "FOREIGN KEY(" + FOREIGN_KEY_OUTPUT + ") REFERENCES " + OUTPUT_TABLE + "(" + OUTPUT_ID + ")" +
                ")";
        db.execSQL(createCircuitQuery);
        db.execSQL(createComponentQuery);
        db.execSQL(createCircuitComponentsQuery);
        db.execSQL(createComponentSettings);
        db.execSQL(createInput);
        db.execSQL(createOutput);
        db.execSQL(createInputOutput);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + COMPONENT_TABLE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + CIRCUIT_TABLE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + CIRCUIT_COMPONENTS_TABLE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + COMPONENT_SETTINGS);
        db.execSQL("DROP_TABLE_IF_EXISTS " + INPUT_TABLE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + OUTPUT_TABLE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + INPUT_OUTPUT_TABLE);
        onCreate(db);
    }

    public void saveCircuit(CircuitModel circuit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CIRCUIT_NAME, circuit.name);
        long id = db.insert(CIRCUIT_TABLE, null, values);
        for (int x = 0; x < circuit.width; x++) {
            for (int y = 0; y < circuit.height; y++) {
                if (circuit.components[x][y] != null) {
                    saveComponent(db, circuit.components[x][y], id, x, y);
                }
            }
        }
        db.close();
    }

    private void saveComponent(SQLiteDatabase writableDB, Component component, long circuitId, int x, int y) {
        ContentValues values = new ContentValues();
        values.put(FOREIGN_KEY_CIRCUIT, circuitId);
        values.put(COMPONENT_ID, getComponentId(component));
        values.put(COMPONENT_X_POSITION, x);
        values.put(COMPONENT_Y_POSITION, y);
        writableDB.insert(CIRCUIT_COMPONENTS_TABLE, null, values);
    }

    private int getComponentId(Component component) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(COMPONENT_TABLE, new String[] {COMPONENT_ID}, "type = ?", new String[] {component.getClass().getSimpleName()}, null, null, null);
        return cursor.getColumnIndex(COMPONENT_ID);
    }
}
