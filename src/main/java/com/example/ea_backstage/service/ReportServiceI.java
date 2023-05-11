package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.Leave;
import com.example.ea_backstage.bean.Punch;
import com.example.ea_backstage.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportServiceI {

    List<Punch> findAllpunch();

    List<Leave> findAllleave();

     List<Punch> findpunchByusername(String username);


    List<Leave> findleaveByusername(String username);


    List<User> findallreport();

    List<User> findoneuserreport(String username);


}
