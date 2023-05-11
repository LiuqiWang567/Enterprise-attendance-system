package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.BukaHistoryAdapter;
import com.example.myapplication.ui.Adapter.DakaHistoryAdapter;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Entity.Item.ItemBuka;
import com.example.myapplication.ui.Entity.Item.ItemDaka;
import com.example.myapplication.ui.Entity.Punch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BukaHistory extends AppCompatActivity {
    private EditText et_time1;
    private EditText et_time2;
    private Button btn_search;
    private TextView error;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x11:
                    List<Punch> bukalist = (List<Punch>) msg.obj;
                        BukaHistoryAdapter adapter = new BukaHistoryAdapter(BukaHistory.this, R.layout.item_buka_history, bukalist);
                        ListView listView = (ListView) findViewById(R.id.list_view22);
                        listView.setAdapter(adapter);
                    break;
                case 0x12:
                    String str = (String) msg.obj;
                    Intent intent=new Intent(BukaHistory.this,BukaHistory.class);
                    startActivity(intent);
                    Toast.makeText(BukaHistory.this, str, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_history);
        getSupportActionBar().setTitle("补卡历史记录");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
        int uid = pref.getInt("uid", 00);
        init();
        getpunchlist(uid);
        et_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取Calendar实例
                //创建日期选择器
                DatePickerDialog dialog = new DatePickerDialog((Context) BukaHistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int lmonth = month + 1;
                        String desc = String.format("%d-%s-%s", year, lmonth >= 10 ? lmonth : "0" + lmonth, dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                        Toast.makeText(BukaHistory.this, "选择的日期为:" + desc, Toast.LENGTH_SHORT).show();
                        et_time1.setText(desc);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MARCH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//窗口弹出
            }
        });
        et_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取Calendar实例
                //创建日期选择器
                DatePickerDialog dialog = new DatePickerDialog((Context) BukaHistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int lmonth = month + 1;
                        String desc = String.format("%d-%s-%s", year, lmonth >= 10 ? lmonth : "0" + lmonth, dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                        Toast.makeText(BukaHistory.this, "选择的日期为:" + desc, Toast.LENGTH_SHORT).show();
                        et_time2.setText(desc);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MARCH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//窗口弹出
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String t1=et_time1.getText().toString().trim();
               String t2=et_time2.getText().toString().trim();
               if(t1.equals("") || t2.equals("")){
                   Toast.makeText(BukaHistory.this, "请选择时间段", Toast.LENGTH_SHORT).show();
               }else {
                   tosearch();
               }
            }
        });

    }

    public void init() {
        et_time1 = (EditText) findViewById(R.id.et_time1);
        et_time2 = (EditText) findViewById(R.id.et_time2);
        btn_search = (Button) findViewById(R.id.btn_search);
        error = (TextView) findViewById(R.id.error);

    }

    public void getpunchlist(int uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                List<Punch> bukalist = PunchDao.getInstance().bukalist(uid);
                Message message = handler.obtainMessage();
                if (bukalist!= null && !bukalist.isEmpty()) {
                    message.what = 0x11;
                    message.obj = (List<Punch>) bukalist;

                } else {
                    message.what = 0x12;
                    message.obj = "未查询到记录";

                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void tosearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String time1 = et_time1.getText().toString().trim();
                String time2 = et_time2.getText().toString().trim();
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                List<Punch> bukalist = PunchDao.getInstance().bukalistBetweenTime(uid,time1,time2);
                Message message = handler.obtainMessage();
                if (bukalist != null && !bukalist.isEmpty()) {
                    message.what = 0x11;
                    message.obj = (List<Punch>) bukalist;
                } else {
                    message.what = 0x12;
                    message.obj = "未查询到记录";

                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();


    }
}