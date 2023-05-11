package com.example.ea_backstage.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer department_id;
    private String phone;
    private String email;
    private String school;
    private Date birthday;
    private String gender;
    private String education;
    private String address;
    private Date dateofentry;
    private String name;
    private String role;
    private Department dep;
    private List<Punch> punch;
    private List<Leave> oneleave;
    private int allleavedays;
    private int countpunch;

}
