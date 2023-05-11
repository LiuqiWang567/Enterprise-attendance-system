package com.example.ea_backstage.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.DateTimeException;

@Data
public class Punch implements Serializable {
    private int id;
    private String username;
    private int punchcount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp punchtime1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp punchtime2;
    private int punchresultid;
    private Punchresults pr;
    private int buka;
    private Resultss rs;
    private User user;
    private int punchdays;

}
