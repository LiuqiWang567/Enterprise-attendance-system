package com.example.ea_backstage.service;

import com.example.ea_backstage.bean.RuleTime;
import com.example.ea_backstage.bean.SystemRule;
import com.example.ea_backstage.mapper.SystemRuleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysServiceImpl implements SysServiceI{
    @Resource
    SystemRuleMapper systemRuleMapper;


    @Override
    public SystemRule findrule() {
        return systemRuleMapper.findsysrule();
    }

    @Override
    public int updaterule(RuleTime systemRule) {
        return systemRuleMapper.updatesys(systemRule);
    }
}
