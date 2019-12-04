package com.xuecheng.manage_cms;

import com.xuecheng.mange_cms.MangeCmsApplication;
import com.xuecheng.mange_cms.dao.CmsConfigRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author 码农界的小学生
 * @description:
 * @title: RestTemplate
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/31 22:09
 */
@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class RestTemplateTest {
    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);

    }
}
