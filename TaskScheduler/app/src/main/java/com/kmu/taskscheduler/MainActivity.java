package com.kmu.taskscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

            final ArrayList<String> tasks = new ArrayList<String>();
            tasks.add("자료구조 Quiz");
            tasks.add("컴구 HW6");
            tasks.add("수학과문명 독후감");

            final ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                            String item = String.valueOf(parent.getItemAtPosition(i));
//                            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            startActivity(intent);
                        }
                    }
            );

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    tasks.remove(position);
                    ((BaseAdapter)adapter).notifyDataSetChanged();
                    return true;
                }
            });

//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//                intent.putExtra();
//            }

        }

}
