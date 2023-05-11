package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.Department;
import com.example.ea_backstage.mapper.DepartmentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentServiceI{

    @Resource
    DepartmentMapper departmentMapper;

    @Override
    public int addDep(Department dep) {
        return departmentMapper.addDep(dep);
    }

    @Override
    public int delDep(int did) {
        return departmentMapper.delDep(did);
    }


    @Override
    public int updateDep(Department dep) {
        return departmentMapper.updateDep(dep);
    }

    @Override
    public Department findDepById(int did) {
        return departmentMapper.findDepById(did);
    }

    @Override
    public List<Department> findAllDep() {
        return departmentMapper.fingAllDep();
    }
}
