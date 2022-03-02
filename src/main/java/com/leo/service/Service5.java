package com.leo.service;

import com.alibaba.fastjson.JSON;
import com.leo.entity.MiddleStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service5 {

    private static File outFile = new File("result5.txt");

    public static void duplicateSchoolAndClass(List<MiddleStudent> middleStudentList){
        //统计小学和初中都是同校且同班同学的学生名单

        List<MiddleStudent> duplicateSchoolAndClass = new ArrayList<>();
        middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(Collectors.groupingBy(MiddleStudent::getPrimarySchoolAndClass)).forEach((k,v)->{
            Map<String, List<MiddleStudent>> collect1 = v.stream().collect(Collectors.groupingBy(MiddleStudent::getSchoolAndClass));
            collect1.forEach((a,b)->{
                if(b.size()>1){
                    duplicateSchoolAndClass.addAll(b);
                }
            });
        });

        Writer out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out.write(JSON.toJSONString(duplicateSchoolAndClass));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
