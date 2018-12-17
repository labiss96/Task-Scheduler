package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

//        Toast.makeText(getApplicationContext(), arr[0] , Toast.LENGTH_LONG).show();
//
//        for(int i = 0; i<arr.length; ++i) {
//            tasks.add(arr[i]);
//        }

        ((BaseAdapter)adapter).notifyDataSetChanged();

    }
}
