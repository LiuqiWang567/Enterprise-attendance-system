package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.Leave;
import com.example.ea_backstage.bean.Punch;
import com.example.ea_backstage.bean.User;
import com.example.ea_backstage.mapper.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportServiceI{
    @Resource
    ReportMapper reportMapper;
    @Override
    public List<Punch> findAllpunch() {
        return reportMapper.findAllpunch();
    }

    @Override
    public List<Leave> findAllleave() {
        return reportMapper.findAllleave();
    }

    @Override
    public List<Punch> findpunchByusername(String username) {
        return reportMapper.findpunchByusername(username);
    }

    @Override
    public List<Leave> findleaveByusername(String username) {
        return reportMapper.findleaveByusername(username);
    }

    @Override
    public List<User> findallreport() {
        return reportMapper.findallreport();
    }

    @Override
    public List<User> findoneuserreport(String username) {
        return reportMapper.findoneuserreport(username);
    }
}
