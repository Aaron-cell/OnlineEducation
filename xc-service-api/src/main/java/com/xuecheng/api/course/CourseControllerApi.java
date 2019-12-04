package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CourseControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/21 21:49
 */
@Api(value="课程管理接口")
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);
    @ApiOperation("查询我的课程列表")
    public QueryResponseResult findCourseList(int page, int size);
    @ApiOperation("添加课程基本信息")
    public ResponseResult addCourseBase(CourseBase courseBase);
    @ApiOperation("获取课程基本信息")
    public CourseBase getCourseBaseById(String courseId);
    @ApiOperation("更新课程基础信息")
    public ResponseResult updateCourseBase(CourseBase courseBase);
}
