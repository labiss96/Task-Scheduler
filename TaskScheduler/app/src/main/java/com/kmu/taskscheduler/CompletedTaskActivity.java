package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompletedTaskActivity extends AppCompatActivity {

    ArrayList<String> tasks = new ArrayList<String>();
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.completed_task);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        ListView listView = (ListView) findViewById(R.id.completedTask);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        String[] arr = intent.getStringArrayExtra("completedTasks");

        //Toast.makeText(getApplicationContext(), arr[0] , Toast.LENGTH_LONG).show();

        int i = 0;
        while(arr[i] != null){
            tasks.add(arr[i]);
            ++i;
        }
        ((BaseAdapter)adapter).notifyDataSetChanged();


//        listView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
//
//                        getValue(position);
//                        detailIntent.putExtra("title",detail_title);
//                        detailIntent.putExtra("contents",detail_contents);
//                        detailIntent.putExtra("category",detail_category);
//                        detailIntent.putExtra("position", position); //리스트에 어떤 값인지를 구분하기 위해 position 값도 같이 넘겨줌.
//                        detailIntent.putExtra("taskID", detail_id);
//                        detailIntent.putExtra("finalDay", detail_finalDay);
//                        detailIntent.putExtra("dday",detail_dday);
//
//                        startActivityForResult(detailIntent, 3001);
//                    }
//                }
//        );

    }
}
