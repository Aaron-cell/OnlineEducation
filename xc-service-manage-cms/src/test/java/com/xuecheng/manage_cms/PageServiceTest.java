package com.xuecheng.manage_cms;

import com.xuecheng.mange_cms.MangeCmsApplication;
import com.xuecheng.mange_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author 码农界的小学生
 * @description:
 * @title: PageServiceTest
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/1 22:08
 */
@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    PageService pageService;
    @Test
    public void testGetPageHtml() throws IOException {
        String pageHtml = pageService.getPageHtml("5db2925db267ae1c80444af4");
        System.out.println(pageHtml);
    }
}
