package com.example.ea_backstage.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.ea_backstage.bean.Department;
import com.example.ea_backstage.bean.EchartsVo;
import com.example.ea_backstage.mapper.DepartmentMapper;
import com.example.ea_backstage.service.DepartmentServiceI;
import com.example.ea_backstage.service.ReportServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tocharts")
@Scope(value = "prototype")
public class ChartsController {
    @Autowired
    private DepartmentMapper departmentMapper;

    @RequestMapping("/getEchartsDate.action")
    @ResponseBody
    public ModelAndView deparmentcharts(ModelAndView mv){
        EchartsVo echartsVo=new EchartsVo();
        List<String> data=departmentMapper.fingAllDep().stream().map(item->item.getDepartment_Numemp().toString()).collect(Collectors.toList());
        List<String> categories=departmentMapper.fingAllDep().stream().map(item->item.getDepartment_name().toString()).collect(Collectors.toList());

        echartsVo.setCategories(categories);
        echartsVo.setData(data);
        mv.addObject("echartsVo",echartsVo);
        mv.setViewName("Charts");
        return mv;

    }
}
