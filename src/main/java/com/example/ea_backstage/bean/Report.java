package com.example.ea_backstage.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Report implements Serializable {
    private int id;
    private String username;
    private int punchdays;
    private int leavedays;
}
