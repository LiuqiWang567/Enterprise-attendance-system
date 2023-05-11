package com.example.myapplication.ui.Entity;

public class Punch {
    private int id;
    private int uid;
    private String time1;
    private String time2;
    private int punchcount;
    private String punch_result;
    private String reason;
    private String shenpi_result;
    private String location;
    private String createtime;
    public Punch(){}

    @Override
    public String toString() {
        return "Punch{" +
                "id=" + id +
                ", uid=" + uid +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", punchcount=" + punchcount +
                ", punch_result='" + punch_result + '\'' +
                ", reason='" + reason + '\'' +
                ", shenpi_result='" + shenpi_result + '\'' +
                ", location='" + location + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
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

    public int getPunchcount() {
        return punchcount;
    }

    public void setPunchcount(int punchcount) {
        this.punchcount = punchcount;
    }

    public String getPunch_result() {
        return punch_result;
    }

    public void setPunch_result(String punch_result) {
        this.punch_result = punch_result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getShenpi_result() {
        return shenpi_result;
    }

    public void setShenpi_result(String shenpi_result) {
        this.shenpi_result = shenpi_result;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Punch(int id, int uid, String time1, String time2, int punchcount, String punch_result, String reason, String shenpi_result, String location, String createtime) {
        this.id = id;
        this.uid = uid;
        this.time1 = time1;
        this.time2 = time2;
        this.punchcount = punchcount;
        this.punch_result = punch_result;
        this.reason = reason;
        this.shenpi_result = shenpi_result;
        this.location = location;
        this.createtime = createtime;
    }
}
