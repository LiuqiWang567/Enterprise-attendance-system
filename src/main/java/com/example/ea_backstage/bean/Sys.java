package com.example.ea_backstage.bean;

import lombok.Data;

import java.sql.Time;

@Data
public class Sys {
    private int id;
    private Time punchstarttime;
    private Time punchendtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getPunchstarttime() {
        return punchstarttime;
    }

    public void setPunchstarttime(Time punchstarttime) {
        this.punchstarttime = punchstarttime;
    }

    public Time getPunchendtime() {
        return punchendtime;
    }

    public void setPunchendtime(Time punchendtime) {
        this.punchendtime = punchendtime;
    }
}
