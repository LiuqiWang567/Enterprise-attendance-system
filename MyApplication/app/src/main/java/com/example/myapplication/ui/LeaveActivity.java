package com.example.myapplication.ui;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Entity.Leave;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.Rule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeaveActivity extends AppCompatActivity {
    private TextView btn_history;
    private EditText et_leavetype;
    private EditText et_time1;
    private EditText et_time2;
    private EditText et_reason;
    private TextView btn_submit;
    private String leavetype;
    private String time1;
    private String time2;
    private String reason;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x11:
                    String ss=(String)msg.obj;
                    Toast.makeText(LeaveActivity.this, ss, Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(LeaveActivity.this, LeaveHistory.class);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        getSupportActionBar().setTitle("请假表单填写");
        init();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leave leave = new Leave();
                leavetype = et_leavetype.getText().toString().trim();
                time1 = et_time1.getText().toString().trim();
                time2 = et_time2.getText().toString().trim();
                reason = et_reason.getText().toString().trim();
                leave.setReason(reason);
                leave.setLeave_type(leavetype);
                leave.setTime1(time1);
                leave.setTime2(time2);
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                leave.setCreatetime(df.format(day));
                if (!leavetype.equals("") && !time1.equals("") && !time2.equals("") && !reason.equals("")) {
                    tosubmit(leave);

                } else {
                    Toast.makeText(LeaveActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LeaveActivity.this, LeaveHistory.class);
                startActivity(intent2);
            }
        });

    }

    public void init() {
        btn_history = (TextView) findViewById(R.id.btn_history);
        et_leavetype = (EditText) findViewById(R.id.et_leavetype);
        et_time1 = (EditText) findViewById(R.id.et_time1);
        et_time2 = (EditText) findViewById(R.id.et_time2);
        et_reason = (EditText) findViewById(R.id.et_reason);
        btn_submit = (TextView) findViewById(R.id.btn_submit);

    }

    public void tosubmit(Leave leave) {
        final Boolean[] res = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = LeaveDao.getInstance().add(leave);
                Message message = handler.obtainMessage();
                if (res==true) {
                    message.what = 0x11;
                    message.obj = "提交成功";
                }else{
                    message.what = 0x11;
                    message.obj = "提交失败";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }
}