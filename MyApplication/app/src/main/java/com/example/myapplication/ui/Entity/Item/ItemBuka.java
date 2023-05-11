package com.example.myapplication.ui.Entity.Item;

public class ItemBuka {
    private Integer id;
    private String username;
    private String name;
    private String punch_result;
    private String reason;
    private String applaydate;
    private String shenpi_result;

    public ItemBuka(){}
    public ItemBuka(Integer id, String username, String name, String punch_result, String reason, String applaydate, String shenpi_result) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.punch_result = punch_result;
        this.reason = reason;
        this.applaydate = applaydate;
        this.shenpi_result = shenpi_result;
    }

    @Override
    public String toString() {
        return "ItemBuka{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", punch_result='" + punch_result + '\'' +
                ", reason='" + reason + '\'' +
                ", applaydate='" + applaydate + '\'' +
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

    public String getApplaydate() {
        return applaydate;
    }

    public void setApplaydate(String applaydate) {
        this.applaydate = applaydate;
    }

    public String getShenpi_result() {
        return shenpi_result;
    }

    public void setShenpi_result(String shenpi_result) {
        this.shenpi_result = shenpi_result;
    }
}
