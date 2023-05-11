package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.LeaveHistoryAdapter;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Entity.Leave;
import com.example.myapplication.ui.Entity.Punch;

import java.util.List;
import java.util.Locale;

public class Buka extends AppCompatActivity {

    private EditText et_id;
    private EditText et_reason;
    private TextView btn_submit;
    private TextView btn_history;
   private int id;
  private   String reason;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                   String s=(String)msg.obj;
                    Toast.makeText(Buka.this, s, Toast.LENGTH_SHORT).show();
                    break;
                case 0x12:
                    String ss=(String)msg.obj;
                    Toast.makeText(Buka.this, ss, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka);
        et_id=(EditText) findViewById(R.id.et_id);
        et_reason=(EditText) findViewById(R.id.et_reason);
        btn_submit= (TextView) findViewById(R.id.btn_submit);
        btn_history= (TextView) findViewById(R.id.btn_history);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String id_= et_id.getText().toString().trim();
                reason=et_reason.getText().toString().trim();
                if(id_==null||(reason==null)) {
                    Toast.makeText(Buka.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    tobuka();
                }
            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tohistory();
            }
        });

    }
    public void tobuka(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                id= Integer.parseInt(et_id.getText().toString().trim());
                reason=et_reason.getText().toString().trim();
                Punch bean=new Punch();
                bean.setId(id);
                bean.setReason(reason);
             Boolean r= PunchDao.getInstance().buka(bean);
                Message message = handler.obtainMessage();
                if(r){
                    message.what = 0x11;
                    message.obj ="提交成功";
                }else{
                    message.what = 0x12;
                    message.obj ="提交失败，考勤ID号不存在";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void tohistory(){
        Intent intent1=new Intent(Buka.this, BukaHistory.class);
        startActivity(intent1);
    }
}