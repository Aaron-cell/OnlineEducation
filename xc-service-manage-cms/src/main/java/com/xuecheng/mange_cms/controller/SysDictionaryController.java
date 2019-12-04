package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.SysDictionaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.mange_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 码农界的小学生
 * @description:
 * @title: SysDictionaryController
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/25 10:31
 */
@RestController
@RequestMapping("/sys")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    private SysDictionaryService sysDictionaryService;
    //查询数据字典
    @Override
    @GetMapping("/dictionary/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysDictionaryService.getByType(type);
    }
}
