package com.example.myapplication.ui.Daos;

import android.util.Log;

import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.Report;
import com.example.myapplication.ui.Entity.User;
import com.example.myapplication.ui.tools.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static DBUtil dbUtil;
    private static UserDao userDao=null;
    private UserDao(){
        new Thread(){
            public void run(){
                //创建数据库对象
                if(dbUtil == null){
                    dbUtil=new DBUtil();
                }
            }
        }.start();
    }
    public static synchronized UserDao getInstance(){
        if(userDao == null){
            userDao = new UserDao();
        }
        return userDao;
    }
    //获取列表
    public static List<User> getList(int department_id) {
        department_id=1;
        //结果存放集合
        List<User> list = new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_user where role_id=1 and department_id="+department_id;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                User bean = new User();
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setEmail(rs.getString("email"));
                bean.setCreateTime(rs.getString("createTime"));
                bean.setBirthday(rs.getString("birthday"));
                bean.setPhone(rs.getString("phone"));
                bean.setGender(rs.getString("gender"));
                bean.setRole_id(rs.getInt("role_id"));
                bean.setDepartment_id(rs.getInt("department_id"));

                list.add(bean);
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
        return list;
    }
    //根据用户名和密码获取用户信息
    public User Login(String username,String password){
        User bean = new User();
        //MySQL 语句
        String sql = "select * from tb_user where username="+username+" and password="+password;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setEmail(rs.getString("email"));
                bean.setCreateTime(rs.getString("createTime"));
                bean.setBirthday(rs.getString("birthday"));
                bean.setPhone(rs.getString("phone"));
                bean.setGender(rs.getString("gender"));
                bean.setRole_id(rs.getInt("role_id"));
                bean.setDepartment_id(rs.getInt("department_id"));

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
        return bean;
    }
    //根据用户名获取用户信息
    public User findUserById(String username ){
        User bean = new User();
        //MySQL 语句
        String sql = "select * from tb_user,tb_department where did=department_id and username="+username;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));

                bean.setName(rs.getString("name"));
                bean.setGender(rs.getString("gender"));
                bean.setRole_id(rs.getInt("role_id"));
                bean.setDepartment_id(rs.getInt("department_id"));
                bean.setDepartment_name(rs.getString("department_name"));

                bean.setEmail(rs.getString("email"));
                bean.setCreateTime(rs.getString("createTime"));
                bean.setBirthday(rs.getString("birthday"));
                bean.setPhone(rs.getString("phone"));
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
        return bean;
    }
    //更新用户信息
    public static boolean update(User bean) {

        String sql = "update tb_user set name='" + bean.getName() + "',phone='" + bean.getPhone() +
                "',gender='" + bean.getGender() + "',birthday='" + bean.getBirthday() +
                "',password='" + bean.getPassword() +
                "' where id=" + bean.getId();
        Connection conn = DBUtil.getConn();
        Statement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("update->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }
    //新增用户
    public static boolean add(User bean) {
        String sql = "insert into tb_user(username,password,name,gender,birthday,department_id,role_id,phone,email,createTime)values('"
                + bean.getUsername() + "','" + bean.getPassword() + "','"
                + bean.getName() + "','" + bean.getGender() +"','"
                + bean.getBirthday() + "','" + bean.getDepartment_id() +"','"
                + bean.getRole_id() + "','" + bean.getPhone() +"','"
                + bean.getEmail() + "','" + bean.getCreateTime() +  "')";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("add->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }



}
