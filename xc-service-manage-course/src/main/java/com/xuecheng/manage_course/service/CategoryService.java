package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CategoryService
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/24 21:32
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public CategoryNode findList() {
        CategoryNode list = categoryMapper.findList();
        System.out.println(list);
        return list;
    }
}
