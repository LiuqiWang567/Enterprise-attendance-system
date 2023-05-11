package com.example.ea_backstage.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Leave implements Serializable {
    private int id;
    private String username;
    private Date starttime;
    private Date endtime;
    private int leavetype;
    private Leavetype lts;
    private String reasons;
    private int ifapply;
    private Resultss rs;
    private User user;
    private int leavedays;
}
