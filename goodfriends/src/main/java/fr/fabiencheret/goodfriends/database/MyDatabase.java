package fr.fabiencheret.goodfriends.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.googlecode.androidannotations.annotations.EBean;
import fr.fabiencheret.goodfriends.model.Debt;
import fr.fabiencheret.goodfriends.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 19/05/13
 * Time: 20:23
 */
@EBean
public class MyDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GoodFriends.db";
    private final static String FOREIGN = "PRAGMA foreign_keys = ON;";


    private static final String SQL_USER_ENTRIES = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.UserColumns.TABLE_NAME + " (" +
            DatabaseContract.UserColumns.COLUMN_NAME_NAME + " TEXT," +
            DatabaseContract.UserColumns.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT)";

    private static final String SQL_DEBT_ENTRIES = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.DebtColumns.TABLE_NAME + " (" +
            DatabaseContract.DebtColumns.COLUMN_NAME_POSITIVE + " INTEGER," +
            DatabaseContract.DebtColumns.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.DebtColumns.COLUMN_NAME_WHAT + " TEXT," +
            DatabaseContract.DebtColumns.COLUMN_NAME_USERID + " INTEGER," +
            "FOREIGN KEY (" + DatabaseContract.DebtColumns.COLUMN_NAME_USERID + ") REFERENCES " +
            DatabaseContract.UserColumns.TABLE_NAME + " (" + DatabaseContract.UserColumns.COLUMN_NAME_ID + "))";


    public MyDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FOREIGN);
        db.execSQL(SQL_USER_ENTRIES);
        db.execSQL(SQL_DEBT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
    }

    public void truncateDatabase(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseContract.DebtColumns.TABLE_NAME);
        db.execSQL("DELETE FROM " + DatabaseContract.UserColumns.TABLE_NAME);
    }

    public synchronized void store(User user){
        User exists =  getUserByName(user.name);
        if(exists == null){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(DatabaseContract.UserColumns.COLUMN_NAME_NAME,user.name);
            user.ID = db.insert(DatabaseContract.UserColumns.TABLE_NAME,null,val);
        } else {
            user.ID = exists.ID;
        }
    }

    public synchronized void store(Debt debt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        store(debt.user);
        val.put(DatabaseContract.DebtColumns.COLUMN_NAME_USERID, debt.user.ID);
        val.put(DatabaseContract.DebtColumns.COLUMN_NAME_POSITIVE, debt.positive ? 1 : 0);
        val.put(DatabaseContract.DebtColumns.COLUMN_NAME_WHAT,debt.what);
        debt.ID = db.insert(DatabaseContract.DebtColumns.TABLE_NAME,null,val);
    }

    public synchronized Debt getDebtById(long id){
        SQLiteDatabase db = getReadableDatabase();
        Debt result = null;
        String[] projection = {DatabaseContract.DebtColumns.COLUMN_NAME_ID,
                DatabaseContract.DebtColumns.COLUMN_NAME_POSITIVE,
                DatabaseContract.DebtColumns.COLUMN_NAME_USERID,
                DatabaseContract.DebtColumns.COLUMN_NAME_WHAT};
        String[] selection = { String.valueOf(id) };
        Cursor c = db.query(DatabaseContract.DebtColumns.TABLE_NAME,projection,
                DatabaseContract.DebtColumns.COLUMN_NAME_ID, selection,null, null, null);
        if(c.moveToFirst()){
            result = new Debt(c.getLong(0),getUserById(c.getLong(1)),c.getInt(2)>0,c.getString(3));
        }
        return result;
    }

    public synchronized List<Debt> getAllDebts(){
        SQLiteDatabase db = getReadableDatabase();
        List<Debt> result = new ArrayList<Debt>();
        String[] projection = {DatabaseContract.DebtColumns.COLUMN_NAME_ID,
                DatabaseContract.DebtColumns.COLUMN_NAME_POSITIVE,
                DatabaseContract.DebtColumns.COLUMN_NAME_USERID,
                DatabaseContract.DebtColumns.COLUMN_NAME_WHAT};
        Cursor c = db.query(DatabaseContract.DebtColumns.TABLE_NAME,projection, null, null, null, null, null);
        if(c.moveToFirst()){
            do {
                result.add(new Debt(c.getLong(0),getUserById(c.getLong(1)),c.getInt(2)>0,c.getString(3)));
            }   while(c.moveToNext());
        }
        return result;
    }

    public synchronized List<User> getAllUsers(){
        SQLiteDatabase db = getReadableDatabase();
        List<User> result = new ArrayList<User>();
        String[] projection = { DatabaseContract.UserColumns.COLUMN_NAME_ID,
                DatabaseContract.UserColumns.COLUMN_NAME_NAME };
        Cursor c = db.query(DatabaseContract.UserColumns.TABLE_NAME,projection,null, null, null, null, null);

        if(c.moveToFirst()){
            do {
                result.add(new User(c.getLong(0),c.getString(1)));
            } while(c.moveToNext());
        }
        return result;
    }

    public synchronized User getUserByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        User result = null;
        String[] projection = { DatabaseContract.UserColumns.COLUMN_NAME_ID,
                DatabaseContract.UserColumns.COLUMN_NAME_NAME };
        String[] selection = { name };
        Cursor c = db.query(DatabaseContract.UserColumns.TABLE_NAME,projection,
                DatabaseContract.UserColumns.COLUMN_NAME_NAME + " LIKE ?", selection,null, null, null);
        if(c.moveToFirst()){
            result = new User(c.getLong(0),c.getString(1));
        }
        return result;
    }

    public synchronized User getUserById(long id){
        SQLiteDatabase db = getReadableDatabase();
        User result = null;
        String[] projection = { DatabaseContract.UserColumns.COLUMN_NAME_ID,
                DatabaseContract.UserColumns.COLUMN_NAME_NAME };
        String[] selection = {String.valueOf(id)};
        Cursor c = db.query(DatabaseContract.UserColumns.TABLE_NAME,projection,
                DatabaseContract.UserColumns.COLUMN_NAME_ID + " LIKE ?", selection,null, null, null);
        if(c.moveToFirst()){
            result = new User(c.getLong(0),c.getString(1));
        }
        return result;
    }

}
