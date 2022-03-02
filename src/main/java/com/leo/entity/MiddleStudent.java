package com.leo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiddleStudent implements Serializable {

    private Integer id;

    private String name;

    private String sex;

    private String phone;

    private String classInfo;

    private String school;

    private Integer score;

    private String primaryClassInfo;

    private String primarySchool;

    public String getPrimarySchoolAndClass(){
        return primarySchool + " " + primaryClassInfo;
    }

    public String getSchoolAndClass(){
        return school + " " + classInfo;
    }

}
