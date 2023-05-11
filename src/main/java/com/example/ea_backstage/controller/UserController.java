package com.example.ea_backstage.controller;

import com.example.ea_backstage.bean.User;
import com.example.ea_backstage.service.ReportServiceI;
import com.example.ea_backstage.service.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ReportServiceI reportServiceI;

    //登录业务
    @RequestMapping("/")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/loginfrom")
    public String loginForm(ModelAndView mv, @Param("username") String username, @Param("password") String password){

        User userlogin=userService.loginuser(username,password);

        if(userlogin!=null) {
            mv.addObject("user",userlogin);
            mv.setViewName("index");
            return "index";
        }
        return "login";
    }
    @RequestMapping("/toexit")
    public String toexit(){
        return "login";
    }

    //用户管理
    //查询所有用户
    @RequestMapping("/touserlist")
    @ResponseBody
    public ModelAndView UserList(ModelAndView mv,
                                 @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<User> list = userService.findAllUser();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("UsersManagement");
        return mv;
    }

    //根据用户名查找用户
    @PostMapping("/tofindUserByusername")
    @ResponseBody
    public ModelAndView updatePage(String username, ModelAndView mv,
                                   @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        List<User> userone = reportServiceI.findoneuserreport(username);
        if (pageNum < 1 || pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 5);
        List<User> list = new ArrayList<>();
        PageInfo<User> pageInfo = new PageInfo<>(userone);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("UsersManagement");
        return mv;
    }
    //编辑用户

    @RequestMapping("/toupdateuser")
    public ModelAndView updateUser(ModelAndView mv,String username) {
        User findOne = userService.findUserByUsername(username);
        mv.addObject("users", findOne);
        mv.setViewName("updateuser");   //将数据发送给userupdate页面
        return mv;
    }

    @PostMapping("/updateUserinfo")
    public String updateUser(User user, HttpServletResponse servletResponse) throws IOException {
        int row=userService.updateUser(user);
        if(row==1) {
            servletResponse.sendRedirect("/touserlist");
        }
        return "error";
    }


    //删除用户
    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer id, HttpServletResponse servletResponse) throws IOException {
        int count = userService.deleteUser(id);
        System.out.println("1111111");
        if (count == 1) {
            servletResponse.sendRedirect("/touserlist");
        }
        return "error";
    }
    //新增用户
    @RequestMapping("/toadduser")
    public String toadduser(){
        return "adduser";
    }

    @RequestMapping("/adduser")
    public String adduser(User user, HttpServletResponse servletResponse) throws IOException {
        int addUser = userService.addUser(user);
        if (addUser == 1) {
            servletResponse.sendRedirect("/touserlist");
        }
        return "error";
    }

}
