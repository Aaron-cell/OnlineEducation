package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.mange_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsConfigController
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/31 21:54
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    PageService pageService;

    @GetMapping("/getmodel/{id}")
    @Override
    public CmsConfig getmodel(@PathVariable("id") String id) {
        CmsConfig cmsConfig = pageService.getConfigById(id);
        return cmsConfig;
    }

}
