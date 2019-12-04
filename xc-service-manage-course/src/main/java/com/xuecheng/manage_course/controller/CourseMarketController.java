package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CourseMarketController
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 19:19
 */
@RestController
@RequestMapping("/course")
public class CourseMarketController implements CourseMarketControllerApi {
    @Autowired
    private MarketService marketService;
    @Override
    @GetMapping("/getcoursemarket/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return marketService.getCourseMarketById(courseId);
    }

    @Override
    @PostMapping("/updatecoursemarket/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        return marketService.updateCourseMarket(id,courseMarket);
    }
}
