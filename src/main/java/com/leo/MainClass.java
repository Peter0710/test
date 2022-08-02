package com.leo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leo.entity.MiddleStudent;
import com.leo.entity.PrimaryStudent;
import com.leo.service.*;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Leo
 */
public class MainClass {

    public static ExcelWriter writer = ExcelUtil.getWriter("d:/result.xlsx", "中学各班级的中考平均分");

    public static void main(String[] args) {
        //获取小学sqlSession
        SqlSession primaryStudentSession = SqlSessionUtils.getPrimaryStudentSession();
        //获取中学sqlSession
        SqlSession middleStudentSession = SqlSessionUtils.getMiddleStudentSession();
        List<PrimaryStudent> primaryStudentList = primaryStudentSession.selectList("com.leo.dao.PrimaryStudentMapper.getPrimaryStudent");
        List<MiddleStudent> middleStudentList = middleStudentSession.selectList("com.leo.dao.MiddleStudentMapper.getMiddleStudent");

        Map<String, PrimaryStudent> primaryStudent = primaryStudentList.stream().collect(Collectors.toMap(PrimaryStudent::getPhone, Function.identity()));
        middleStudentList.forEach(student ->{
            if(primaryStudent.containsKey(student.getPhone())){
                student.setPrimaryClassInfo(primaryStudent.get(student.getPhone()).getClassInfo());
                student.setPrimarySchool(primaryStudent.get(student.getPhone()).getSchool());
            }
        });

        //统计各个中学各班级的中考平均分
        Map<String, Map<String, Double>> middleStudentAverage = middleStudentList.stream().collect(
                Collectors.groupingBy(MiddleStudent::getSchool, Collectors.groupingBy(MiddleStudent::getClassInfo, Collectors.averagingInt(MiddleStudent::getScore))));
//        Service1.middleStudentAverage(middleStudentList);
        //统计各个小学各班级的中考平均分
        Map<String, Map<String, Double>> primaryStudentAverage = middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(
                Collectors.groupingBy(MiddleStudent::getPrimarySchool, Collectors.groupingBy(MiddleStudent::getPrimaryClassInfo, Collectors.averagingInt(MiddleStudent::getScore))));
//        Service2.primaryStudentAverage(middleStudentList);
        // 统计各初中各班级学生分别来自哪所小学的哪个班级
        Map<String, Map<String, Map<String, Map<String, Long>>>> middleStudentDistribution = middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(
                Collectors.groupingBy(MiddleStudent::getSchool, Collectors.groupingBy(MiddleStudent::getClassInfo, Collectors.groupingBy(MiddleStudent::getPrimarySchool, Collectors.groupingBy(MiddleStudent::getPrimaryClassInfo, Collectors.counting())))));
//        Service3.middleStudentDistribution(middleStudentList);
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
//        Service4.duplicateSchool(middleStudentList);
//        //统计小学和初中都是同校且同班同学的学生名单
        List<MiddleStudent> duplicateSchoolAndClass = new ArrayList<>();
        middleStudentList.stream().filter(student -> student.getPrimaryClassInfo() != null).collect(Collectors.groupingBy(MiddleStudent::getPrimarySchoolAndClass)).forEach((k,v)->{
            Map<String, List<MiddleStudent>> collect1 = v.stream().collect(Collectors.groupingBy(MiddleStudent::getSchoolAndClass));
            collect1.forEach((a,b)->{
                if(b.size()>1){
                    duplicateSchoolAndClass.addAll(b);
                }
            });
        });
//        Service5.duplicateSchoolAndClass(middleStudentList);
        middleStudentAverage.forEach((k,v) ->{
            writer.merge(v.size() - 1, k);
            writer.write(CollUtil.newArrayList(v), true);
        });
        writer.setSheet("小学各班级的中考平均分123");
        primaryStudentAverage.forEach((k,v) ->{
            writer.merge(v.size() - 1, k);
            writer.write(CollUtil.newArrayList(v), true);
        });
        writer.setSheet("学生统计分布-----------123");
        middleStudentDistribution.forEach((k, v) ->{
            writer.merge(v.size() - 1, k);
            v.forEach((a,b) ->{
                writer.merge(v.size() - 1, a);
                b.forEach((c,d) ->{
                    writer.merge(v.size() - 1, c);
                    writer.write(CollUtil.newArrayList(d), true);
                });
            });
        });
        writer.setSheet("同校123");
        writer.write(CollUtil.newArrayList(duplicateSchool));
        writer.setSheet("同校且同班123");
        writer.write(CollUtil.newArrayList(duplicateSchoolAndClass));
        writer.close();
        System.out.println("test");
        System.out.println("朱阿敏大笨蛋123");
        System.out.println("刘振鹏123");
        System.out.println("a");
    }

}
