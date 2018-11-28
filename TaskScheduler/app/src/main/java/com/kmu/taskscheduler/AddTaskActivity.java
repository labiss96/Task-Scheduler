package com.kmu.taskscheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddTaskActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_task);

        //spinner 과제 카테고리 창 구현
        final String[] list = getResources().getStringArray(R.array.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        spinner = (Spinner)findViewById(R.id.category);
        spinner.setAdapter(adapter);

    }

}
