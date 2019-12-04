package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.mange_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 码农界的小学生
 * @description:
 * @title: SysDictionaryService
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 10:34
 */
@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    /**
     * 根据类型查询数据字典
     * @param type
     * @return
     */
    public SysDictionary getByType(String type) {
        SysDictionary sys = sysDictionaryRepository.findByDType(type);
        if(sys == null){
            //该类型不存在
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return sys;
    }
}
