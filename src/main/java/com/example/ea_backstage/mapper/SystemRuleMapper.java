package com.example.ea_backstage.mapper;

import com.example.ea_backstage.bean.RuleTime;
import com.example.ea_backstage.bean.Sys;
import com.example.ea_backstage.bean.SystemRule;
import com.example.ea_backstage.bean.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SystemRuleMapper {
    //查询打卡规则
    @Select({"select \n" +
            "TIME_FORMAT(qiandao_startTime,'%h:%m') as time1,\n" +
            "TIME_FORMAT(qiandao_endTime,'%h:%m') as time2,\n" +
            "TIME_FORMAT(qiantui_startTime,'%h:%m') as time3,\n" +
            "TIME_FORMAT(qiantui_endTime,'%h:%m') as time4\n" +
            "from tb_rule where id=1"})
    @Results(id="rulesystem",value={
            @Result(column = "time1",property = "qiandao_startTime"),
            @Result(column = "time2",property = "qiandao_endTime"),
            @Result(column = "time3",property = "qiantui_startTime"),
            @Result(column = "time4",property = "qiantui_endTime")
    })
    SystemRule findsysrule();

    //更新打卡规则
    @Update("update tb_rule" +
            " set qiandao_startTime = #{qiandao_startTime}," +
            " qiandao_endTime = #{qiandao_endTime}," +
            " qiantui_startTime = #{qiantui_startTime}," +
            " qiantui_endTime = #{qiantui_endTime}" +
            " where id=1")
    int updatesys(RuleTime sys);
}
