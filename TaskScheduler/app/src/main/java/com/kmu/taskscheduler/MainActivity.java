package com.kmu.taskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBHelper mydb;
    SQLiteDatabase sqliteDB;
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<Integer> tasksId = new ArrayList<Integer>();
    String title_value, contents_value, category_value;
    int year_value, month_value, day_value;
    ListAdapter adapter;

    String detail_title, detail_contents, detail_category, detail_finalDay;
    int detail_dday, detail_id;

    private SQLiteDatabase init_database(){
        SQLiteDatabase db = null ;

        //File file = getDatabasePath("contact.db") ;
        File file = new File(getFilesDir(), "task_db.db") ;
        System.out.println("PATH : " + file.toString()) ;
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null) ;
        } catch (SQLiteException e) {
            e.printStackTrace() ;
        } if (db == null) {
            System.out.println("DB creation failed. " + file.getAbsolutePath()) ;
        }
        return db;
    }

    private void init_tables(){
        mydb = new DBHelper(this);
    }
    /*@Override
    protected  void onDestroy(){
        sqliteDB.close();
        super.onDestroy();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sqliteDB = init_database();
        init_tables();
        sqliteDB.execSQL(mydb.SQL_CREAT_TB);
        sqliteDB.execSQL(mydb.SQL_CREATE_TB2);
        Cursor cs2 = sqliteDB.rawQuery(DBHelper.SQL_SELECT,null);
        if(0==cs2.getCount()){
            add(0,0,1,"","","","",0);

        }
        while(cs2.moveToNext()) {
            if(cs2.getInt(1)==0){
            String no = cs2.getString(4);
            int i = cs2.getInt(0);
            tasksId.add(i);
            tasks.add(no);}
        }
        Cursor cs = sqliteDB.rawQuery(DBHelper.SQL_SELECT_2,null);
        if(cs.getCount()==0){
            int j = 0;
            for(int i = 0 ; i<6;i++){
                if(i == 5)
                    j = 4;
                addt2(i+1,3+j);
            }
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //과제 클릭 시 데이터베이스에 저장된 해당 과제의 값들을 detailActivity로 보냄
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

                        getValue(position);
                        detailIntent.putExtra("title",detail_title);
                        detailIntent.putExtra("contents",detail_contents);
                        detailIntent.putExtra("category",detail_category);
                        detailIntent.putExtra("position", position); //리스트에 어떤 값인지를 구분하기 위해 position 값도 같이 넘겨줌.
                        detailIntent.putExtra("taskID", detail_id);
                        detailIntent.putExtra("finalDay", detail_finalDay);
                        detailIntent.putExtra("dday",detail_dday);

                        startActivityForResult(detailIntent, 3001);
                    }
                }
        );

        //과제 길게 클릭 시 해당 과제를 리스트에서 제거.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {

                int task_id = 0;
                String title = listView.getAdapter().getItem(position).toString();
                Toast.makeText(getApplicationContext(), title+ "과제가 삭제되었습니다.", Toast.LENGTH_LONG).show();

                Cursor cs = sqliteDB.rawQuery(DBHelper.SQL_SELECT,null);
                if(cs.moveToPosition(position))
                    task_id = cs.getInt(0);

                sqliteDB.delete(mydb.TABLE_NAME, mydb.ID+"=?", new String[]{String.valueOf(task_id)});

                tasks.remove(position);
                ((BaseAdapter)adapter).notifyDataSetChanged();
                return false;
            }
        });


    } //end onCreate

    private void updateNumber(int id){
        sqliteDB.execSQL(mydb.SQL_UPDATE_NUM+id);
    }
    private void getValue(int k){
        Cursor cs = sqliteDB.rawQuery(DBHelper.SQL_SELECT,null);
        if(cs.moveToPosition(k)){
            detail_category = taskChangeToString(cs.getInt(2));
            detail_contents = cs.getString(6);
            detail_title = cs.getString(4);
            detail_finalDay = cs.getString(3);
            detail_dday = cs.getInt(7);
            detail_id = cs.getInt(0);

            // get value of table (need index or keyvalue)
        }
    }
    private void add(int id,int n,int task, String fd,String sd,String cont,String title,int dday){
        SQLiteDatabase sqdb = mydb.getWritableDatabase();
        sqdb.execSQL(DBHelper.SQL_DELETE);

        String sqlInsert = DBHelper.SQL_INSERT + " ("+id+", "+n+", "+
                task + ", " + "'" +
                fd +"'," + "'"+
                title + "', " +"'"+
                sd + "'," + "'"+
                cont + "', "+ dday +")"; //1,
        sqdb.execSQL(sqlInsert);
        sqliteDB.execSQL(sqlInsert);
    }
    private void addt2(int id,int aver){
        String sqlInsert = mydb.SQL_INSERT_2 + " ("+id+", "+aver+")";
        sqliteDB.execSQL(sqlInsert);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            SQLiteDatabase sqdb = mydb.getReadableDatabase();
            Cursor cs = sqliteDB.rawQuery(DBHelper.SQL_SELECT,null);
            switch (requestCode){
                case 3000: { // AddTaskActivity
                    title_value = data.getStringExtra("title");
                    contents_value = data.getStringExtra("contents");
                    category_value = data.getStringExtra("category");
                    year_value = data.getIntExtra("year", 1);
                    month_value = data.getIntExtra("month", 1);
                    day_value = data.getIntExtra("day", 1);

                    String fd = String.valueOf(year_value) +"/"+ String.valueOf(month_value)+"/"+String.valueOf(day_value);

                    Date n = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    cs.moveToLast();
                    Calendar now = Calendar.getInstance();
                    int date = now.get(Calendar.DATE);
                    add(cs.getInt(0)+1,0,taskChange(category_value),fd,sdf.format(n),contents_value,title_value,date);

                    cs = sqliteDB.rawQuery(DBHelper.SQL_SELECT, null);

                    if(cs.moveToLast())
                        tasks.add(cs.getString(4));
                        //tasksId.add(cs.getInt(0));
                    ((BaseAdapter)adapter).notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), title_value + year_value +"년"+ month_value +"월"+ day_value + "일", Toast.LENGTH_LONG).show();
                    break;
                }
                case 3001: { // DetailActivity에서 제거 및 과제완료 버튼
                    String deleteTitle = "";
                    String completeTitle = "";
                    int deletePosition, completePosition, buttonType, completedTask_id, deleteTask_id;

                    deleteTitle = data.getStringExtra("deleteTitle");
                    completeTitle = data.getStringExtra("completedTitle");
                    deletePosition = data.getIntExtra("deletePosition", 1);
                    completePosition = data.getIntExtra("completedPosition", 1);
                    buttonType = data.getIntExtra("buttonType", 0);
                    completedTask_id = data.getIntExtra("completedID", 0);
                    deleteTask_id = data.getIntExtra("deleteID", 0);


                    switch (buttonType){
                        case 1: { //과제완료
                            tasks.remove(completePosition);
                            ((BaseAdapter)adapter).notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), completeTitle, Toast.LENGTH_LONG).show();
                            //DB에서 넘버링 바꿔서 완료코드로 전환
                            updateNumber(completedTask_id);
                            break;
                        }
                        case 2: { //과제삭제
                            tasks.remove(deletePosition);
                            ((BaseAdapter) adapter).notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), deleteTitle, Toast.LENGTH_LONG).show();
                            //DB에서 제거하는 코드
                            sqliteDB.delete(mydb.TABLE_NAME, mydb.ID+"=?", new String[]{String.valueOf(deleteTask_id)});
                            break;
                        }
                        default:
                            break;
                    }
                }
                default:
                    break;
            }
        }
    }

    private int taskChange(String t) {
        int r = 0;
        switch (t) {
            case "레포트":
                r= 1;
                break;
            case "독후감":
                r= 2;
                break;
            case "코딩":
                r= 3;
                break;
            case "Quiz":
                r= 4;
                break;
            case "라이브 코딩테스트":
                r= 5;
                break;
            case "중간/기말고사":
                r= 6;
                break;
        }
        return r;
    }
    private String taskChangeToString(int t){
        String r = null;
        switch (t){
            case 1:
                r="레포트";
                break;
            case 2:
                r = "독후감";
                break;
            case 3:
                r = "코딩";
                break;
            case 4:
                r = "Quiz";
                break;
            case 5:
                r = "라이브 코딩테스트";
                break;
            case 6:
                r = "중간/기말고사";
                break;
        }
        return r;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfSt atement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Cursor cs = sqliteDB.rawQuery(mydb.SQL_SELECT,null);
        Fragment fragment = null;

        //네비게이션바에 메뉴 클릭 시 기능.
        if (id == R.id.main_task) {
            //fragment = new AddTaskFragment();
        } else if (id == R.id.add_task) {
            Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(addTaskIntent, 3000);
        } else if (id == R.id.complete_task) {
            Intent completedIntent = new Intent(this, CompletedTaskActivity.class);
            String[] arr = new String[100];
            int i = 0 ;

                while(cs.moveToNext()){
                    int c2 = cs.getInt(1);
                    if(c2 == 1){
                        arr[i] = cs.getString(4);
                        i++;
                    }

            }
            completedIntent.putExtra("completedTasks",arr);
            //Toast.makeText(getApplicationContext(), "" +i , Toast.LENGTH_LONG).show();

            startActivity(completedIntent);
        } else if (id == R.id.average) {

        } else if (id == R.id.info) {

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
