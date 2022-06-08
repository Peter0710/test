package com.leo.service;

import com.alibaba.fastjson.JSON;
import com.leo.entity.MiddleStudent;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service1 {

    private static File outFile = new File("result1.txt");

    public static void middleStudentAverage(List<MiddleStudent> middleStudentList ){
        //统计各个中学各班级的中考平均分ooooooooo
        Map<String, Map<String, Double>> middleStudentAverage = middleStudentList.stream().collect(
                Collectors.groupingBy(MiddleStudent::getSchool, Collectors.groupingBy(MiddleStudent::getClassInfo, Collectors.averagingInt(MiddleStudent::getScore))));
        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out.write(JSON.toJSONString(middleStudentAverage));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
