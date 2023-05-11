package com.example.myapplication.ui.Entity.Item;

public class ItemLeave {
    private Integer id;
    private String username;
    private String name;
    private String time1;
    private String time2;
    private String leavetype;
    private String reason;
    private String shenpi_result;

    public ItemLeave(){}

    @Override
    public String toString() {
        return "ItemLeave{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", leavetype='" + leavetype + '\'' +
                ", reason='" + reason + '\'' +
                ", shenpi_result='" + shenpi_result + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
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

    public ItemLeave(Integer id, String username, String name, String time1, String time2, String leavetype, String reason, String shenpi_result) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.time1 = time1;
        this.time2 = time2;
        this.leavetype = leavetype;
        this.reason = reason;
        this.shenpi_result = shenpi_result;
    }
}
