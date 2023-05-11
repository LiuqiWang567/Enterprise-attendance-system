package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Daos.DepartmentDao;
import com.example.myapplication.ui.Daos.LeaveDao;
import com.example.myapplication.ui.Daos.PunchDao;
import com.example.myapplication.ui.Daos.UserDao;
import com.example.myapplication.ui.Entity.Report;
import com.example.myapplication.ui.Entity.User;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    private TextView tv_name;
    private TextView tv_department;
    private TextView tv_time;
    private TextView tv_punchday;
    private TextView tv_leaveday;
    private TextView tv_rate;
    private EditText et_selectTime;
    private TextView search;
    private String selectime;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    Report rep=(Report) msg.obj;
                    tv_name.setText(rep.getUsername());
                    tv_department.setText(rep.getDepartment());
                    tv_time.setText(rep.getMonth());
                    tv_punchday.setText((rep.getPunchdayl()!=null&&!rep.getPunchdayl().isEmpty()?rep.getPunchdayl():"0")+"天");
                    tv_leaveday.setText((rep.getLeaveday()!=null&&!rep.getLeaveday().isEmpty()?rep.getLeaveday():"0")+"天");
                    tv_rate.setText(rep.getRate()!=null&&!rep.getRate().isEmpty()?rep.getRate():"0");
                    break;
                case 0x14:
                    String str2=(String) msg.obj;
                    Toast.makeText(ReportActivity.this, str2, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setTitle("报表模块");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tv_name = findViewById(R.id.tv_name);
        tv_department = findViewById(R.id.tv_department);
        tv_time = findViewById(R.id.tv_time);
        tv_punchday = findViewById(R.id.tv_punchday);
        tv_leaveday = findViewById(R.id.tv_leaveday);
        search = findViewById(R.id.search);
        et_selectTime=findViewById(R.id.et_selectTime);
        tv_rate=findViewById(R.id.tv_rate);

        //获得当前时间
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        selectime = sdf.format(date1);
        et_selectTime.setText(selectime);


        et_selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取Calendar实例
                //创建日期选择器
                DatePickerDialog dialog = new DatePickerDialog((Context) ReportActivity.this, (DatePickerDialog.OnDateSetListener) ReportActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MARCH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//窗口弹出
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tosearch();
            }
        });

        tosearch();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int lmonth=month+1;
        String desc = String.format("%d-%s", year, lmonth>=10?lmonth:"0"+lmonth);
        Toast.makeText(ReportActivity.this,"选择的日期为:"+desc, Toast.LENGTH_SHORT).show();
        et_selectTime.setText(desc);
    }


   public void tosearch(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String time=et_selectTime.getText().toString().trim();
                if(et_selectTime==null) {
                    //获得当前时间
                    Date date1 = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    time = sdf.format(date1);
                }
                Report rep=new Report();
                SharedPreferences pref = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
                int uid = pref.getInt("uid", 00);
                String name=pref.getString("name","");
                int did=pref.getInt("department_id",0);
                rep.setUsername(name);
               String pday= String.valueOf(PunchDao.getInstance().punchdays(uid,time));
               String lday= String.valueOf(LeaveDao.getInstance().leaveday(uid,time));
               rep.setPunchdayl(pday);
               rep.setMonth(time);
               rep.setLeaveday(lday);
               String rate="";
               double rp=PunchDao.getInstance().punchdays(uid,time)*1.0;
                double mon= 0;
                try {
                    mon = getdayofmonth(time)*1.0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                double res=rp/mon;
                DecimalFormat df1=new DecimalFormat("##0.00%");
                rate=df1.format(res);
                rep.setRate(rate);
               rep.setDepartment(DepartmentDao.getInstance().finddepByid(did).getName());
                Message message = handler.obtainMessage();
                    message.what = 0x11;
                    message.obj = rep;

                // 发消息通知主线程更新UI
                handler.sendMessage(message);
            }
        }).start();
    }
    //球当前月份有多少天
    public int getdayofmonth(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return getDaysOfMonth(sdf.parse(time));

    }
    public  int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}