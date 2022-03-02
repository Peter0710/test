package com.leo.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionUtils {

    public static SqlSession getPrimaryStudentSession(){
        Reader reader;
        //获取配置的资源文件
        try {
            reader = Resources.getResourceAsReader("dataSource1.xml");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("找不到dataSource1.xml文件");
        }

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //sqlSession能够执行配置文件中的SQL语句
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }


    public static SqlSession getMiddleStudentSession(){
        Reader reader;
        //获取配置的资源文件
        try {
            reader = Resources.getResourceAsReader("dataSource2.xml");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("找不到dataSource2.xml文件");
        }

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //sqlSession能够执行配置文件中的SQL语句
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }
}
