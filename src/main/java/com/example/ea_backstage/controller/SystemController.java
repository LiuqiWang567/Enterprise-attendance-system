package com.example.ea_backstage.controller;

import com.example.ea_backstage.bean.RuleTime;
import com.example.ea_backstage.bean.Sys;
import com.example.ea_backstage.bean.SystemRule;
import com.example.ea_backstage.service.SysServiceI;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Controller
public class SystemController {
    @Autowired
    SysServiceI sysServiceI;
    //进入系统设置
    @SneakyThrows
    @RequestMapping("/tosystem")
    public ModelAndView Tosystem(ModelAndView mv) {
        SystemRule sysrule=sysServiceI.findrule();
        SimpleDateFormat timeformat=new SimpleDateFormat("hh:mm");

        mv.addObject("rule", sysrule);
       // mv.addObject("ruleTime",ruleTime);
        mv.setViewName("SystemManagement");   //将数据发送给userupdate页面
        return mv;
    }

    //更新系统设置
    @PostMapping("/updatesystem")
    public String updateUser(Time qiandao_startTime,Time qiandao_endTime,Time qiantui_startTime,Time qiantui_endTime, HttpServletResponse servletResponse) throws IOException {

        RuleTime rule=new RuleTime(qiandao_startTime,qiandao_endTime,qiantui_startTime,qiantui_endTime);


        int row=sysServiceI.updaterule(rule);
        if(row==1) {
            servletResponse.sendRedirect("/tosystem");
        }
        return "error";
    }
}
