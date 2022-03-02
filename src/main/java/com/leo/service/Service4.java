package com.leo.service;

import com.alibaba.fastjson.JSON;
import com.leo.entity.MiddleStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service4 {

    private static File outFile = new File("result4.txt");

    public static void duplicateSchool(List<MiddleStudent> middleStudentList){
        //统计小学和初中都是同校同学的学生名单
        List<MiddleStudent> duplicateSchool = new ArrayList<>();
        middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(Collectors.groupingBy(MiddleStudent::getPrimarySchool)).forEach((k,v)->{
            Map<String, List<MiddleStudent>> collect1 = v.stream().collect(Collectors.groupingBy(MiddleStudent::getSchool));
            collect1.forEach((a,b)->{
                if(b.size()>1){
                    duplicateSchool.addAll(b);
                }
            });
        });
        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out.write(JSON.toJSONString(duplicateSchool));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
