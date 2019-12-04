package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CmsConfigRepository
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/31 21:48
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
