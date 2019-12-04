package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

/**
 * @author 码农界的小学生
 * @description:Cms页面的Api
 * @title: CmsPageControllerApi
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/21 11:20
 */
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",required = true, paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "size",value = "每页数量",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
    //新增页面
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);
    //修改页面
    //1.根据页面id查询页面信息
    @ApiOperation("根据页面id查询页面信息")
    @ApiImplicitParam(name = "id",value = "页面id",required = true,paramType = "path",dataType = "String")
    public CmsPage findById(String id);
    //2.根据id，修改页面
    @ApiOperation("修改页面")
    @ApiImplicitParam(name = "id",value = "页面id",required = true,paramType = "path",dataType = "String")
    public CmsPageResult edit(String id,CmsPage cmsPage);
    //删除页面
    @ApiOperation("删除页面")
    @ApiImplicitParam(name = "id",value = "页面id",required = true,paramType = "path",dataType = "String")
    public ResponseResult delete(String id);
    //页面发布
    @ApiOperation("页面发布")
    @ApiImplicitParam(name = "pageId",value = "页面id",required = true,paramType = "path",dataType = "String")
    public ResponseResult post(String pageId) throws IOException;
}
