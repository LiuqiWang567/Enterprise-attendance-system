package com.example.myapplication.ui.Entity;

public class Report {
    private String username;
    private String department;
    private String month;
    private String punchdayl;
    private String leaveday;
    private String rate;

    @Override
    public String toString() {
        return "Report{" +
                "username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", month='" + month + '\'' +
                ", punchdayl='" + punchdayl + '\'' +
                ", leaveday='" + leaveday + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    public Report(){}
    public Report(String username, String department, String month, String punchdayl, String leaveday, String rate) {
        this.username = username;
        this.department = department;
        this.month = month;
        this.punchdayl = punchdayl;
        this.leaveday = leaveday;
        this.rate = rate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPunchdayl() {
        return punchdayl;
    }

    public void setPunchdayl(String punchdayl) {
        this.punchdayl = punchdayl;
    }

    public String getLeaveday() {
        return leaveday;
    }

    public void setLeaveday(String leaveday) {
        this.leaveday = leaveday;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
