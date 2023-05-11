package com.example.ea_backstage.mapper;

import com.example.ea_backstage.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //查询所有用户的信息
    @Select({"select * from tb_user"})
    @Results(id = "userinfo", value = {
            @Result(column = "username", property = "username"),
            @Result(column = "department_id", property = "dep",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "department_id", property = "department_id"),
            @Result(column = "username", property = "punch",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findpunchByusername")),

            @Result(column = "username", property = "oneleave",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findleaveByusername"))
    })
    List<User> findAllUser();

    //根据用户名查询用户信息
    @Select({"select * from tb_user where username=#{username}"})
    @Results(id = "userinfo3", value = {
            @Result(column = "department_id", property = "dep",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "department_id", property = "department_id")
    })
    public User findUserByUsername(@Param("username") String username);

    //根据部门名称查询用户信息
    @Select({"select * from tb_user where department_id=#{department_id}"})
    @Results(id = "userinfo1", value = {
            @Result(column = "department_id", property = "dep",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "department_id", property = "department_id")
    })
    User findUserByDep(@Param("department_id") int department_id);

    @Select({"select * from tb_user where username=#{username} and password=#{password}"})
    @Results(id = "userinfo2", value = {
            @Result(column = "department_id", property = "dep",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "department_id", property = "department_id")
    })
    User loginuser(@Param("username") String username, @Param("password") String password);

    //新增用户
    @Insert("insert into tb_user(username,password,department_id,phone,email,school,birthday,gender,education,address,dateofentry,name,role) values " +
            "(#{username},#{password},1,#{phone},#{email},#{school},#{birthday},#{gender},#{education},#{address},#{dateofentry},#{name},1)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(User user);

    //编辑用户
    @Update("update tb_user" +
            " set username = #{username,jdbcType=VARCHAR}," +
            " password = #{password,jdbcType=VARCHAR}," +
            " phone = #{phone,jdbcType=VARCHAR}," +
            " email = #{email,jdbcType=VARCHAR}," +
            " school = #{school,jdbcType=VARCHAR}," +
            " birthday = #{birthday,jdbcType=DATE}," +
            " gender = #{gender,jdbcType=VARCHAR}," +
            " education = #{education,jdbcType=VARCHAR}," +
            " address = #{address,jdbcType=VARCHAR}," +
            " dateofentry = #{dateofentry,jdbcType=DATE}," +
            " name = #{name,jdbcType=VARCHAR}" +
            " where username = #{username,jdbcType=VARCHAR}")
    int updateUser(User user);

    //删除用户
    @Delete("delete from tb_user where id=#{id}")
    int deleteUser(@Param("id") Integer id);

}
