package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 码农界的小学生
 * @description:
 * @title: MarketService
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 19:21
 */
@Service
public class MarketService {
    @Autowired
    private CourseMarketRepository courseMarketRepository;

    /**
     * 查询课程营销信息
     * @param courseId
     * @return
     */
    @Transactional
    public CourseMarket getCourseMarketById(String courseId) {
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        //不管数据库有没有都返回
        return optional.get();
    }

    /**
     * 更新课程营销信息 如果查询不到，则表示添加
     * @param id
     * @param courseMarket
     * @return
     */
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {

        courseMarket.setId(id);
        courseMarketRepository.save(courseMarket);
        return ResponseResult.SUCCESS();
    }
}
