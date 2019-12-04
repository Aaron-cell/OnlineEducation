package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 码农界的小学生
 * @description:
 * @title: SysDictionaryControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 10:27
 */
@Api(value = "数据字典接口",tags = {"提供数据字典的管理，查询功能"})
public interface SysDictionaryControllerApi {
    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String type);
}
