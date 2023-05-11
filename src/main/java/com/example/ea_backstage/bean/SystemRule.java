package com.example.ea_backstage.bean;

import java.sql.Time;

public class SystemRule {
    private int id;
    private String qiandao_startTime;
    private String qiandao_endTime;
    private String qiantui_startTime;
    private String qiantui_endTime;


    public SystemRule(){}

    @Override
    public String toString() {
        return "SystemRule{" +
                "id=" + id +
                ", qiandao_startTime='" + qiandao_startTime + '\'' +
                ", qiandao_endTime='" + qiandao_endTime + '\'' +
                ", qiantui_startTime='" + qiantui_startTime + '\'' +
                ", qiantui_endTime='" + qiantui_endTime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQiandao_startTime() {
        return qiandao_startTime;
    }

    public void setQiandao_startTime(String qiandao_startTime) {
        this.qiandao_startTime = qiandao_startTime;
    }

    public String getQiandao_endTime() {
        return qiandao_endTime;
    }

    public void setQiandao_endTime(String qiandao_endTime) {
        this.qiandao_endTime = qiandao_endTime;
    }

    public String getQiantui_startTime() {
        return qiantui_startTime;
    }

    public void setQiantui_startTime(String qiantui_startTime) {
        this.qiantui_startTime = qiantui_startTime;
    }

    public String getQiantui_endTime() {
        return qiantui_endTime;
    }

    public void setQiantui_endTime(String qiantui_endTime) {
        this.qiantui_endTime = qiantui_endTime;
    }

    public SystemRule(int id, String qiandao_startTime, String qiandao_endTime, String qiantui_startTime, String qiantui_endTime) {
        this.id = id;
        this.qiandao_startTime = qiandao_startTime;
        this.qiandao_endTime = qiandao_endTime;
        this.qiantui_startTime = qiantui_startTime;
        this.qiantui_endTime = qiantui_endTime;
    }
}
