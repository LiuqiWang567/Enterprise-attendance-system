package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.RuleTime;
import com.example.ea_backstage.bean.SystemRule;
import org.apache.ibatis.annotations.*;

public interface SysServiceI {
    SystemRule findrule();
    //更新打卡规则

    int updaterule(@Param("sys") RuleTime systemRule);

}
