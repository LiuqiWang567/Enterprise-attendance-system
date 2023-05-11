package com.example.ea_backstage.controller;

import com.example.ea_backstage.bean.Department;
import com.example.ea_backstage.service.DepartmentServiceI;
import com.example.ea_backstage.service.DepartmentServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentServiceI departmentServiceI;

    //查询所有部门信息
    @RequestMapping("/todepartment")
    // @ResponseBody
    public ModelAndView findalldep(ModelAndView mv,
                                   @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<Department> list = departmentServiceI.findAllDep();
        PageInfo<Department> pageInfo = new PageInfo<Department>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("DepartmentManagement");
        return mv;
    }

    //新增部门
    @RequestMapping("/adddep")
    public String adddep(Department dep, HttpServletResponse servletResponse) throws IOException {
        int addDep = departmentServiceI.addDep(dep);
        if (addDep == 1) {
            return "redirect:/todepartment";
        }
        return "error";
    }
    //删除部门
    @RequestMapping("/todeletedep")
    public String deletedep(@RequestParam Integer did, HttpServletResponse servletResponse) throws IOException {
        int count = departmentServiceI.delDep(did);
        System.out.println("1111111");
        if (count == 1) {
            return "redirect:todepartment";
        }
        return "error";
    }
    //编辑部门
    @RequestMapping("/toupdatedep")
    public ModelAndView updatedep(ModelAndView mv, @Param("did") int did) {
        Department onedep = departmentServiceI.findDepById(did);
        mv.addObject("onedep", onedep);
        mv.setViewName("updep");
        return mv;
    }

    @RequestMapping("/updatedep")
    public String updateDep2(Department dep) {
        int row = departmentServiceI.updateDep(dep);
        if (row == 1) {
            System.out.println("修改成功");
            return "redirect:/todepartment";
        }
        System.out.println("修改失败");
        return "error";


    }


}
