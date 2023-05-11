package com.example.myapplication.ui.Entity;

public class Leave {
    private int id;
    private int uid;
    private String time1;
    private String time2;
    private String reason;
    private String leave_type;
    private String result;
    private String createtime;

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", uid=" + uid +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", reason='" + reason + '\'' +
                ", leave_type='" + leave_type + '\'' +
                ", result='" + result + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }

    public Leave(){}
    public Leave(int id, int uid, String time1, String time2, String reason, String leave_type, String result, String createtime) {
        this.id = id;
        this.uid = uid;
        this.time1 = time1;
        this.time2 = time2;
        this.reason = reason;
        this.leave_type = leave_type;
        this.result = result;
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
