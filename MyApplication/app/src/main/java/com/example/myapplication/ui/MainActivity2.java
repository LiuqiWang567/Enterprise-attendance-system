package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Daos.UserDao;
import com.example.myapplication.ui.Entity.Leave;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.User;
import com.example.myapplication.ui.tools.DBUtils;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private UserDao userDao;

    private Button btn_get_data;
    private TextView tv_data;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    tv_data.setText(s);
                    break;
                case 0x12:
                    String ss = (String) msg.obj;
                    tv_data.setText(ss);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        userDao=UserDao.getInstance();
        // 控件的初始化
        btn_get_data = findViewById(R.id.btn_get_data);
        tv_data = findViewById(R.id.tv_data);

        setListener();
    }
    /**
     * 设置监听
     */
    private void setListener() {

        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {



                        int did=1;
                        List<Punch> list= PunchDao.getInstance().punchlistBydepid(did);
                        //String username = null;String password = null;
                        //User user=UserDao.getInstance().Login(username,password);

                        Message message = handler.obtainMessage();
                        if(list != null){
                            message.what = 0x12;
                            message.obj = list.toString();
                        }else {
                            message.what = 0x11;
                            message.obj = "查询结果为空";
                        }
                        // 发消息通知主线程更新UI
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        /**
        // 按钮点击事件
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 创建一个线程来连接数据库并获取数据库中对应表的数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                        int dep = 0;
                        List<User> list=UserDao.getList(dep);

                        HashMap<String, Object> map = DBUtils.getInfoByName("2110120");
                        Message message = handler.obtainMessage();
                        if(list != null){
                            message.what = 0x12;
                            message.obj = list.toString();
                        }else {
                            message.what = 0x11;
                            message.obj = "查询结果为空";
                        }
                        // 发消息通知主线程更新UI
                        handler.sendMessage(message);
                    }
                }).start();

            }
        });
**/
    }

}