package com.example.lab2_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private Button b_back, b_add;
    private TimePicker picker;
    private EditText et_desk, et_task;
    String data_mili;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWidjet();
        data_mili = getIntent().getStringExtra("data_mili");

        listenerMethod();
        Log.d("daatr_add", data_mili);

    }

    private void listenerMethod() {
        b_back.setOnClickListener(v->{
            onBackPressed();

        });

        b_add.setOnClickListener(v->{
            String title = et_task.getText().toString();
            String desk = et_desk.getText().toString();

            if(title.isEmpty()){
                Toast.makeText(AddActivity.this, "Title is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if(desk.isEmpty()){
                Toast.makeText(AddActivity.this, "Desk is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Model task = new Model(data_mili,
                    picker.getHour()+":"+picker.getMinute(),
                        title,desk);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_task", (Serializable) task);
            setResult(24, resultIntent);
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