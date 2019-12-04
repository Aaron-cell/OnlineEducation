package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 码农界的小学生
 * @description:页面模板dao层
 * @title: CmsTemplateRepository
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/1 20:41
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
