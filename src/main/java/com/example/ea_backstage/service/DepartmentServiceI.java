package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.Department;
import org.apache.catalina.LifecycleState;

import java.util.List;


public interface DepartmentServiceI {

    public int addDep(Department dep);

    public int delDep(int did);

    public int updateDep(Department dep);

    public Department findDepById(int did);

    public List<Department> findAllDep();



}

