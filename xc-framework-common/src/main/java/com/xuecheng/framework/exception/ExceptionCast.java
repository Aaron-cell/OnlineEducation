package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author 码农界的小学生
 * @description:
 * @title: ExceptionCast
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/27 13:56
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
