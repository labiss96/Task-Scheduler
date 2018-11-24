package com.example.gilwoongkang.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/*
*   Dday 키를 unvisible에 넘겨주는 평균값으로 이용하기로 결정.
*   d-day는 끝나는 날 - 현재날 로 구할 수도 있으며 이게 실시간 반영 되기 때문에 더 구현하기 쉬움.
*   하나의 db에 테이블 두개 운용하기로 결정.
* */
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


    public static final String TABLE_NAME_2 = "UNVISIBLE_DATA";
    public static final String NUM_2 = "number";
    public static final String USERSELECT = "userSelection";
    public static final String AVERAGE = "averageDay";
    public static final String CUSTOM = "customDay";


    static final String DB_NAME = "task_db.db";
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

        stringBuffer.append("CREATE TABLE "+TABLE_NAME_2 + "(");
        stringBuffer.append(NUM_2 + "INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append(USERSELECT + "INTEGER, ");
        stringBuffer.append(AVERAGE + "REAL");
        stringBuffer.append(CUSTOM + "INTEGER)");

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
    public boolean insertData2(int us,double ad,int cd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(USERSELECT,us);
        values.put(AVERAGE,ad);
        values.put(CUSTOM,cd);
        long result = db.insert(TABLE_NAME_2,null,values);
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

    public boolean updateData(String number,int task,String finalDate,String startDate,String title,int Dday,String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK, task);
        values.put(FINALDATE, finalDate);
        values.put(STARTDATE, startDate);
        values.put(TITLE, title);
        values.put(DDAY, Dday);
        values.put(CONTENT, content);

        db.update(TABLE_NAME, values, "NUM =  ?", new String[]{number});
        return true;
    }

    public boolean updateData2(String number,int us,double ad,int cd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(USERSELECT,us);
        values.put(AVERAGE,ad);
        values.put(CUSTOM,cd);

        db.update(TABLE_NAME_2,values,"NUM_2 = ?",new String[] {number});
        return true;
        }

    public Integer deleteData(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "number = ?" , new String[] {number});
    }

}
