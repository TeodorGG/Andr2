package com.example.lab2_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;

public class UpdateActivity extends AppCompatActivity {
    private Button b_back, b_add;
    private TimePicker picker;
    private EditText et_desk, et_task;
    private Model task;
    String dd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getWidjet();
        task = (Model) getIntent().getSerializableExtra("task");
        et_desk.setText(task.getDescription());
        et_task.setText(task.getTitle_task());
        dd = task.getDate();
        int hour = Integer.parseInt(task.getTime().substring(0,1));
        int minute = Integer.parseInt(task.getTime().substring(2));

        picker.setMinute(minute);
        picker.setHour(hour);

        listenerMethod();
        Log.d("daatr_add", task.toString());
    }

    private void listenerMethod() {
        b_back.setOnClickListener(v->{
            onBackPressed();
        });

        b_add.setOnClickListener(v->{
            String title = et_task.getText().toString();
            String desk = et_desk.getText().toString();

            if(title.isEmpty()){
                Toast.makeText(UpdateActivity.this, "Title is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if(desk.isEmpty()){
                Toast.makeText(UpdateActivity.this, "Desk is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Model task = new Model(
                    dd,
                    picker.getHour()+":"+picker.getMinute(),
                    title,
                    desk);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated_task", (Serializable) task);
            resultIntent.putExtra("position", getIntent().getIntExtra("position", 0));

            setResult(25, resultIntent);
            finish();

        });
    }

    private void getWidjet() {
        b_back = findViewById(R.id.b_back);
        b_add = findViewById(R.id.b_add);

        picker = findViewById(R.id.picker);

        et_desk = findViewById(R.id.et_desk);
        et_task = findViewById(R.id.et_task);

    }
}