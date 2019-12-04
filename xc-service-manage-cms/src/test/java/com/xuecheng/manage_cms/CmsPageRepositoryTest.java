package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.mange_cms.MangeCmsApplication;
import com.xuecheng.mange_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsPageRepositoryTest
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/22 21:50
 */
@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }
    //测试分页查询
    @Test
    public void testFindPate(){
        //分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }
    //测试修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> byId = cmsPageRepository.findById("5abefd525b05aa293098fca6");
        if(byId.isPresent()){
            CmsPage cmsPage = byId.get();
            cmsPage.setPageAliase("test1");
            cmsPageRepository.save(cmsPage);
        }
    }
    //查找
    @Test
    public void testCheck(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("index2.html");
        System.out.println(cmsPage.toString());
    }
    //条件查找 站点id 精确查找
    @Test
    public void testFindAllByExample(){
        //分页参数
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        /*//查询站点id 5a751fab6abb5044e0d19ea1 的页面
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //如果想要同时通过模板id精确查找
        cmsPage.setTemplateId("5aec5dd70e661808240ab7a6");*/
        cmsPage.setPageAliase("test");
        //条件匹配器
        /*ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());*/
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> list = all.getContent();
        System.out.println(list);
    }
}
