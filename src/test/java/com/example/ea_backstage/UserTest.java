package com.example.ea_backstage;

import com.example.ea_backstage.bean.*;
import com.example.ea_backstage.mapper.DepartmentMapper;
import com.example.ea_backstage.mapper.ReportMapper;
import com.example.ea_backstage.mapper.SystemRuleMapper;
import com.example.ea_backstage.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class UserTest {
    @Autowired
    UserMapper usermapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    ReportMapper reportMapper;

    @Autowired
    SystemRuleMapper systemRuleMapper;
    @Test
    public void test1(){
        List<User> userList= usermapper.findAllUser();
        userList.forEach(e->System.out.println(e));
    }
    @Test
    public void test2(){
        List<Department> deplist= departmentMapper.fingAllDep();
        deplist.forEach(e->System.out.println(e));
    }
    @Test
    public void test3(){
        Department dep1=new Department();
        dep1.setDepartment_name("后勤部");
        departmentMapper.addDep(dep1);
        List<Department> deplist= departmentMapper.fingAllDep();
        deplist.forEach(e->System.out.println(e));
    }
    @Test
    public void test4(){
        Department dep1=departmentMapper.findDepById(9);
        dep1.setDepartment_name("后勤部2");
        departmentMapper.updateDep(dep1);
        List<Department> deplist= departmentMapper.fingAllDep();
        deplist.forEach(e->System.out.println(e));
    }

    @Test
    public void test5(){
        departmentMapper.delDep(5);
        List<Department> deplist= departmentMapper.fingAllDep();
        deplist.forEach(e->System.out.println(e));
    }
    @Test
    public void test6(){
        Department dep=departmentMapper.findDepById(3);
        System.out.println(dep);
    }

    @Test
    public void test61(){
        String username="2110120";
        User user=usermapper.findUserByUsername(username);
        System.out.println(user);
    }
    @Test
    public void test7(){
        List<Punch> list= reportMapper.findAllpunch();
        list.forEach(e->System.out.println(e));
    }
    @Test
    public void test8(){
        List<Leave> list= reportMapper.findAllleave();
        list.forEach(e->System.out.println(e));
    }

    @Test
    public void test9(){
        SystemRule sysrule=systemRuleMapper.findsysrule();

        System.out.println(sysrule);
    }

    @Test
    public void test10(){
        SystemRule sysrule=systemRuleMapper.findsysrule();
        System.out.println(sysrule);

        SystemRule sys1=new SystemRule();
        LocalDateTime localTime=LocalDateTime.now();
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("hh:mm");
        String time1=timeFormatter.format(localTime);

        //int res=systemRuleMapper.updatesys(sys1);
        SystemRule sysrule2=systemRuleMapper.findsysrule();

        System.out.println(sysrule2);
    }

    @Test
    public void test11(){
        List<Department> list=departmentMapper.fingAllDep();
        List<String> departmentlist=list.stream().map(item->item.getDepartment_name().toString()).collect(Collectors.toList());
        System.out.println(departmentlist);
        List<String> numberlist=list.stream().map(item->item.getDepartment_Numemp().toString()).collect(Collectors.toList());
        System.out.println(numberlist);

        //list.forEach(e->System.out.println(e));
//        String[] department_name=new String[list.size()];
//        int count=0;
//        for(Department dep:list){
//            department_name[count]=dep.getDepartment_name();
//            count++;
//        }
//        System.out.println(department_name);

    }
}
