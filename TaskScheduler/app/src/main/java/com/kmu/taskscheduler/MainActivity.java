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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBHelper mydb;
    SQLiteDatabase sqliteDB;
    ArrayList<String> tasks = new ArrayList<String>();
    String title_value, contents_value, category_value;
    int year_value, month_value, day_value;
    ListAdapter adapter;

    String detail_title, detail_contents, detail_category;

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
        save_value();

        SQLiteDatabase sqdb = mydb.getReadableDatabase();
        Cursor cs = sqdb.rawQuery(DBHelper.SQL_SELECT,null);


        //listView에 들어가는 과제들을 arraylist로 담았음.

//        tasks.add("Quiz2");
//        tasks.add("컴구 HW6");
//        tasks.add("수학과문명 독후감");

        if(cs.moveToFirst()) {
            String no = cs.getString(5);
            tasks.add(no);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //과제 클릭 시 데이터베이스에 저장된 해당 과제의 값들을 detailActivity로 보냄
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                        getValue(position);
                        intent.putExtra("title",detail_title);
                        intent.putExtra("contents",detail_contents);
                        intent.putExtra("category",detail_category);

                        startActivity(intent);
                    }
                }
        );

        //과제 길게 클릭 시 해당 과제를 리스트에서 제거.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                tasks.remove(position);
                ((BaseAdapter)adapter).notifyDataSetChanged();
                return false;
            }
        });


    }

    private void getValue(int k){
        SQLiteDatabase sqdb = mydb.getReadableDatabase();
        Cursor cs = sqdb.rawQuery(DBHelper.SQL_SELECT,null);
        if(cs.move(k)){
            detail_category = cs.getString(1);
            detail_contents = cs.getString(4);
            detail_title = cs.getString(5);
            // get value of table (need index or keyvalue)
        }
    }
    private void add(int n,int task , String fd,String sd,String cont,String title,int dday){
        SQLiteDatabase sqdb = mydb.getWritableDatabase();
        sqdb.execSQL(DBHelper.SQL_DELETE);

        String sqlInsert = DBHelper.SQL_INSERT + " ("+n+", "+
                task + ", " + "'" +
                fd +"'," + "'"+
                sd + "', " +"'"+
                cont + "'," + "'"+
                title + "', "+ dday +")"; //1,
        sqdb.execSQL(sqlInsert);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            SQLiteDatabase sqdb = mydb.getReadableDatabase();
            Cursor cs = sqdb.rawQuery(DBHelper.SQL_SELECT,null);
            switch (requestCode){
                case 3000: {
                    title_value = data.getStringExtra("title");
                    contents_value = data.getStringExtra("contents");
                    category_value = data.getStringExtra("category");
                    year_value = data.getIntExtra("year", 1);
                    month_value = data.getIntExtra("month", 1);
                    day_value = data.getIntExtra("day", 1);

                    add(4,2,"20180909","201982091",contents_value,title_value,4);
                    if(cs.moveToLast())
                        tasks.add(cs.getString(5));
                    ((BaseAdapter)adapter).notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), title_value + year_value +"년"+ month_value +"월"+ day_value + "일", Toast.LENGTH_LONG).show();
                    break;
                }
                default:
                    break;
            }
        }
    }


    private void save_value(){
        SQLiteDatabase sqdb = mydb.getReadableDatabase();
        sqdb.execSQL(DBHelper.SQL_DELETE);

        String fd = "20151515";
        String sd = "20193278";
        String cont = "test";
        String title = "testTitle";
        String task = "quiz";

        String sqlInsert = DBHelper.SQL_INSERT + " (" +
                1 + ", " +
                1 + ", " + "'" +
                fd + "', " + "'" +
                sd + "', " + "'" +
                cont + "', " + "'" +
                title + "', " +
                1 + ")" ;
        sqdb.execSQL(sqlInsert) ;

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

        Fragment fragment = null;

        //네비게이션바에 메뉴 클릭 시 기능.
        if (id == R.id.main_task) {
            //fragment = new AddTaskFragment();
        } else if (id == R.id.add_task) {
            Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(addTaskIntent, 3000);
        } else if (id == R.id.complete_task) {
            Intent completedIntent = new Intent(this, CompletedTaskActivity.class);
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
