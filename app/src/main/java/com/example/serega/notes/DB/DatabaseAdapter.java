package com.example.serega.notes.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.serega.notes.JSON.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION
        , DatabaseHelper.COLUMN_COLOR};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }
    public List<Item> getItems(){
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
                String color = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COLOR));
                items.add(new Item(id, name, description, color));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  items;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Item getItem(long id){
        Item item = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COLOR));
            item = new Item(id, name, description, color);
        }
        cursor.close();
        return  item;
    }

    public long insert(Item item){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, item.getName());
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        cv.put(DatabaseHelper.COLUMN_COLOR, item.getColor());

        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long userId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Item item){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(item.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, item.getName());
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        cv.put(DatabaseHelper.COLUMN_COLOR, item.getColor());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}