package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class Home_leader extends AppCompatActivity {

    private TextView daka;
    private TextView leave;
    private TextView shenpi;
    private TextView baobiao;
    private TextView loginuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_leader);

        init();
    }
    public void init(){
        daka=(TextView)findViewById(R.id.tv_daka);
        leave=(TextView)findViewById(R.id.tv_leave);
        shenpi=(TextView)findViewById(R.id.tv_report);
        baobiao=(TextView)findViewById(R.id.tv_shenpi);
        loginuser=(TextView)findViewById(R.id.tv_username);
        daka.setOnClickListener(l);
        leave.setOnClickListener(l);
        shenpi.setOnClickListener(l);
        baobiao.setOnClickListener(l);

        SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
        String name = pref.getString("name","null");
        loginuser.setText(name);

    }
    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_daka:
                    Intent intent=new Intent(Home_leader.this,Daka.class);
                    startActivity(intent);
                    break;
                case R.id.tv_leave:
                    Intent intent2=new Intent(Home_leader.this,LeaveActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.tv_report:
                    Intent intent4=new Intent(Home_leader.this,ReportActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.tv_shenpi:
                    Intent intent5=new Intent(Home_leader.this,MainShenpi.class);
                    startActivity(intent5);
                    break;
                default:
                    break;

            }
        }
    };

}