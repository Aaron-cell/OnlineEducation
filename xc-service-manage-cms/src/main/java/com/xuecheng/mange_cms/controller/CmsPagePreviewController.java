package com.xuecheng.mange_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.mange_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsPagePreviewController
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/3 22:13
 */
@Controller
public class CmsPagePreviewController extends BaseController {
    @Autowired
    PageService pageService;
    //页面预览
    @RequestMapping(value = "/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId) throws IOException {
        //执行静态化
        String pageHtml = pageService.getPageHtml(pageId);
        //通过response对象将内容输出
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pageHtml.getBytes("utf-8"));
    }
}
