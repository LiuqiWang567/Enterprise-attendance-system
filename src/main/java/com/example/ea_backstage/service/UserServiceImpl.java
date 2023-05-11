package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.Department;
import com.example.ea_backstage.bean.User;
import com.example.ea_backstage.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserServiceI{

    @Resource
    UserMapper usermapper;

    public List<User> findAllUser(){
        return usermapper.findAllUser();
    }
    public User findUserByDep(int deparment_id){
        return usermapper.findUserByDep(deparment_id);
    }
    public User findUserByUsername(String username) {
        return usermapper.findUserByUsername(username);
    }

    @Override
    public User loginuser(String username, String password) {
        return usermapper.loginuser(username,password);
    }

    @Override
    public int addUser(User user) {
        return usermapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return usermapper.updateUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return usermapper.deleteUser(id);
    }

}
