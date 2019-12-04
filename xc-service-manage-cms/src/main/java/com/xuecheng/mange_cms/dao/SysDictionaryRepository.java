package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 码农界的小学生
 * @description:
 * @title: SysDictionaryRepository
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 10:26
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    public SysDictionary findByDType(String type);
}
