package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    String tit, cnt, cat;
    int position;



    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.detail_main);


        TextView title = (TextView) findViewById(R.id.title);
        TextView contents = (TextView) findViewById(R.id.contents);
        TextView category = (TextView) findViewById(R.id.category);

        Button deleteButton = (Button) findViewById(R.id.delete);
        Button completedButton = (Button) findViewById(R.id.complete);


        //title textView에 과제 string 값 받아서 출력.
        Intent intent = getIntent();
        tit = intent.getStringExtra("title");
        cnt = intent.getStringExtra("contents");
        cat = intent.getStringExtra("category");
        position = intent.getIntExtra("position",1);

        title.setText(tit);
        contents.setText(cnt);
        category.setText(cat);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                intent.putExtra("deleteTitle", tit);
                intent.putExtra("deletePosition", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                intent.putExtra("completedTitle", tit);
                intent.putExtra("completedPosition", position);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart()   {
        super.onStart();

//        TextView title = (TextView) findViewById(R.id.title);
//        TextView contents = (TextView) findViewById(R.id.contents);
//        TextView category = (TextView) findViewById(R.id.category);
//
//        Intent intent = getIntent();
//        tit = intent.getStringExtra("title");
//        cnt = intent.getStringExtra("contents");
//        cat = intent.getStringExtra("category");
//
//        title.setText(tit);
//        contents.setText(cnt);
//        category.setText(cat);
    }
}
