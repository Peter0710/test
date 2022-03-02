package com.leo.service;

import com.alibaba.fastjson.JSON;
import com.leo.entity.MiddleStudent;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service3 {

    private static File outFile = new File("result3.txt");

    public static void middleStudentDistribution(List<MiddleStudent> middleStudentList){
        //统计各初中各班级学生分别来自哪所小学的哪个班级
        Map<String, Map<String, Map<String, Map<String, Long>>>> middleStudentDistribution = middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(
                Collectors.groupingBy(MiddleStudent::getSchool, Collectors.groupingBy(MiddleStudent::getClassInfo, Collectors.groupingBy(MiddleStudent::getPrimarySchool, Collectors.groupingBy(MiddleStudent::getPrimaryClassInfo, Collectors.counting())))));
        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out.write(JSON.toJSONString(middleStudentDistribution));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
