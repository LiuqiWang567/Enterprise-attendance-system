package com.example.myapplication.ui.Daos;

import android.util.Log;

import com.example.myapplication.ui.Entity.Item.ItemBuka;
import com.example.myapplication.ui.Entity.Item.ItemDaka;
import com.example.myapplication.ui.Entity.Punch;
import com.example.myapplication.ui.Entity.User;
import com.example.myapplication.ui.tools.DBUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PunchDao {
    private static DBUtil dbUtil;
    private static PunchDao punchDao=null;
    private PunchDao(){
        new Thread(){
            public void run(){
                //创建数据库对象
                if(dbUtil == null){
                    dbUtil=new DBUtil();
                }
            }
        }.start();
    }
    public static synchronized PunchDao getInstance(){
        if(punchDao == null){
            punchDao = new PunchDao();
        }
        return punchDao;
    }

    //领导查询部门下所有员工的打卡信息
    public List<Punch> punchlistBydepid(int did){
        List<Punch> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select p.id as id,p.uid as uid,p.time1,p.time2,p.punchcount,p.punch_result,p.reason,p.shenpi_result,p.createTime from tb_punchlist p,tb_user u where u.id=p.uid and u.department_id="+did;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Punch bean = new Punch();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setPunchcount(rs.getInt("punchcount"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                bean.setCreatetime(rs.getString("createTime"));
                punchlist.add(bean);
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
        return punchlist;
    }
    //根据用户名查询所有打卡信息
    public List<Punch> punchlistByusername(int uid){
        List<Punch> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_punchlist where uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Punch bean = new Punch();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setPunchcount(rs.getInt("punchcount"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                bean.setCreatetime(rs.getString("createTime"));
                punchlist.add(bean);
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
        return punchlist;
    }
    //个人打卡记录
    public List<ItemDaka> PunchHistory(int uid){
        List<ItemDaka> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_punchlist where uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemDaka bean = new ItemDaka();
                bean.setId(rs.getInt("id"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setResult(rs.getString("punch_result"));
                bean.setLocation(rs.getString("location"));
                punchlist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select punchlist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        return punchlist;
    }
    //个人打卡记录
    public List<ItemDaka> PunchHistoryBetweenTime(int uid,String time1,String time2){
        List<ItemDaka> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_punchlist  where createTime between '"+time1+"' and '"+time2+"' and uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemDaka bean = new ItemDaka();
                bean.setId(rs.getInt("id"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setResult(rs.getString("punch_result"));
                bean.setLocation(rs.getString("location"));
                punchlist.add(bean);
            }
        } catch (Exception e) {
            Log.e("select punchlist->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        return punchlist;
    }

    //查询是否有签到记录
    public Punch record1(int uid){
        //获得当前系统时间
        Date day=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String createtime=sdf.format(day);

        Punch punch=new Punch();
        //MySQL 语句
        String sql = "select * from tb_punchlist where uid="+uid+" and createTime='"+createtime+"'";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                punch.setTime1(rs.getString("time1"));
                punch.setTime2(rs.getString("time2"));
            }
        } catch (Exception e) {
            Log.e("select record->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, state, conn);
        }
        if (a > 0) {
            f = true;
        }
        return punch;
    }
    //新增打卡记录(签到)
    public boolean add1(int uid) {
        Punch bean=new Punch();
        bean.setUid(uid);
        //获得当前系统时间
        java.util.Date day=new Date();
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String createtime=sdf.format(day);
        String time=sdf2.format(day);

        bean.setTime1(time);
        bean.setCreatetime(createtime);
        bean.setPunchcount(1);
        bean.setPunch_result("已签到(未签退)");

        String sql = "insert into tb_punchlist(uid,time1,punchcount,punch_result,createTime)values('"
                + bean.getUid() + "','"
                + bean.getTime1() + "','"
                + bean.getPunchcount() +"','"
                + bean.getPunch_result() + "','"
                + bean.getCreatetime() +  "')";
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
    //打卡记录(签退（已签到）)
    public boolean add2(int uid) {
        Punch bean=new Punch();
        bean.setUid(uid);
        //获得当前系统时间
        java.util.Date day=new Date();
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf2.format(day);

        bean.setTime2(time);
        bean.setPunchcount(2);
        bean.setPunch_result("打卡成功");

        String sql = "update tb_punch set " +
                "time2='" + bean.getTime2() +
                "', punchcount='" + bean.getPunchcount() +
                "', punch_result='" + bean.getPunch_result() +
                "' where id='" + bean.getId() + "'";
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
    //新增打卡记录(签退（未签到）)
    public boolean add3(int uid) {
        Punch bean=new Punch();
        bean.setUid(uid);
        //获得当前系统时间
        java.util.Date day=new Date();
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String createtime=sdf.format(day);
        String time=sdf2.format(day);

        bean.setTime2(time);
        bean.setCreatetime(createtime);
        bean.setPunchcount(1);
        bean.setPunch_result("已签退(未签到)");

        String sql = "insert into tb_punchlist(uid,time2,punchcount,punch_result,createTime)values('"
                + bean.getUid() + "','"
                + bean.getTime2() + "','"
                + bean.getPunchcount() +"','"
                + bean.getPunch_result() + "','"
                + bean.getCreatetime() +  "')";
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

    //新增补卡记录
    public boolean buka(Punch bean) {
        bean.setShenpi_result("未审批");
        String sql = "update tb_punchlist set " +
                "reason='" + bean.getReason() +
                "',shenpi_result='未审批'"+
                " where id=" + bean.getId();
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
    //查询用户补卡信息（历史信息）
    public List<Punch> bukalist(int uid){
        List<Punch> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_punchlist where reason is not null and uid="+uid+" ORDER BY createTime";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Punch bean = new Punch();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setPunchcount(rs.getInt("punchcount"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                bean.setCreatetime(rs.getString("createTime"));
                punchlist.add(bean);
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
        return punchlist;
    }
    //查询用户补卡信息（历史信息）
    public List<Punch> bukalistBetweenTime(int uid,String time1,String time2){
        List<Punch> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select * from tb_punchlist where reason is not null and createTime between '"+time1+"' and '"+time2+"' and uid="+uid+" ORDER BY createTime";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Punch bean = new Punch();
                bean.setId(rs.getInt("id"));
                bean.setUid(rs.getInt("uid"));
                bean.setTime1(rs.getString("time1"));
                bean.setTime2(rs.getString("time2"));
                bean.setPunchcount(rs.getInt("punchcount"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                bean.setCreatetime(rs.getString("createTime"));
                punchlist.add(bean);
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
        return punchlist;
    }


    //补卡审批list(待处理)
    public List<ItemBuka> bukalist1(int did){
        List<ItemBuka> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select username,name,punch_result,reason,p.createTime,shenpi_result " +
                "from tb_punchlist p,tb_user u where u.id=p.uid and department_id="+did+" and shenpi_result='未审批' ORDER BY createTime";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemBuka bean = new ItemBuka();
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setApplaydate(rs.getString("createTime"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                punchlist.add(bean);
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
        return punchlist;
    }
    //补卡审批list（已处理）
    public List<ItemBuka> yishenpi(int did){
        List<ItemBuka> punchlist=new ArrayList<>();
        //MySQL 语句
        String sql = "select username,name,punch_result,reason,p.createTime as createTime,shenpi_result " +
                "from tb_punchlist p,tb_user u where u.id=p.uid and shenpi_result != '未审批' and shenpi_result is not null and department_id="+did+" ORDER BY createTime";
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                ItemBuka bean = new ItemBuka();
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                bean.setPunch_result(rs.getString("punch_result"));
                bean.setReason(rs.getString("reason"));
                bean.setApplaydate(rs.getString("createTime"));
                bean.setShenpi_result(rs.getString("shenpi_result"));
                punchlist.add(bean);
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
        return punchlist;
    }
    //领导审批（同意）
    public boolean agree(int pid) {

        String sql = "update tb_punchlist set "
                + "shenpi_result='同意',punch_result='打卡成功' where id=" + pid;
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
    //领导审批（同意）
    public boolean refuse(int pid) {

        String sql = "update tb_punchlist set "
                + "shenpi_result='拒绝' where id=" + pid;
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

    //查询用户指定月份打卡天数
    public int punchdays(int uid,String time){
        int day=0;
        //MySQL 语句
        String sql = "select count(p.id) as num from tb_punchlist p,tb_user u where p.id=u.id and punch_result='打卡成功' and Left(p.createTime,7)='"+time+"' and uid="+uid;
        Connection conn = DBUtil.getConn();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = (Statement) conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
               day=rs.getInt("num");
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
        return day;
    }


}
