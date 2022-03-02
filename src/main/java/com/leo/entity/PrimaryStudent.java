package com.leo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrimaryStudent implements Serializable {

    private Integer id;

    private String name;

    private String sex;

    private String phone;

    private String classInfo;

    private String school;
}
