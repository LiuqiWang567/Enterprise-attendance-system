package com.example.myapplication.ui.Entity.Item;

public class ItemDaka {
    private int id;
    private String result;
    private String time1;
    private String time2;
    private String location;
    public ItemDaka(){}

    @Override
    public String toString() {
        return "ItemDaka{" +
                "id=" + id +
                ", result='" + result + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ItemDaka(int id, String result, String time1, String time2, String location) {
        this.id = id;
        this.result = result;
        this.time1 = time1;
        this.time2 = time2;
        this.location = location;
    }
}
