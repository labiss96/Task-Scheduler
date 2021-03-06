package com.kmu.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {

    String tit, cnt, cat, finDay;
    int position, id, dday, averageDate;



    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.detail_main);


        TextView title = (TextView) findViewById(R.id.title);
        TextView contents = (TextView) findViewById(R.id.contents);
        TextView category = (TextView) findViewById(R.id.category);
        TextView finalDay = (TextView) findViewById(R.id.finalDay);
        TextView d_day = (TextView) findViewById(R.id.D_Day);
        TextView average = (TextView) findViewById(R.id.average);

        Button deleteButton = (Button) findViewById(R.id.delete);
        Button completedButton = (Button) findViewById(R.id.complete);

        //title textView에 과제 string 값 받아서 출력.
        Intent intent = getIntent();
        tit = intent.getStringExtra("title");
        cnt = intent.getStringExtra("contents");
        cat = intent.getStringExtra("category");
        position = intent.getIntExtra("position",0);
        id = intent.getIntExtra("taskID", 0);
        finDay = intent.getStringExtra("finalDay");
        dday = intent.getIntExtra("dday", 0);
        averageDate = intent.getIntExtra("averageDate", 0);

        String[] finalDayYMD = finDay.split("/");
        int year = Integer.valueOf(finalDayYMD[0]);
        int mon = Integer.valueOf(finalDayYMD[1]);
        int day = Integer.valueOf(finalDayYMD[2]);

        Calendar c = Calendar.getInstance();
        Calendar cc = Calendar.getInstance();
        c.set(year,mon,day);
        int fd = c.get(Calendar.DATE);
        int d = fd - cc.get(Calendar.DATE);

        title.setText(tit);
        contents.setText(cnt);
        category.setText(cat);
        finalDay.setText(finDay);
        d_day.setText("D - "+String.valueOf(d));
        average.setText("당신의 평균 과제 수행일은 " + averageDate + " 입니다.");

        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                intent.putExtra("completedTitle", tit);
                intent.putExtra("completedPosition", position);
                intent.putExtra("completedID", id);
                intent.putExtra("buttonType", 1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);

                intent.putExtra("deleteTitle", tit);
                intent.putExtra("deletePosition", position);
                intent.putExtra("deleteID", id);
                intent.putExtra("buttonType", 2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
