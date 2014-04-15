package com.dturi.itemscounter.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by DTuri on 15/04/14.
 */
public class InterestingItemsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_SCORE};

    public InterestingItemsDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public InterestingItems createItem(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_SCORE, 0);
        long insertId = database.insert(DatabaseHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        InterestingItems newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public void updateItemCounter(InterestingItems item) {
        int id = item.GetID();
        System.out.println("Item updated with id: " + id);
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SCORE, item.GetScore());
        String itemID = String.valueOf(item.GetID());
        database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + " = " + itemID, null);
    }

    public void updateItemName(InterestingItems item) {
        int id = item.GetID();
        System.out.println("Item updated with id: " + id);
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, item.GetName());
        String itemID = String.valueOf(item.GetID());
        database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + " = " + itemID, null);
    }

    public void deleteItem(InterestingItems item) {
        int id = item.GetID();
        System.out.println("Item deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    /*public List<InterestingItems> getAllItems() {
        List<InterestingItems> items = new ArrayList<InterestingItems>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InterestingItems item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return items;
    }*/

    public ArrayList<InterestingItems> getAllItems() {
        ArrayList<InterestingItems> gridArray = new ArrayList<InterestingItems>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InterestingItems item = cursorToItem(cursor);
            gridArray.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return gridArray;
    }

    private InterestingItems cursorToItem(Cursor cursor) {
        InterestingItems item = new InterestingItems();
        item.SetID(cursor.getInt(0));
        item.SetName(cursor.getString(1));
        item.SetScore(cursor.getInt(2));
        return item;
    }
}