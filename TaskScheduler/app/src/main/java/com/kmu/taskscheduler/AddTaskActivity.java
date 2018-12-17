package com.kmu.taskscheduler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    Spinner spinner;

    private TextView taskDate;
    private Button taskPickDate;

    private int taskYear, taskMonth, taskDay;
    static String category = "";

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle bundle)  {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_task);


        //spinner 과제 카테고리 창 구현


        final String[] list = getResources().getStringArray(R.array.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        spinner = (Spinner)findViewById(R.id.category);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  {
                //Toast.makeText(AddTaskActivity.this, ""+spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                category = spinner.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });


        final TextInputEditText titleText = (TextInputEditText) findViewById(R.id.title);
        final TextInputEditText contentText = (TextInputEditText) findViewById(R.id.contents);


        Button addTaskButton = (Button) findViewById(R.id.addTask) ;
        addTaskButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, contents;

                title = titleText.getText().toString();
                contents = contentText.getText().toString();
//                category = spinner.getSelectedItem().toString();


                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);

                intent.putExtra("title",title);
                intent.putExtra("contents",contents);
                intent.putExtra("category",category);
                intent.putExtra("year", taskYear);
                intent.putExtra("month", taskMonth+1);
                intent.putExtra("day", taskDay);


                setResult(RESULT_OK, intent);
        finish();

    }
});


        taskDate = (TextView)findViewById(R.id.dateView);
        taskPickDate = (Button)findViewById(R.id.datePicker);

        //과제기한 버튼 누르면 날짜 선택 다이얼로그 띄워줌
        taskPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(DATE_DIALOG_ID).show();
            }
        });
        //현재 날짜 가져와서 기본값으로 둘께용
        final Calendar c = Calendar.getInstance();
        taskYear = c.get(Calendar.YEAR);
        taskMonth = c.get(Calendar.MONTH);
        taskDay = c.get(Calendar.DAY_OF_MONTH);


        //텍스트뷰 갱신
        updateDisplay();
    }

    //calender 클래스로 추출한 달은 0부터 시작되기 때문에 +1을 해주어야 한다.
    private void updateDisplay(){
        taskDate.setText(String.format("%d년 %d월 %d일", taskYear, taskMonth+1, taskDay));
    }

    protected Dialog createDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, taskYear, taskMonth, taskDay);
    }
    //DatePicker 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    taskYear = year;
                    taskMonth = monthOfYear;
                    taskDay = dayOfMonth;
                    updateDisplay();
                }
            };


}
