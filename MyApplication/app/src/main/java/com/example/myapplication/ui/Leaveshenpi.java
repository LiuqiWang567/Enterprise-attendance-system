package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.LeaveHistoryAdapter;
import com.example.myapplication.ui.Adapter.LeaveShenpiAdapter;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Entity.Item.ItemLeave;
import com.example.myapplication.ui.Entity.Leave;

import java.util.List;

public class Leaveshenpi extends AppCompatActivity {

    private TextView tv_yishenpi;
    private TextView tv_weishenpi;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    List<ItemLeave> list_weishenpi = (List<ItemLeave>) msg.obj;
                        LeaveShenpiAdapter adapter=new LeaveShenpiAdapter(Leaveshenpi.this,R.layout.item_leave_shenpi,list_weishenpi);
                        ListView list_view_leaveshenpi=(ListView) findViewById(R.id.list_view_leaveshenpi);
                    list_view_leaveshenpi.setAdapter(adapter);
                    list_view_leaveshenpi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ItemLeave list= (ItemLeave) list_view_leaveshenpi.getAdapter().getItem(position);
                            int lid= list.getId();
                            Todialog(lid);

                        }
                    });
                    break;
                case 0x12:
                    List<ItemLeave> list_yishenpi = (List<ItemLeave>) msg.obj;
                        LeaveShenpiAdapter adapter2=new LeaveShenpiAdapter(Leaveshenpi.this,R.layout.item_leave_shenpi,list_yishenpi);
                        ListView list_view_yishenpi=(ListView) findViewById(R.id.list_view_leaveshenpi);
                    list_view_yishenpi.setAdapter(adapter2);
                    break;
                case 0x00:
                    String strr=(String)msg.obj;
                    Toast.makeText(Leaveshenpi.this, strr, Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(Leaveshenpi.this,Leaveshenpi.class);
                    startActivity(intent2);
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaveshenpi);
        tv_yishenpi=findViewById(R.id.tv_yishenpi);
        tv_weishenpi= findViewById(R.id.tv_weishenpi);
        getSupportActionBar().setTitle("请假审批");

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getleavelist();
        tv_yishenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_weishenpi.setTextColor(Color.BLACK);
                tv_yishenpi.setTextColor(Color.BLUE);
                toyishenpi();
            }
        });
        tv_weishenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yishenpi.setTextColor(Color.BLACK);

                tv_weishenpi.setTextColor(Color.BLUE);
                getleavelist();
            }
        });

    }
    public void getleavelist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid",00);
                List<ItemLeave>  leaveList= LeaveDao.getInstance().leavelist0(uid);
                Message message = handler.obtainMessage();
                if(leaveList!=null&&!leaveList.equals("")){
                    message.what = 0x11;
                    message.obj = (List<ItemLeave>)leaveList;
                }else{
                    message.what = 0x00;
                    message.obj = (String)"该用户无请假记录";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }
    public void toyishenpi(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid",00);
                List<ItemLeave>  leaveList= LeaveDao.getInstance().leavelist2(uid);
                Message message = handler.obtainMessage();
                if(leaveList!=null&&!leaveList.isEmpty()){
                    message.what = 0x12;
                    message.obj = (List<ItemLeave>)leaveList;
                }else{
                    message.what = 0x00;
                    message.obj = (String)"未查询到数据";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void Todialog(int lid){
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this)
                .setTitle("审批请假")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //拒绝补卡审批
                       Torefuse(lid);
                        Intent intent2=new Intent(Leaveshenpi.this,Leaveshenpi.class);
                        startActivity(intent2);
                        Toast.makeText(Leaveshenpi.this, "操作成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //同意补卡审批
                       Toagree(lid);
                        Intent intent2=new Intent(Leaveshenpi.this,Leaveshenpi.class);
                        startActivity(intent2);
                        Toast.makeText(Leaveshenpi.this, "操作成功", Toast.LENGTH_SHORT).show();

                    }
                })
                .create();
        dialog.show();
    }
    public void Torefuse(int lid){
        new Thread(new Runnable() {
            @Override
            public void run() {
               boolean r= LeaveDao.getInstance().refuse(lid);
                Message message = handler.obtainMessage();
                if (r) {
                    message.what = 0x00;
                    message.obj = "操作成功";
                } else {
                    message.what = 0x00;
                    message.obj = "操作失败";
                }
            }
        }).start();
    }
    public void Toagree(int lid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean r= LeaveDao.getInstance().agree(lid);
                Message message = handler.obtainMessage();
                if (r) {
                    message.what = 0x00;
                    message.obj = "操作成功";
                } else {
                    message.what = 0x00;
                    message.obj = "操作失败";
                }
            }
        }).start();
    }

}
