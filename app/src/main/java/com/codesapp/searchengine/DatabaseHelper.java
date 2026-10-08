package com.codesapp.searchengine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CodesApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_FAVORITES = "favorites";

    // Column names for items table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY = "category";

    // Column names for history table
    private static final String COLUMN_HISTORY_ID = "id";
    private static final String COLUMN_QUERY = "query";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // Column names for favorites table
    private static final String COLUMN_FAV_ID = "id";
    private static final String COLUMN_FAV_QUERY = "query";
    private static final String COLUMN_FAV_DATE = "date_added";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create items table
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_CATEGORY + " TEXT" +
                ")";
        db.execSQL(CREATE_ITEMS_TABLE);

        // Create history table
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "(" +
                COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_QUERY + " TEXT NOT NULL," +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_HISTORY_TABLE);

        // Create favorites table
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "(" +
                COLUMN_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FAV_QUERY + " TEXT NOT NULL UNIQUE," +
                COLUMN_FAV_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_FAVORITES_TABLE);

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        String[] items = {
            "Android Development",
            "Android Studio",
            "Android SDK",
            "Android Emulator",
            "Android Manifest",
            "Android Permissions",
            "Android Services",
            "Android Activities",
            "Android Fragments",
            "Android Intent",
            "Android SharedPreferences",
            "Android Database",
            "Android SQLite",
            "Android Networking",
            "Android JSON",
            "Android API",
            "Android REST API",
            "Android Web Services",
            "Android Security",
            "Android Cryptography",
            "Android UI Design",
            "Android Material Design",
            "Android Layouts",
            "Android Views",
            "Android Widgets",
            "Android Buttons",
            "Android TextViews",
            "Android EditText",
            "Android ListView",
            "Android RecyclerView",
            "Android GridView",
            "Android Dialogs",
            "Android Notifications",
            "Android Sensors",
            "Android Camera",
            "Android GPS",
            "Android Location",
            "Android Maps",
            "Android Multimedia",
            "Android Video",
            "Android Audio",
            "Android Graphics",
            "Android Canvas",
            "Android Animation",
            "Android Threading",
            "Android Handlers",
            "Android AsyncTask",
            "Android Coroutines",
            "Android LiveData",
            "Android ViewModel",
            "Android Repository Pattern",
            "Android Dependency Injection",
            "Android Testing",
            "Android JUnit",
            "Android Espresso",
            "Android Mockito",
            "Android Performance",
            "Android Memory",
            "Android Battery",
            "Android Power Management",
            "Android Cloud Messaging",
            "Android Firebase",
            "Android Google Play",
            "Android Publishing",
            "Android App Store",
            "Android APK",
            "Android Bundle",
            "Java Programming",
            "Java Basics",
            "Java OOP",
            "Java Collections",
            "Java Streams",
            "Java Lambda",
            "Java Reflection",
            "Java Generics",
            "Java Annotations",
            "Java Design Patterns",
            "Kotlin Language",
            "Kotlin Coroutines",
            "Kotlin Extensions",
            "Kotlin Data Classes",
            "XML Parsing",
            "JSON Parsing",
            "Database Design",
            "SQL Queries",
            "Entity Relationships",
            "Web Development",
            "HTML",
            "CSS",
            "JavaScript",
            "Backend Development",
            "Node.js",
            "Express.js",
            "Python Programming",
            "Python Django",
            "Python Flask"
        };

        for (String item : items) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, item);
            values.put(COLUMN_DESCRIPTION, "Learn about " + item);
            values.put(COLUMN_CATEGORY, "Technology");
            db.insert(TABLE_ITEMS, null, values);
        }
    }

    // Items methods
    public List<String> searchItems(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> results = new ArrayList<>();

        String selection = COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%"};

        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_TITLE},
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                results.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    public List<String> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> items = new ArrayList<>();

        Cursor cursor = db.query(TABLE_ITEMS, new String[]{COLUMN_TITLE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    // History methods
    public void addToHistory(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUERY, query);
        db.insert(TABLE_HISTORY, null, values);
    }

    public List<String> getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> history = new ArrayList<>();

        String orderBy = COLUMN_TIMESTAMP + " DESC LIMIT 50";
        Cursor cursor = db.query(TABLE_HISTORY, new String[]{COLUMN_QUERY},
                null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                history.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return history;
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, null, null);
    }

    // Favorites methods
    public void addToFavorites(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAV_QUERY, query);
        try {
            db.insert(TABLE_FAVORITES, null, values);
        } catch (Exception e) {
            // Already exists
        }
    }

    public List<String> getFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> favorites = new ArrayList<>();

        String orderBy = COLUMN_FAV_DATE + " DESC";
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_FAV_QUERY},
                null, null, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                favorites.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favorites;
    }

    public void removeFromFavorites(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_FAV_QUERY + " = ?", new String[]{query});
    }

    public boolean isFavorite(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_FAV_QUERY + " = ?",
                new String[]{query}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void clearFavorites() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, null, null);
    }
}
