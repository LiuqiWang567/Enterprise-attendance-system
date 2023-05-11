package com.example.myapplication.ui.Daos;

import android.util.Log;

import com.example.myapplication.ui.Entity.Item.ItemBuka;
import com.example.myapplication.ui.Entity.Item.ItemLeave;
import com.example.myapplication.ui.Entity.Leave;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.tools.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeaveDao {
    private static DBUtil dbUtil;
    private static LeaveDao leaveDao=null;
    private LeaveDao(){
        new Thread(){
            public void run(){
                //创建数据库对象
                if(dbUtil == null){
                    dbUtil=new DBUtil();
                }
            }
        }.start();
    }
    public static synchronized LeaveDao getInstance(){
        if(leaveDao == null){
            leaveDao = new LeaveDao();
        }
        return leaveDao;
    }

    //单个用户查询自己的所有请假记录（历史记录）
    public List<Leave> leavelist(int uid){
        List<Leave> leavelist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_leavelist where uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Leave bean = new Leave();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setLeave_type(rs.getString("leave_type"));
                bean.setResult(rs.getString("result"));
                bean.setReason(rs.getString("reason"));
                bean.setCreatetime(rs.getString("createtime"));
                leavelist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return leavelist;
    }
    //单个用户查询自己的所有请假记录（历史记录）
    public List<Leave> leavelistBetweenTime(int uid,String time1,String time2){
        List<Leave> leavelist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_leavelist where createtime between '"+time1+"' and '"+time2+"' and uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Leave bean = new Leave();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setLeave_type(rs.getString("leave_type"));
                bean.setResult(rs.getString("result"));
                bean.setReason(rs.getString("reason"));
                bean.setCreatetime(rs.getString("createtime"));
                leavelist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return leavelist;
    }
    //添加请假记录
    public boolean add(Leave bean) {
        String sql = "insert into tb_leavelist(uid,time1,time2,leave_type,reason,result,createtime)values('"
                + bean.getUid() + "','" + bean.getTime1()
                + "','" + bean.getTime2()
                + "','" + bean.getLeave_type()
                +"','" + bean.getReason()
                +"','" + bean.getResult()
                + "','" + bean.getCreatetime() +  "')";
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

    //请假记录list(全部)
    public List<ItemLeave> leavelist0(int did){
        List<ItemLeave> punchlist=new ArrayList<>();
        String shenpi_result="未审批";
        //MySQL 语句
        String sql = "select p.id as id,username,name,time1,time2,reason,leave_type,p.createtime,result " +
                "from tb_leavelist p,tb_user u where u.id=p.uid and result='未审批' and department_id="+did;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemLeave bean = new ItemLeave();
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setTime1(rs.getString("time1"));
                bean.setReason(rs.getString("reason"));
                bean.setTime2(rs.getString("time2"));
                bean.setLeavetype(rs.getString("leave_type"));
                bean.setShenpi_result(rs.getString("result"));
                punchlist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return punchlist;
    }
    //请假审批list(待处理)
    public List<ItemLeave> leavelist1(int did){
        List<ItemLeave> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select p.id as id, username,name,time1,time2,reason,leave_type,p.createtime,result" +
                "from tb_leavelist p,tb_user u where u.id=p.uid and result='未审批' and department_id="+did;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemLeave bean = new ItemLeave();
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setTime1(rs.getString("time1"));
                bean.setReason(rs.getString("reason"));
                bean.setTime2(rs.getString("time2"));
                bean.setLeavetype(rs.getString("leave_type"));
                bean.setShenpi_result(rs.getString("result"));
                punchlist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return punchlist;
    }
    //请假审批list（已处理）
    public List<ItemLeave> leavelist2(int did){
        List<ItemLeave> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select p.id as id,username,name,time1,time2,reason,leave_type,p.createtime,result " +
                "from tb_leavelist p,tb_user u where u.id=p.uid and result is not null and department_id="+did+" and result!='未审批'";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemLeave bean = new ItemLeave();
                bean.setId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setTime1(rs.getString("time1"));
                bean.setReason(rs.getString("reason"));
                bean.setTime2(rs.getString("time2"));
                bean.setLeavetype(rs.getString("leave_type"));
                bean.setShenpi_result(rs.getString("result"));
                punchlist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return punchlist;
    }

    //领导审批（同意）
    public boolean agree(int leave_id) {
        Leave bean=new Leave();
        bean.setId(leave_id);
        bean.setResult("同意");
        String sql = "update tb_leavelist set "
                + "result='同意' where id=" + bean.getId();
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

    public boolean refuse(int leave_id) {
        Leave bean=new Leave();
        bean.setId(leave_id);
        String sql = "update tb_punchlist set "
                + "result='拒绝' where id=" + bean.getId();
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
    public int leaveday(int uid,String time) {
        int leaveday=0;
        String sql = "select p.id,sum(TIMESTAMPDIFF(Day,STR_TO_DATE(time1,\"%Y-%m-%d\"),STR_TO_DATE(time2,\"%Y-%m-%d\"))) as num\n" +
                "from tb_leavelist p,tb_user u where p.id=u.id and Left(time1,7)='"+time+"' and uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {

               leaveday=rs.getInt("num");
            }
        } catch (Exception e) {
            Log.e("select leavelist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return leaveday;
    }



}
