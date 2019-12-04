package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 码农界的小学生
 * @description: 统一的异常捕获类
 * @title: ExceptionCatch
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/10/27 13:59
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    private static final Logger
            LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //定义map，配置异常类型所对应的错误代码
    //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResponseResult> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResponseResult> builder = ImmutableMap.builder();
    //捕获CustomException此类异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录日志
        LOGGER.error("catch exception:{}",customException.getResultCode());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());
        //如果EXCEPTIONS没有构建，为空
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }
        //从EXCEPTIONS中查看是否有对应的异常，如果有则返回该错误信息，反之返回99999
        ResponseResult responseResult = EXCEPTIONS.get(Exception.class);
        if(responseResult != null){
            return responseResult;
        }

        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
    static {
        //定义了异常信息所对应的代码
        builder.put(HttpMessageNotReadableException.class,new ResponseResult(CommonCode.INVALID_PARAM));
    }
}
