package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author 码农界的小学生
 * @description:
 * @title: CustomException
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/27 13:45
 */
public class CustomException extends RuntimeException{
    //runtimeException 运行时异常  好处对代码的没有侵入性
    ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public ResultCode  getResultCode(){
        return resultCode;
    }
}
