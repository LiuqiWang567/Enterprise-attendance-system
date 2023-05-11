package com.example.myapplication.ui.Daos;

import android.util.Log;

import com.example.myapplication.ui.Entity.Department;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.tools.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private static DBUtil dbUtil;
    private static DepartmentDao departmentDao=null;
    private DepartmentDao(){
        new Thread(){
            public void run(){
                //创建数据库对象
                if(dbUtil == null){
                    dbUtil=new DBUtil();
                }
            }
        }.start();
    }
    public static synchronized DepartmentDao getInstance(){
        if(departmentDao == null){
            departmentDao = new DepartmentDao();
        }
        return departmentDao;
    }
    //
    public Department finddepByid(int did){
        Department bean=new Department();
        //MySQL 语句
        String sql = "select * from tb_department where did="+did;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                bean.setId(rs.getInt("did"));
                bean.setName(rs.getString("department_name"));

            }
        } catch (Exception e) {
            Log.e("select punchlist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return bean;
}
}
