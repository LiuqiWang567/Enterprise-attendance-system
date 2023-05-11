package com.example.ea_backstage.bean;

import java.sql.Time;

public class RuleTime {
    private Time qiandao_startTime;
    private Time qiandao_endTime;
    private Time qiantui_startTime;
    private Time qiantui_endTime;


    @Override
    public String toString() {
        return "RuleTime{" +
                "qiandao_startTime=" + qiandao_startTime +
                ", qiandao_endTime=" + qiandao_endTime +
                ", qiantui_startTime=" + qiantui_startTime +
                ", qiantui_endTime=" + qiantui_endTime +
                '}';
    }

    public RuleTime(){}
    public RuleTime(Time qiandao_startTime, Time qiandao_endTime, Time qiantui_startTime, Time qiantui_endTime) {
        this.qiandao_startTime = qiandao_startTime;
        this.qiandao_endTime = qiandao_endTime;
        this.qiantui_startTime = qiantui_startTime;
        this.qiantui_endTime = qiantui_endTime;
    }

    public Time getQiandao_startTime() {
        return qiandao_startTime;
    }

    public void setQiandao_startTime(Time qiandao_startTime) {
        this.qiandao_startTime = qiandao_startTime;
    }

    public Time getQiandao_endTime() {
        return qiandao_endTime;
    }

    public void setQiandao_endTime(Time qiandao_endTime) {
        this.qiandao_endTime = qiandao_endTime;
    }

    public Time getQiantui_startTime() {
        return qiantui_startTime;
    }

    public void setQiantui_startTime(Time qiantui_startTime) {
        this.qiantui_startTime = qiantui_startTime;
    }

    public Time getQiantui_endTime() {
        return qiantui_endTime;
    }

    public void setQiantui_endTime(Time qiantui_endTime) {
        this.qiantui_endTime = qiantui_endTime;
    }
}
