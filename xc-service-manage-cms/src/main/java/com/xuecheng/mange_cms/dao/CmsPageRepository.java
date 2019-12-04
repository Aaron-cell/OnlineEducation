package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsPageRepository
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/22 21:45
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //自定义方法查询
    //根据页面名称查询
    CmsPage findByPageName(String pageName);
    //根据页面名称，站点id，页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String PageWebPath);

}
