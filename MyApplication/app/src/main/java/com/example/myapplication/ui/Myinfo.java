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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.LeaveHistoryAdapter;
import com.example.myapplication.ui.Daos.DepartmentDao;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Daos.UserDao;
import com.example.myapplication.ui.Entity.Department;
import com.example.myapplication.ui.Entity.Leave;
import com.example.myapplication.ui.Entity.User;

import java.util.Calendar;
import java.util.List;

public class Myinfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText et_birthday;
    private EditText et_username;
    private EditText et_password;
    private EditText et_gender;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_department;
    private TextView btn_update;
    private String department;
    private int departmentId;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    User user1=(User)msg.obj;
                    et_username.setText(user1.getUsername());
                    et_password.setText(user1.getPassword());
                    et_gender.setText(user1.getGender());
                    et_name.setText(user1.getName());
                    et_phone.setText(user1.getPhone());
                    et_department.setText(user1.getDepartment_name());
                    et_birthday.setText(user1.getBirthday());
                    break;
                case 0x13:
                    User Loginuser=(User) msg.obj;
                    saveLogin(Loginuser);
                    Toast.makeText(Myinfo.this, "更新成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x14:
                    String str2=(String) msg.obj;
                    Toast.makeText(Myinfo.this, str2, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        getSupportActionBar().setTitle("我的信息");

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_gender = (EditText) findViewById(R.id.et_gender);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_department = (EditText) findViewById(R.id.et_department);
        et_birthday = (EditText) findViewById(R.id.et_birthday);
        btn_update = (TextView) findViewById(R.id.btn_update);
        getInfo();
        et_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取Calendar实例
                //创建日期选择器
                DatePickerDialog dialog = new DatePickerDialog((Context) Myinfo.this, Myinfo.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MARCH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//窗口弹出
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toupdate();
            }
        });

    }


    public void getInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                String username = pref.getString("username","null");
               User user= UserDao.getInstance().findUserById(username);
                Message message = handler.obtainMessage();
                if(user!=null&&!user.equals("")){
                    message.what = 0x11;
                    message.obj = (User)user;
                }else{
                    message.what = 0x14;
                    message.obj = "无用户信息";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       int lmonth=month+1;
        String desc = String.format("%d-%s-%s", year, lmonth>=10?lmonth:"0"+lmonth, dayOfMonth>=10?dayOfMonth:"0"+dayOfMonth);
        Toast.makeText(Myinfo.this,"选择的日期为:"+desc, Toast.LENGTH_SHORT).show();
        et_birthday.setText(desc);
    }

    public void toupdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setUsername(et_username.getText().toString().trim());
                user.setName(et_name.getText().toString().trim());
                user.setBirthday(et_birthday.getText().toString().trim());
                user.setGender(et_gender.getText().toString().trim());
                user.setPhone(et_phone.getText().toString().trim());
                user.setPassword(et_password.getText().toString().trim());
                Message message = handler.obtainMessage();
                boolean res = UserDao.update(user);
                if (res==true) {
                    message.what = 0x13;
                    message.obj = user;
                } else {
                    message.what = 0x14;
                    message.obj = "更新失败";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }
    public void saveLogin(User Loginuser) {
        SharedPreferences sp = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
        //要想编辑它，首先需要调用edit方法，拿到Editor对象
        SharedPreferences.Editor edt = sp.edit();
        edt.putString("username", Loginuser.getUsername());
        edt.putString("name", Loginuser.getName());
        edt.putInt("department_id", Loginuser.getDepartment_id());
        edt.putString("department_name",Loginuser.getDepartment_name());
        edt.putInt("id", Loginuser.getId());
        edt.commit();
    }
}