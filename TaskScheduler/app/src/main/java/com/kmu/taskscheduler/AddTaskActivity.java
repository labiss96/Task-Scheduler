package com.kmu.taskscheduler;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


        final Spinner spinner = (Spinner)findViewById(R.id.category);
        final TextInputEditText titleText = (TextInputEditText) findViewById(R.id.title);
        final TextInputEditText contentText = (TextInputEditText) findViewById(R.id.contents);



        Button addTaskButton = (Button) findViewById(R.id.addTask) ;
        addTaskButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String title, contents,category;

                title = titleText.getText().toString();
                contents = contentText.getText().toString();
                category = spinner.getSelectedItem().toString();


                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);

                intent.putExtra("title",title);
                intent.putExtra("contents",contents);
                intent.putExtra("category",category);

                setResult(RESULT_OK, intent);
                finish();

            }
        });



    }



}
