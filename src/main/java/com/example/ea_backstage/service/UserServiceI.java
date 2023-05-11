package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserServiceI {
    List<User> findAllUser();

    User findUserByDep(int deparment_id);

    User findUserByUsername(String username);

    User loginuser(String username,String password);

    int addUser(User user);
    //编辑用户
    int updateUser(User user);
    //删除用户
    int deleteUser(@Param("id") Integer id);
}
