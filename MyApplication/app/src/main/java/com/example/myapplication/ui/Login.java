package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Daos.UserDao;
import com.example.myapplication.ui.Entity.Item.ItemDaka;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.User;

import java.util.List;

public class Login extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private TextView btn_login;
    String username;
    String password;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x20:
                  User  Loginuser = (User) msg.obj;
                    saveLogin(Loginuser);
                    int roleId=Loginuser.getRole_id();
                    if(roleId==1){
                        Toast.makeText(Login.this, Loginuser.getName()+"登录成功", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Login.this,Index2.class);
                        startActivity(intent);
                    }else if(roleId==2){
                        Toast.makeText(Login.this, Loginuser.getName()+"登录成功", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Login.this,Index1.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "该用户为管理员，请登录后台系统", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x21:
                  String  strr = (String) msg.obj;
                    Toast.makeText(Login.this, strr, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (TextView) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if(username==null || username.equals("")) {
                    Toast.makeText(Login.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }else {
                    Login();
                }
            }
        });
    }


    public void saveLogin(User Loginuser) {
        SharedPreferences sp = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
        //要想编辑它，首先需要调用edit方法，拿到Editor对象
        SharedPreferences.Editor edt = sp.edit();
        edt.putString("username", Loginuser.getUsername());
        edt.putString("name", Loginuser.getName());
        edt.putInt("department_id", Loginuser.getDepartment_id());
        edt.putInt("id", Loginuser.getId());
        edt.commit();
    }
    public void Login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                User user=UserDao.getInstance().Login(username,password);
                Message message = handler.obtainMessage();
                if (user != null) {
                    message.what = 0x20;
                    message.obj = user;
                } else {
                    message.what = 0x21;
                    message.obj = "用户名或密码错误";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);

            }
        }).start();
    }

}