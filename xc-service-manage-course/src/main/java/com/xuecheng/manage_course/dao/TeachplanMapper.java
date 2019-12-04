package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 码农界的小学生
 * @description:
 * @title: TeachplanMapper
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/22 11:16
 */
@Mapper
public interface TeachplanMapper {
    //课程计划查询方法
    public TeachplanNode selectList(String courseId);
}
