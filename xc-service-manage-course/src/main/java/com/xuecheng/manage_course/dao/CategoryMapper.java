package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CategoryMapper
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/24 21:34
 */
@Mapper
public interface CategoryMapper {
    public CategoryNode findList();
}
