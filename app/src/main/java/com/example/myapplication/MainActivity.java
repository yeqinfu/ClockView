package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ClockView clockView;
    EditText et_month;
    EditText et_day;
    EditText et_hour;
    EditText et_minute;
    EditText et_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockView = findViewById(R.id.clock_view);
        et_month = findViewById(R.id.et_month);
        et_day = findViewById(R.id.et_day);
        et_hour = findViewById(R.id.et_hour);
        et_minute = findViewById(R.id.et_minute);
        et_second = findViewById(R.id.et_second);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        TextUtils.isEmpty(et_month.getText()) ||
                                TextUtils.isEmpty(et_day.getText()) ||
                                TextUtils.isEmpty(et_hour.getText()) ||
                                TextUtils.isEmpty(et_minute.getText()) ||
                                TextUtils.isEmpty(et_second.getText())

                ) {
                    Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                int month=Integer.parseInt(et_month.getText().toString());
                int day=Integer.parseInt(et_day.getText().toString());
                int hour=Integer.parseInt(et_hour.getText().toString());
                int minute=Integer.parseInt(et_minute.getText().toString());
                int second=Integer.parseInt(et_second.getText().toString());
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,second);
                clockView.setDateAndStart(calendar);

            }
        });
        findViewById(R.id.btn_line_fill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(MainActivity.this, LineFillActivity.class);
                startActivity(it);

            }
        });

    }

}
