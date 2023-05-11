package com.example.ea_backstage.mapper;

import com.example.ea_backstage.bean.*;
import com.example.ea_backstage.bean.Resultss;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;

import java.util.List;

@Mapper
public interface ReportMapper {
    //查询全部打卡信息
    @Select({"select * from tb_punch"})
    @Results(id = "allpunchinfo", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "username", property = "user",
                    one =@One(select = "com.example.ea_backstage.mapper.UserMapper.findUserByUsername")
            ),
            @Result(column = "username", property = "punchdays",
                    one =@One(select = "com.example.ea_backstage.mapper.ReportMapper.countpunch")
            ),
            @Result(column = "punchcount", property = "punchcount"),
            @Result(column = "punchtime1", property = "punchtime1"),
            @Result(column = "punchtime2", property = "punchtime2"),
            @Result(column = "buka", property = "buka"),
            @Result(column = "punchresid", property = "punchresultid"),

            @Result(column = "punchresid", property = "pr",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findpresultByID")
            ),
            @Result(column = "buka", property = "rs",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findresultByID")
            )
    })
    List<Punch> findAllpunch();

    //查询通过用户名打卡信息
    @Select({"select * from tb_punch where username=#{username}"})
    @Results(id = "onepunchinfo2", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "username", property = "user",
                    one =@One(select = "com.example.ea_backstage.mapper.UserMapper.findUserByUsername")

            ),
            @Result(column = "username", property = "punchdays",
                    one =@One(select = "com.example.ea_backstage.mapper.ReportMapper.countpunch")

            ),
            @Result(column = "punchcount", property = "punchcount"),
            @Result(column = "punchtime1", property = "punchtime1"),
            @Result(column = "punchtime2", property = "punchtime2"),
            @Result(column = "buka", property = "buka"),
            @Result(column = "punchresid", property = "punchresultid"),

            @Result(column = "punchresid", property = "pr",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findpresultByID")
            ),
            @Result(column = "buka", property = "rs",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findresultByID")
            )
    })
   public List<Punch> findpunchByusername(@Param("username") String username);


    @Select({"select * from tb_result where id=#{id}"})
    @Results(id = "findrsbyid", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "result", property = "result")

    })
    public Result findresultByID(int id);

    @Select({"select * from tb_punchresults where id=#{id}"})
    @Results(id = "findprsbyid2", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "punchresult", property = "punchresult")

    })
    public Punchresults findpresultByID(int id);

    @Select({"select * from tb_leavetype where id=#{id}"})
    @Results(id = "findprsbyid3", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "leavetypes", property = "leavetypes")
    })
    public Leavetype findleavetyByID(int id);

    @Select({"select * from tb_result where id=#{id}"})
    @Results(id = "findrsbyid3", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "result", property = "result")
    })
    public Resultss findrsyByID(@Param("id") int id);


    //查询全部请假信息
    @Select({"select * from tb_leave"})
    @Results(id = "allleaveinfo", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id",property = "leavedays",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.leavedays")),
            @Result(column = "username", property = "user",
                    one =@One(select = "com.example.ea_backstage.mapper.UserMapper.findUserByUsername")),
            @Result(column = "starttime", property = "starttime"),
            @Result(column = "endtime", property = "endtime"),
            @Result(column = "leavetype", property = "lts",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findleavetyByID")
            ),
            @Result(column = "reasons", property = "reasons"),
            @Result(column = "ifapplay", property = "ifapply"),
            @Result(column = "ifapplay", property = "rs",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findrsyByID")
            )
    })
    public List<Leave> findAllleave();
    //查询单个用户全部请假信息
    @Select({"select * from tb_leave where username=#{username}"})
    @Results(id = "allleaveinfo2", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id",property = "leavedays",
            one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.leavedays")),
            @Result(column = "username",property = "username"),
            @Result(column = "username", property = "user",
                    one =@One(select = "com.example.ea_backstage.mapper.UserMapper.findUserByUsername")),
            @Result(column = "starttime", property = "starttime"),
            @Result(column = "endtime", property = "endtime"),
            @Result(column = "leavetype", property = "lts",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findleavetyByID")
            ),
            @Result(column = "reasons", property = "reasons"),
            @Result(column = "ifapplay", property = "ifapply"),
            @Result(column = "ifapplay", property = "rs",
                    one = @One(select = "com.example.ea_backstage.mapper.ReportMapper.findrsyByID")
            )
    })
    public List<Leave> findleaveByusername(@Param("username") String username);



    //查询单个用户请假单个天数
    @Select("SELECT id,username,DATEDIFF(endtime,starttime) FROM tb_leave where id=#{id}")
    public int leavedays(@Param("id") int id);

    //查询单个用户请假总天数
    @Select("select SUM(DATEDIFF(endtime,starttime)) from tb_leave where username=#{username}")
    public int allleavedays(@Param("username") String username);

    //查询正常打卡天数
    @Select({"select count(*) from tb_punch where username=#{username} and punchresid=1"})
    public int countpunch(@Param("username")String username);

    //查询总报表,编号，工号，部门，打卡总数，请假总数
    @Select({"select * from tb_user"})
    @Results(id = "allreport", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "department_id", property = "dep",
                    one =@One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "username",property = "countpunch",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.countpunch")),
            @Result(column = "username",property = "allleavedays",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.allleavedays"))
    })
    List<User> findallreport();

    //查询单个用户总报表,编号，工号，部门，打卡总数，请假总数
    @Select({"select * from tb_user where username=#{username}"})
    @Results(id = "alloneuserreport", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "department_id", property = "dep",
                    one =@One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "username",property = "countpunch",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.countpunch")),
            @Result(column = "username",property = "allleavedays",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.allleavedays"))
    })
    List<User> findoneuserreport(@Param("username")String username);

    //考勤率计算：每个员工实际打卡天数/应该打卡天数+% where 月份 group By 部门

    @Select({"select username,(select count(*) from tb_punch where username=#{username} and punchresid=1)/22 as punchrate from tb_user where username=#{username}"})
    @Results(id = "oneuserpunchrate", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "department_id", property = "dep",
                    one =@One(select = "com.example.ea_backstage.mapper.DepartmentMapper.findDepById")
            ),
            @Result(column = "username",property = "countpunch",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.countpunch")),
            @Result(column = "username",property = "allleavedays",
                    one=@One(select = "com.example.ea_backstage.mapper.ReportMapper.allleavedays"))
    })

    float oneuerpunchrate(@Param("username")String username);



}