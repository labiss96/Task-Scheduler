package com.kmu.taskscheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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

        //이제 리스트뷰에 DB에 저장된 완료된 과제를 넣어주면 되는데.. oncreate 될때만 슥 넣어주면 되겠지?



    }
}
