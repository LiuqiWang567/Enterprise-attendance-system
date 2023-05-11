package com.example.ea_backstage.mapper;

import com.example.ea_backstage.bean.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    //查询部门信息
    @Select({"select * from tb_department"})
    @Results(id="alldep",value={
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "department_name",property = "department_name"),
            @Result(column = "department_leader",property = "department_leader"),
            @Result(column = "department_Numemp", property = "department_Numemp",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.depnums")
            ),
            @Result(column = "describle",property = "describle")
    })
    List<Department> fingAllDep();

    @Select({"select * from tb_department where did=#{department_id}"})
    @Results(id="depbyid",value={
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "department_name",property = "department_name"),
            @Result(column = "department_leader",property = "department_leader"),
            @Result(column = "department_Numemp", property = "department_Numemp",
                    one = @One(select = "com.example.ea_backstage.mapper.DepartmentMapper.depnums")
            ),
            @Result(column = "describle",property = "describle")
    })
    Department findDepById(@Param("department_id") int department_id);

    //增加部门
    @Insert("insert into tb_department(did,department_name,department_leader,department_Numemp,describle) values (" +
            "#{did},#{department_name},#{department_leader},#{department_Numemp},#{describle})")
    @Options(useGeneratedKeys=true, keyProperty="did", keyColumn="did")
    public int addDep(Department dep);

    //编辑部门
    @Update("update tb_department" +
            " set department_name=#{department_name,jdbcType=VARCHAR}," +
            "department_leader=#{department_leader,jdbcType=VARCHAR}," +
            "describle=#{describle,jdbcType=VARCHAR}" +
            " where did=#{did,jdbcType=INTEGER}")
    public int updateDep(Department dep);
    //删除部门
    @Delete("delete from tb_department where did=#{did}")
   public int delDep(@Param("did") Integer did);

    //查询部门人数
    @Select("select count(*) from tb_user where department_id=#{department_id}")
    public int depnums(@Param("username") int department_id);


}
