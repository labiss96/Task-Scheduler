package com.example.gilwoongkang.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    public Context context;

    public static final String TABLE_NAME = "VISIBLE_DATA";
    public static final String NUM = "number";
    public static final String TASK = "task";
    public static final String FINALDATE = "finalDate";
    public static final String STARTDATE = "startDate";
    public static final String TITLE = "title";
    public static final String DDAY = "Dday";
    public static final String CONTENT = "contet";

    static final String DB_NAME = "visibleData";
    static final int VERSION = 1;

    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE " +TABLE_NAME + "(");
        stringBuffer.append(NUM + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append(TASK + " INTEGER, ");
        stringBuffer.append(FINALDATE + " TEXT, ");
        stringBuffer.append(STARTDATE + " TEXT, ");
        stringBuffer.append(TITLE + " TEXT, ");
        stringBuffer.append(DDAY + " INTEGER, ");
        stringBuffer.append(CONTENT + " TEXT)");

        Log.d(TAG, stringBuffer.toString());
        db.execSQL(stringBuffer.toString());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertData(int task,String finalDate,String startDate,String title,int Dday,String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK,task);
        values.put(FINALDATE,finalDate);
        values.put(STARTDATE,startDate);
        values.put(TITLE,title);
        values.put(DDAY,Dday);
        values.put(CONTENT,content);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor corsor = db.rawQuery("select * from " + TABLE_NAME + " order by number desc", null);
        return corsor;
    }

    public boolean updateData(String number,int task,String finalDate,String startDate,String title,int Dday,String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK,task);
        values.put(FINALDATE,finalDate);
        values.put(STARTDATE,startDate);
        values.put(TITLE,title);
        values.put(DDAY,Dday);
        values.put(CONTENT,content);

        db.update(TABLE_NAME, values, "NUM =  ?", new String[] { number });
        return true;


    }
    public Integer deleteData(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "number = ?" , new String[] {number});
    }

}
