package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsConfigControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/31 21:40
 */
@Api(value="cms配置管理接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据id查询cms配置信息")
    public CmsConfig getmodel(String id);
}
