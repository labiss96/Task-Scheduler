package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity  extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        //title textView에 과제 string 값 받아서 출력.
        TextView title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String title_contents = intent.getStringExtra("title");
        title.setText(title_contents);

    }
}
