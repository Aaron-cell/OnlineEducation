package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CategoryControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/24 21:19
 */
@Api(value = "课程分类管理",tags = {"课程分类管理"})
public interface CategoryControllerApi {
    @ApiOperation("分类查询")
    public CategoryNode findList();
}
