
package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.ui.Adapter.BukaHistoryAdapter;
import com.example.myapplication.ui.Adapter.BukaShenpiAdapter;
import com.example.myapplication.ui.Adapter.LeaveHistoryAdapter;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Entity.Item.ItemBuka;
import com.example.myapplication.ui.Entity.Item.ItemLeave;
import com.example.myapplication.ui.Entity.Leave;

import java.util.ArrayList;
import java.util.List;

public class Bukashenpi extends AppCompatActivity {

    private TextView tv_weishenpi;
    private TextView tv_yishenpi;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x11:
                    List<ItemBuka> list_buakshenpi = (List<ItemBuka>) msg.obj;
                    BukaShenpiAdapter adapter = new BukaShenpiAdapter(Bukashenpi.this, R.layout.item_buka_shenpi, list_buakshenpi);
                    ListView list_view_bukashenpi = (ListView) findViewById(R.id.list_view_bukashenpi);
                    list_view_bukashenpi.setAdapter(adapter);
                    list_view_bukashenpi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ItemBuka list = (ItemBuka) list_view_bukashenpi.getAdapter().getItem(position);
                            //int lid = list.getId();
                            int lid=1;
                            Todialog(lid);
                        }
                    });
                    break;
                case 0x22:
                    List<ItemBuka> list22 = (List<ItemBuka>) msg.obj;
                    BukaShenpiAdapter adapter22 = new BukaShenpiAdapter(Bukashenpi.this, R.layout.item_buka_shenpi, list22);
                    ListView listView22 = (ListView) findViewById(R.id.list_view_bukashenpi);
                    listView22.setAdapter(adapter22);
                    break;
                case 0x00:
                    String strr=(String)msg.obj;
                    Intent intent=new Intent(Bukashenpi.this,Bukashenpi.class);
                    startActivity(intent);
                    Toast.makeText(Bukashenpi.this, strr, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukashenpi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tv_yishenpi=  findViewById(R.id.tv_yishenpi);
        tv_weishenpi= findViewById(R.id.tv_weishenpi);

        getbukalist();
        tv_yishenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yishenpi();
            }
        });
        tv_weishenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getbukalist();
            }
        });
    }

    public void getbukalist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int did = pref.getInt("department_id", 00);
                List<ItemBuka> list = new ArrayList<>();
                list = PunchDao.getInstance().bukalist1(did);
                Message message = handler.obtainMessage();
                if (list != null&&!list.isEmpty()) {
                    message.what = 0x11;
                    message.obj = (List<ItemBuka>) list;
                } else {
                    message.what = 0x00;
                    message.obj = "无数据";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }
    public void yishenpi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int did = pref.getInt("department_id", 00);
                List<ItemBuka> list = new ArrayList<>();
                list = PunchDao.getInstance().yishenpi(did);
                Message message = handler.obtainMessage();
                if (list != null && !list.isEmpty()) {
                    message.what = 0x22;
                    message.obj = (List<ItemBuka>) list;
                } else {
                    message.what = 0x00;
                    message.obj = "无数据";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void Todialog(int lid) {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this)
                .setTitle("审批补卡")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //拒绝补卡审批
                        Torefuse(lid);
                        Toast.makeText(Bukashenpi.this, "操作成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Bukashenpi.this,Bukashenpi.class);
                        startActivity(intent);

                    }
                })
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //同意补卡审批
                        Toagree(lid);
                        Toast.makeText(Bukashenpi.this, "操作成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Bukashenpi.this,Bukashenpi.class);
                        startActivity(intent);


                    }
                })
                .create();
        dialog.show();
    }

    public void Torefuse(int lid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean r = PunchDao.getInstance().refuse(lid);
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

    public void Toagree(int lid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean r = PunchDao.getInstance().agree(lid);
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