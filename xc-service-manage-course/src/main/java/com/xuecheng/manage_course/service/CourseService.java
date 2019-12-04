package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CourseService
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/22 12:45
 */
@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    //课程计划的查询
    public TeachplanNode findTeachplanList(String courseId){
        return teachplanMapper.selectList(courseId);
    }
    /*
       在操作数据库时要添加事务管理
     */
    //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //判断参数是否合法
        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //课程计划
        String courseid = teachplan.getCourseid();
        //parentId
        String parentid = teachplan.getParentid();
        //处理parentId
        if(StringUtils.isEmpty(parentid)){//添加至根节点
            parentid = this.getTeachplanRoot(courseid);
        }
        //父节点级别
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        String grade = optional.get().getGrade();
        Teachplan teachplan1 = new Teachplan();
        BeanUtils.copyProperties(teachplan,teachplan1);
        teachplan1.setParentid(parentid);
        teachplan1.setCourseid(courseid);
        if(grade.equals("1")){
            teachplan1.setGrade("2");
        }else{
            teachplan1.setGrade("3");
        }
        teachplanRepository.save(teachplan1);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //返回根节点id
    public String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){//查询不到该课程
            return null;
        }
        //判断是否存在根节点 不存在则添加
        List<Teachplan> list = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(list == null || list.size() <= 0){
            //添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setCourseid(courseId);
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplan.setPname(optional.get().getName());
            Teachplan save = teachplanRepository.save(teachplan);
            return save.getId();
        }
        return list.get(0).getId();
    }

    /**
     * 查询我的课程列表
     * @param page
     * @param size
     * @return
     */
    public QueryResponseResult getCourseList(int page, int size) {
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseList = courseMapper.getCourseList();
        List<CourseInfo> result = courseList.getResult();
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(courseList.getTotal());
        queryResult.setList(result);
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @Transactional
    public CourseBase getCourseBase(String courseId) {
        if (StringUtils.isEmpty(courseId)){
            new RuntimeException("数据错误");
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseBase courseBase = optional.get();
        return courseBase;
    }

    /**
     * 添加课程基本信息
     * @param courseBase
     * @return
     */
    @Transactional
    public ResponseResult addCourseBase(CourseBase courseBase) {
        CourseBase save = courseBaseRepository.save(courseBase);
        return ResponseResult.SUCCESS();
    }

    /**
     * 更新课程基本信息
     * @param courseBase
     * @return
     */
    @Transactional
    public ResponseResult updateCourseBase(CourseBase courseBase) {
        courseBaseRepository.save(courseBase);
        return ResponseResult.SUCCESS();
    }
}
