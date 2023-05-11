package com.example.myapplication.ui.Daos;

import android.util.Log;

import com.example.myapplication.ui.Entity.Rule;
import com.example.myapplication.ui.Entity.User;
import com.example.myapplication.ui.tools.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class RuleDao {
    private static DBUtil dbUtil;
    private static RuleDao ruleDao=null;
    private RuleDao(){
        new Thread(){
            public void run(){
                //创建数据库对象
                if(dbUtil == null){
                    dbUtil=new DBUtil();
                }
            }
        }.start();
    }
    public static synchronized RuleDao getInstance(){
        if(ruleDao == null){
            ruleDao = new RuleDao();
        }
        return ruleDao;
    }
    //获取系统时间
    public Rule findRule(){
        Rule rule=new Rule();
        //MySQL 语句
        String sql = "select * from tb_rule";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                int id=rs.getInt("id");
                Time time1=rs.getTime("qiandao_startTime");
                Time time2=rs.getTime("qiandao_endTime");
                Time time3=rs.getTime("qiantui_startTime");
                Time time4=rs.getTime("qiantui_endTime");
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                String time11=sdf.format(time1);
                String time22=sdf.format(time2);
                String time33=sdf.format(time3);
                String time44=sdf.format(time4);
                rule.setId(id);
                rule.setTime1(time11);
                rule.setTime2(time22);
                rule.setTime3(time33);
                rule.setTime4(time44);

            }
        } catch (Exception e) {
            Log.e("update->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return rule;
    }

}
