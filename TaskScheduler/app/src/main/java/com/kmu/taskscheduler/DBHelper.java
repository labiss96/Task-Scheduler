package com.kmu.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

import static android.content.ContentValues.TAG;

/*
*   Dday 키를 unvisible에 넘겨주는 평균값으로 이용하기로 결정.
*   d-day는 끝나는 날 - 현재날 로 구할 수도 있으며 이게 실시간 반영 되기 때문에 더 구현하기 쉬움.
*   하나의 db에 테이블 두개 운용하기로 결정.
* */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DBVERSION = 1;
    public static final String DBFILE = "task_db.db";

    public static final String TABLE_NAME = "VISIBLE_DATA";
    public static final String NUM = "number";
    public static final String TASK = "task";
    public static final String FINALDATE = "finalDate";
    public static final String STARTDATE = "startDate";
    public static final String TITLE = "title";
    public static final String DDAY = "Dday";
    public static final String CONTENT = "contet";

    public static final String SQL_CREAT_TB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " " + "(" + NUM + " INTEGER NOT NULL" + ", " + TASK +" INTEGER"+", "+
            FINALDATE + " TEXT" + ", " + TITLE + " TEXT" + ", " +
            STARTDATE + " TEXT" + ", " + CONTENT + " TEXT" + ", " +
            DDAY + " INTEGER" + ")";

    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TABLE_NAME ;

    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME ;

    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME ;

    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TABLE_NAME + " " + "(" +
            NUM + ", " + TASK + ", " + FINALDATE + ", " + TITLE +", "+ STARTDATE +", "+CONTENT+", "+ DDAY+ ") VALUES " ;

    public static final String TABLE_NAME_2 = "UNVISIBLE_DATA";
    public static final String NUM_2 = "number";
    public static final String USERSELECT = "userSelection";
    public static final String AVERAGE = "averageDay";
    public static final String CUSTOM = "customDay";

    public DBHelper(Context context){
        super(context,DBFILE,null,DBVERSION);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL(DBHelper.SQL_CREAT_TB);
    }
    public void onUpgrade(SQLiteDatabase db,int oldv , int newv){
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db,int oldv,int newv){

    }
}
