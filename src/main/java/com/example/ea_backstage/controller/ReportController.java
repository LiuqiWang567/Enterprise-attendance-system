package com.example.ea_backstage.controller;

import com.example.ea_backstage.bean.Leave;
import com.example.ea_backstage.bean.Punch;
import com.example.ea_backstage.bean.Report;
import com.example.ea_backstage.bean.User;
import com.example.ea_backstage.service.ReportServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ReportController {
    @Autowired
    ReportServiceI reportServiceI;

    @RequestMapping("/tocharts")
    public String tocharts(){
        return "Charts";
    }

    //汇总报表
    @RequestMapping("/toreport")
    @ResponseBody
    public ModelAndView reportlist(ModelAndView mv,
                                 @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<User> list = reportServiceI.findallreport();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("reportManagement");
        return mv;
    }

    //查询单个用户的汇总报表
    @RequestMapping("/tofindreport")
    @ResponseBody
    public ModelAndView oneuserreportlist(ModelAndView mv,
                                   @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,String username){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<User> oneuserreport=reportServiceI.findoneuserreport(username);
        PageInfo<User> pageInfo = new PageInfo<>(oneuserreport);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("reportManagement");
        return mv;
    }

    //打卡报表
    @RequestMapping("/topunchreport")
    @ResponseBody
    public ModelAndView punchlist(ModelAndView mv,
                                   @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<Punch> list = reportServiceI.findAllpunch();
        PageInfo<Punch> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("PunchReport");
        return mv;
    }

    //查询单个用户的打卡报表
    @RequestMapping("/tooneuserpunchreport")
    @ResponseBody
    public ModelAndView oneuserpunchlist(ModelAndView mv,
                                  @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,String username){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<Punch> list = reportServiceI.findpunchByusername(username);
        PageInfo<Punch> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("PunchReport");
        return mv;
    }

    //请假报表
    @RequestMapping("/toleavereport")
    @ResponseBody
    public ModelAndView leavelist(ModelAndView mv,
                                  @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<Leave> list = reportServiceI.findAllleave();
        PageInfo<Leave> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("LeaveReport");
        return mv;
    }

    //单个用户请假报表
    @RequestMapping("/tooneleavereport")
    @ResponseBody
    public ModelAndView oneuserleavelist(ModelAndView mv,
                                  @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,String username){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<Leave> list = reportServiceI.findleaveByusername(username);
        PageInfo<Leave> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("LeaveReport");
        return mv;
    }

}
