package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;

/**
 * @author 码农界的小学生
 * @description:
 * @title: QueryPageRequest
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/21 11:02
 */
@Data
public class QueryPageRequest {
    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;
}
