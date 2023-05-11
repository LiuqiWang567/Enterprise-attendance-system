package com.example.myapplication.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RenderNode;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.ui.Adapter.DakaHistoryAdapter;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Daos.RuleDao;
import com.example.myapplication.ui.Entity.Item.ItemDaka;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.Rule;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Daka extends AppCompatActivity {

    private TextView tv_history;
    private ImageButton btn_daka1;
    private ImageButton btn_daka2;
    private TextView tv_buka;
    private TextView tv_location;
    private TextView tv_time2;
    private TextView tv_time1;
    private ImageButton btn_loaction;
    private TextView result1;
    private TextView result2;
    private ImageView icon1;
    private ImageView icon2;
    private Location location;
    private String address;
    private Rule sys;
    private Punch precord;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x11:
                    Rule rule = (Rule) msg.obj;
                    sys = rule;
                    tv_time1.setText("签到时间段："+rule.getTime1()+"~"+rule.getTime2());
                    tv_time2.setText("签退时间段："+rule.getTime3()+"~"+rule.getTime4());
                    break;
                case 0x12:
                    Punch punch = (Punch) msg.obj;
                    precord=punch;
                    if (punch.getTime1() != null&&!punch.getTime1().isEmpty()) {
                        result1.setText("已签到" + punch.getTime1());
                        btn_daka1.setEnabled(false);
                        icon1.setVisibility(View.VISIBLE);
                    }
                    if (punch.getTime2() != null&&!punch.getTime2().isEmpty()) {
                        result2.setText("已签退" + punch.getTime2());
                        btn_daka2.setEnabled(false);
                        icon2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 0x13:
                    String ss=(String)msg.obj;
                    Toast.makeText(Daka.this, ss, Toast.LENGTH_SHORT).show();
                    result1.setText("未签到");
                    result2.setText("未签退");
                    break;
                case 0x14:
                    Toast.makeText(Daka.this, "打卡成功", Toast.LENGTH_SHORT).show();
                    GetRecord();
                    break;
                case 0x15:
                    Toast.makeText(Daka.this, "打卡失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daka);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init();

        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Daka.this, PunchHistory.class);
                startActivity(intent1);
            }
        });
        tv_buka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Daka.this, Buka.class);
                startActivity(intent1);
            }
        });
        btn_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = GetLocation();
                //获得经纬度
              double  jing = location.getLongitude();
              double  wei = location.getLatitude();
              String address=Getaddress(wei,jing);
                tv_location.setText("已获得当前位置为，经纬度为："+jing+","+wei+"您在可打卡地理位置范围内");
            }
        });
        btn_daka1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否获得定位
                if (location == null) {
                    Toast.makeText(Daka.this, "请先获得定位", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (isEffectiveDateInqiandao()) {
                        Toqiandao();
                    }else{
                        Toast.makeText(Daka.this, "未到签到时间", Toast.LENGTH_SHORT).show();

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_daka2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否获得定位
                if(location == null) {
                    Toast.makeText(Daka.this, "请先获得定位", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (isEffectiveDateInqiantui()) {
                        if(precord.getTime2()==null){
                            Toqiantui_add();
                        }else {
                            Toqiantui_update();
                        }
                    }else{
                        Toast.makeText(Daka.this, "未到签退时间", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void init() {
        tv_history = (TextView) findViewById(R.id.tv_history);
        btn_daka1 = (ImageButton) findViewById(R.id.btn_daka1);
        btn_daka2 = (ImageButton) findViewById(R.id.btn_daka2);
        tv_buka = (TextView) findViewById(R.id.tv_buka);
        tv_location = (TextView) findViewById(R.id.tv_location);
        btn_loaction = (ImageButton) findViewById(R.id.btn_loaction);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        GetSysRule();
        GetRecord();
    }

    @SuppressLint("MissingPermission")
    public Location GetLocation() {
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = null;
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }

        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationProvider);
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        return location;
    }

    public String Getaddress(double latitude, double longitude){
        String lo=latitude+","+longitude;
        String urlString="http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&ak=3DT4PGkliEdcRCh3C3ayjCnSFrnEN6qS&output=json&coordtype=wgs84ll&location=+"
                + location+"language=zh-CN";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        address=res;

        return address;
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示

        }
    };

    //是否在签到时间范围内
    public boolean isEffectiveDateInqiandao() throws ParseException {
        //获得当前时间
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String systime = sdf.format(date1);
        String time1 = sys.getTime1();
        String time2 = sys.getTime2();
        //字符串转日期
        Date startTime = sdf.parse(time1);
        Date endTime = sdf.parse(time2);
        Date nowTime = sdf.parse(systime);

        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    //是否在签退时间范围内
    public boolean isEffectiveDateInqiantui() throws ParseException {
        //获得当前时间
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String systime = sdf.format(date1);
        String time1 = sys.getTime3();
        String time2 = sys.getTime4();
        //字符串转日期
        Date startTime = sdf.parse(time1);
        Date endTime = sdf.parse(time2);
        Date nowTime = sdf.parse(systime);

        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public void GetSysRule() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Rule rule = new Rule();
                rule = RuleDao.getInstance().findRule();
                Message message = handler.obtainMessage();
                if (rule != null && !rule.equals("")) {
                    message.what = 0x11;
                    message.obj = (Rule) rule;
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void GetRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                Punch punch = PunchDao.getInstance().record1(uid);
                Message message = handler.obtainMessage();
                if (punch!=null && !punch.equals("")) {
                    message.what = 0x12;
                    message.obj = (Punch) punch;
                } else {
                    message.what = 0x13;
                    message.obj = "今日未打卡";
                }
                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }

    public void Toqiandao() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
               Boolean flag= PunchDao.getInstance().add1(uid);
                Message message = handler.obtainMessage();
                if(flag){
                   message.what = 0x14;
               }else{
                    message.what=0x15;
                }
            }
        }).start();
    }

    public void Toqiantui_update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                Boolean flag= PunchDao.getInstance().add2(uid);
                Message message = handler.obtainMessage();

                if(flag){
                    message.what = 0x14;
                }else{
                    message.what=0x15;
                }

            }
        }).start();
    }
    public void Toqiantui_add() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                Boolean flag= PunchDao.getInstance().add3(uid);
                Message message = handler.obtainMessage();

                if(flag){
                    message.what = 0x14;
                }else{
                    message.what=0x15;
                }

            }
        }).start();
    }


}