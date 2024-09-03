package java.TFG.generosmusicales.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.TFG.generosmusicales.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ArtistDatabaseHelper extends SQLiteOpenHelper {// a lo mejor tengo que quitar el text1 si no lo uso para nada :)

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "artists_database";

    // Table name
    private static final String TABLE_ARTISTS = "artists";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TEXT_2 = "text2";
    private static final String COLUMN_ALBUM_1 = "album1";
    private static final String COLUMN_ALBUM_2 = "album2";
    private static final String COLUMN_ALBUM_3 = "album3";
    private static final String COLUMN_ALBUM_4 = "album4";

    private static final String DESCRIPTION_ALBUM_1 = "description1";
    private static final String DESCRIPTION_ALBUM_2 = "description2";
    private static final String DESCRIPTION_ALBUM_3 = "description3";
    private static final String DESCRIPTION_ALBUM_4 = "description4";


    private static final String CREATE_TABLE_ARTISTS = "CREATE TABLE " + TABLE_ARTISTS + " ("
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_TEXT_2 + " TEXT, "
            + COLUMN_ALBUM_1 + " TEXT, "
            + COLUMN_ALBUM_2 + " TEXT, "
            + COLUMN_ALBUM_3 + " TEXT, "
            + COLUMN_ALBUM_4 + " TEXT, "
            + DESCRIPTION_ALBUM_1 + " TEXT, "
            + DESCRIPTION_ALBUM_2 + " TEXT, "
            + DESCRIPTION_ALBUM_3 + " TEXT, "
            + DESCRIPTION_ALBUM_4 + " TEXT)";



    public ArtistDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ARTISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS);
        onCreate(db);
    }
    public void insertTextWithName(String name,  String text2, String album1, String album2, String album3,String album4,
                                   String description1, String description2, String description3,String description4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, name);
        values.put(COLUMN_TEXT_2, text2);
        values.put(COLUMN_ALBUM_1, album1);
        values.put(COLUMN_ALBUM_2, album2);
        values.put(COLUMN_ALBUM_3, album3);
        values.put(COLUMN_ALBUM_4, album4);
        values.put(DESCRIPTION_ALBUM_1, description1);
        values.put(DESCRIPTION_ALBUM_2, description2);
        values.put(DESCRIPTION_ALBUM_3, description3);
        values.put(DESCRIPTION_ALBUM_4, description4);

        db.insertWithOnConflict(TABLE_ARTISTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Eliminar todos los datos de la tabla "texts"
        db.delete(TABLE_ARTISTS, null, null);
        // Si necesitas eliminar datos de otras tablas, repite el proceso para cada una de ellas

        db.close();
    }


    @SuppressLint("Range")
    public String getText2ByName(String artistName) {
        String name = TextUtils.normalize(artistName);
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TEXT_2};
        String selection = "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ID + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u'))" + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_ARTISTS, columns, selection, selectionArgs, null, null, null);
        String text = "";
        if (cursor.moveToFirst()) {
            text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT_2));
        }
        cursor.close();
        db.close();
        return text;
    }

    @SuppressLint("Range")
    public String [] getAlbumsByName(String artistName) {
        String name = TextUtils.normalize(artistName);

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ALBUM_1, COLUMN_ALBUM_2, COLUMN_ALBUM_3, COLUMN_ALBUM_4};
        //String selection = COLUMN_ID + " = ?";
        String selection = "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ID + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u'))" + " = ?";

        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_ARTISTS, columns, selection, selectionArgs, null, null, null);
        String [] text = {"", "", "", ""};
        if (cursor.moveToFirst()) {
            for(int i = 1; i <= text.length;i++) {
                String columnName = "album" + i;
                text[i-1] = cursor.getString(cursor.getColumnIndex(columnName));
            }
        }
        cursor.close();
        db.close();
        return text;
    }

    @SuppressLint("Range")
    public String [] getDescriptionsByName(String artistName) {
        String name = TextUtils.normalize(artistName);

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {DESCRIPTION_ALBUM_1, DESCRIPTION_ALBUM_2, DESCRIPTION_ALBUM_3, DESCRIPTION_ALBUM_4};
        String selection = "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ID + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u'))" + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_ARTISTS, columns, selection, selectionArgs, null, null, null);
        String [] text = {"", "", "", ""};
        if (cursor.moveToFirst()) {
            for(int i = 1; i <= text.length;i++) {
                String columnName = "description" + i;
                text[i-1] = cursor.getString(cursor.getColumnIndex(columnName));
            }
        }
        cursor.close();
        db.close();
        return text;
    }

    @SuppressLint("Range")
    public String getNameWIthoutNormalize(String artistName) {
            String name = TextUtils.normalize(artistName);
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_ID};
            String selection = "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ID + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u'))" + " = ?";
            String[] selectionArgs = {name};
            Cursor cursor = db.query(TABLE_ARTISTS, columns, selection, selectionArgs, null, null, null);
            String text = "";
            if (cursor.moveToFirst()) {
                text = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            }
            cursor.close();
            db.close();
            return text;
        }

    public List<String> getAllArtists() {
        List<String> artistList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_ID + " FROM " + TABLE_ARTISTS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String artistName = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                artistList.add(artistName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return artistList;
    }



    @SuppressLint("Range")
    public String getArtistByAlbum(String albumName) {
        String name = TextUtils.normalize(albumName);

        SQLiteDatabase db = this.getReadableDatabase();
        String artistName = null;

        // Consulta para buscar el artista por el álbum
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_ARTISTS + " WHERE "
                + "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ALBUM_1 + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u')) = ? OR "
                + "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ALBUM_2 + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u')) = ? OR "
                + "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ALBUM_3 + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u')) = ? OR "
                + "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" + COLUMN_ALBUM_4 + ", 'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ü', 'u')) = ?";

        Cursor cursor = db.rawQuery(query, new String[]{name, name, name, name});

        if (cursor.moveToFirst()) {
            artistName = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }

        cursor.close();
        db.close();
        return artistName;
    }

}