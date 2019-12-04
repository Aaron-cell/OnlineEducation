package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CourseMarketControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 19:03
 */
@Api("课程营销管理接口")
public interface CourseMarketControllerApi {
    @ApiOperation(("获取课程营销信息"))
    public CourseMarket getCourseMarketById(String courseId);
    @ApiOperation("更新课程营销信息")
    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);
}
