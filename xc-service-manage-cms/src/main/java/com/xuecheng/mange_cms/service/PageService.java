package com.xuecheng.mange_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.mange_cms.config.RabbitmqConfig;
import com.xuecheng.mange_cms.dao.CmsConfigRepository;
import com.xuecheng.mange_cms.dao.CmsPageRepository;
import com.xuecheng.mange_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 码农界的小学生
 * @description:
 * @title: PageService
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/22 23:21
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     *查询列表
     * @param page 页码从1开始
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        //验证
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }
        //自定义查询条件
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //设置条件值
        //设置站点id查询
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //设置模板id查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //设置页面别名查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //实现自定义条件查询
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //分页参数
        if(page <= 0){
            page = 1;
        }
        if(size <= 0){
            size = 10;
        }
        page = page -1;
        //实现分页
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        //实例化返回对象
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        //返回定义的响应结果
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    /**
     * 添加页面
     * @param cmsPage 页面信息
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage){
        //校验页面名称，站点id，页面webPath的唯一性
        //根据页面名称，站点id，页面webPath去集合查询，查到就说明存在，不能添加，返之继续添加
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        /*if(cmsPage1 == null){
            //调用dao新增页面
            //设置主键为空 让其自增
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }else {
            //添加失败
            return new CmsPageResult(CommonCode.FAIL,null);
        }*/
        if(cmsPage1 != null){
            //页面已存在
            //抛出异常，已存在内容
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    public CmsPage findById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id,CmsPage cmsPage){
        //根据id从数据库查询信息
        CmsPage cmsPage1 = this.findById(id);
        if(cmsPage1 !=null){
            //准备更新
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            cmsPage1.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            cmsPage1.setPageName(cmsPage.getPageName());
            //更新访问路径
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageRepository.save(cmsPage1);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage1);
        }else {
            return new CmsPageResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 根据id删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id){
        //先查询一下
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 根据id查询cmsConfig
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> byId = cmsConfigRepository.findById(id);
        if(byId.isPresent()){
            CmsConfig cmsConfig = byId.get();
            return cmsConfig;
        }
        return null;
    }

    /**
     * 页面静态化方法
     * @param pageId
     * @return
     */
    //静态化程序获取页面的DataUrl
    // 3、静态化程序远程请求DataUrl获取数据模型。
    // 4、静态化程序获取页面的模板信息
    // 5、执行页面静态化
    public String getPageHtml(String pageId) throws IOException {
        //获取数据模型
        Map model = this.getModelByPageId(pageId);
        if(model == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取模板内容
        String content = this.getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(content)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = this.generateHtml(model, content);
        if(html == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    //获取数据模型
    private Map getModelByPageId(String pageId){
        //取出页面信息
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage == null){
            //页面为空
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //取出页面dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            //页面dataUrl为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过restTemplate请求dataUrl获取数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }
    //获取页面的模板信息
    private String getTemplateByPageId(String pageId) throws IOException {
        //取出页面信息
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage == null){
            //页面为空
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取页面模板id
        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //查询模板信息
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            System.out.println(gridFSFile);
            //打开一个下载流
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource对象 获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            //从流中获取数据
            String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            return content;
        }
        return null;
    }

    //执行静态化
    private String generateHtml(Map model,String templateContent){
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateContent);
        //向configuration配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try{
            Template template = configuration.getTemplate("template");
            //调用api进行静态化
            String templateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return templateIntoString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *页面发布
     * @param pageId
     * @return
     */
    public ResponseResult post(String pageId) throws IOException {
        //执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        //将得到的静态化页面保存到GridFS
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        //向mq发送消息
        this.sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 将html保存到GridFS中
     * @param pageId
     * @param htmlContent
     * @return
     */
    private CmsPage saveHtml(String pageId,String htmlContent){
        //获取到页面信息
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try{
            //将html页面转换成输入流
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            //将html保存到GridFS中
             objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        }catch (Exception e){
            e.printStackTrace();
        }
        //将html存储在gridFS的id保存到cmspage中
        cmsPage.setHtmlFileId(objectId.toString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    /**
     * 向mq发送消息
     * @param pageId
     */
    private void sendPostPage(String pageId){
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("pageId",pageId);
        String msg = JSON.toJSONString(map);
        //发送给mq 参数 交换机 routingkey为站点id pageId
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),msg);
    }

}
