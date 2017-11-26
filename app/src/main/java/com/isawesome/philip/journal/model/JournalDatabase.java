package com.isawesome.philip.journal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Philip on 11/25/2017.
 */

public class JournalDatabase extends SQLiteOpenHelper implements ModelInterface
{


    private static final String TAG = "JTAG";
    private static final String DB_NAME = "JournalDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Entries";
    private static final String COL_ID = "id";
    private static final String COL_CONTENT = "Content";
    private static final String COL_DATE_CREATED = "Date_Created";


    public JournalDatabase(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Queries.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(Queries.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public Long create(JournalEntry entry)
    {
        Log.d(TAG, "create: Creating a new Entry in the database");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        entry.setDateCreated(
                new SimpleDateFormat("yyyy-MM-dd-E").format(new Date())
        );
        values.put(COL_CONTENT, entry.getContent());
        values.put(COL_DATE_CREATED, entry.getDateCreated());
        return db.insert(TABLE_NAME, null, values);

    }

    @Override
    public List<JournalEntry> read()
    {
        Log.d(TAG, "read: Getting all the Entries from the database.");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Queries.GET_ALL, null);
        Log.d(TAG, "read: From rawQuery, there are " + cursor.getCount() + " items.");
        ArrayList<JournalEntry> entries = new ArrayList<>();
        if (cursor.getCount() == 0)
            return entries;

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            String id = cursor.getString(cursor.getColumnIndex(COL_ID));
            String content = cursor.getString(cursor.getColumnIndex(COL_CONTENT));
            String dateCreated = cursor.getString(cursor.getColumnIndex(COL_DATE_CREATED));
            JournalEntry entry = new JournalEntry(Long.parseLong(id), content, dateCreated);
            entries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, "read: There were " + entries.size() + " items");
        return entries;
    }

    @Override
    public void update(JournalEntry entry)
    {
        Log.d(TAG, "update: Updating entry with id " + entry.getId());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, entry.getContent());
        db.update(TABLE_NAME, values, "id = ?",
                new String[] {Long.toString(entry.getId())});
    }

    @Override
    public void delete(JournalEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{Long.toString(entry.getId())});
    }

    private static class Queries
    {
        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CONTENT + " TEXT NOT NULL, "
                    + COL_DATE_CREATED + " TEXT NOT NULL "
                + ");";
        private  static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        private static final String GET_ALL =
                "SELECT * FROM " + TABLE_NAME + ";";
    }
}
