package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.detail_main);

        String tit, cnt, cat;

        //title textView에 과제 string 값 받아서 출력.
        TextView title = (TextView) findViewById(R.id.title);
        TextView contents = (TextView) findViewById(R.id.contents);
        TextView category = (TextView) findViewById(R.id.category);


        Intent intent = getIntent();
        tit = intent.getStringExtra("title");
        cnt = intent.getStringExtra("contents");
        cat = intent.getStringExtra("category");

        title.setText(tit);
        contents.setText(cnt);
        category.setText(cat);


        //title.setText(title_contents);

    }
}
