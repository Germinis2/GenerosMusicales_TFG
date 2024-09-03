package java.TFG.generosmusicales.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TEXTS = "texts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text1";

    private static final String COLUMN_ARTIST1 = "ARTISTA1";
    private static final String COLUMN_ARTIST2 = "ARTISTA2";
    private static final String COLUMN_ARTIST3 = "ARTISTA3";
    private static final String COLUMN_ARTIST4 = "ARTISTA4";
    private static final String COLUMN_ARTIST5 = "ARTISTA5";
    private static final String COLUMN_ARTIST6 = "ARTISTA6";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_TEXTS + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_ARTIST1 + " TEXT, " +
                COLUMN_ARTIST2 + " TEXT, " +
                COLUMN_ARTIST3 + " TEXT, " +
                COLUMN_ARTIST4 + " TEXT, " +
                COLUMN_ARTIST5 + " TEXT, " +
                COLUMN_ARTIST6 + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXTS);
        onCreate(db);
    }

    public void insertTextWithName(String name, String text1, String artist1, String artist2, String artist3,String artist4, String artist5,String artist6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, name);
        values.put(COLUMN_TEXT, text1);
        values.put(COLUMN_ARTIST1, artist1);
        values.put(COLUMN_ARTIST2, artist2);
        values.put(COLUMN_ARTIST3, artist3);
        values.put(COLUMN_ARTIST4, artist4);
        values.put(COLUMN_ARTIST5, artist5);
        values.put(COLUMN_ARTIST6, artist6);
        db.insertWithOnConflict(TABLE_TEXTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @SuppressLint("Range")
    public String getTextByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TEXT};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_TEXTS, columns, selection, selectionArgs, null, null, null);
        String text = "";

        if (cursor != null && cursor.moveToFirst()) {
            text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
            cursor.close();
        }

        db.close();
        return text;
    }


    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Eliminar todos los datos de la tabla "texts"
        db.delete(TABLE_TEXTS, null, null);

        db.close();
    }
    public String[] getArtist(String genderName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ARTIST1, COLUMN_ARTIST2, COLUMN_ARTIST3, COLUMN_ARTIST4, COLUMN_ARTIST5, COLUMN_ARTIST6};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {genderName};
        Cursor cursor = db.query(TABLE_TEXTS, columns, selection, selectionArgs, null, null, null);
        String[] artists = new String[6];
        if (cursor.moveToFirst()) {
            for (int i = 0; i < 6; i++) {
                artists[i] = cursor.getString(i);
            }
        }
        cursor.close();
        db.close();
        return artists;
    }

    public List<String> getAllGenres() {
        List<String> genresList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_ID + " FROM " + TABLE_TEXTS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String artistName = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                genresList.add(artistName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return genresList;
    }
}