package com.example.ea_backstage.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
public class Department implements Serializable {
    @TableId(value = "did", type = IdType.AUTO)
    private Integer did;
    private String department_name;
    private String department_leader;
    private Integer department_Numemp;
    private String describle;

}
