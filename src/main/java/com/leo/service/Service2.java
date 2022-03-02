package com.leo.service;

import com.alibaba.fastjson.JSON;
import com.leo.entity.MiddleStudent;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service2 {

    private static File outFile = new File("result2.txt");

    public static void primaryStudentAverage(List<MiddleStudent> middleStudentList){
        //统计各个小学各班级的中考平均分
        Map<String, Map<String, Double>> primaryStudentAverage = middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(
                Collectors.groupingBy(MiddleStudent::getPrimarySchool, Collectors.groupingBy(MiddleStudent::getPrimaryClassInfo, Collectors.averagingInt(MiddleStudent::getScore))));
        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out.write(JSON.toJSONString(primaryStudentAverage));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
