package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.mange_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsPageController
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/22 20:33
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,
            @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
       /* //暂时用静态数据
        //定义
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        List<CmsPage> list = new ArrayList<>();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        queryResult.setTotal(1);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;*/
       return pageService.findList(page,size,queryPageRequest);
    }
    //前端请求post数据提交的是json数据
    @PostMapping("/add")
    @Override
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @GetMapping("/get/{id}")
    @Override
    public CmsPage findById(@PathVariable("id") String id) {
        System.out.println(id);
        return pageService.findById(id);
    }

    @PutMapping("/edit/{id}") //更新使用put方法 传递的参数为json数据
    @Override
    public CmsPageResult edit(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {
        return pageService.edit(id,cmsPage);
    }

    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) throws IOException {
        ResponseResult result = pageService.post(pageId);
        return result;
    }
}
