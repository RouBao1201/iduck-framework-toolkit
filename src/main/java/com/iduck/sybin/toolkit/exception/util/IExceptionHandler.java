package com.iduck.sybin.toolkit.exception.util;

import com.iduck.sybin.toolkit.exception.enums.ExceptionEnum;
import com.iduck.sybin.toolkit.exception.model.BaseException;
import com.iduck.sybin.toolkit.exception.model.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 异常处理器
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public class IExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(IExceptionHandler.class);

    /**
     * 抛出业务异常
     *
     * @param message 异常信息
     */
    public static void pushServiceExc(String message) {
        pushException(ExceptionEnum.SERVICE_EXCEPTION.getCode(), message, ServiceException.class);
    }

    /**
     * 抛出业务异常
     */
    public static void pushServiceExc() {
        pushException(ExceptionEnum.SERVICE_EXCEPTION.getCode(), ExceptionEnum.SERVICE_EXCEPTION.getMessage(), ServiceException.class);
    }

    /**
     * 抛出异常
     *
     * @param code     异常编码
     * @param localMsg 异常自定义信息
     * @param clazz    异常类class
     * @param <T>      异常类 extends BaseException
     */
    public static <T extends BaseException> void pushException(String code, String localMsg, Class<T> clazz) {
        T instance = instance(clazz);
        instance.setCode(code);
        instance.setLocalMsg(localMsg);
        throw instance;
    }

    /**
     * 根据class创建对应异常类
     *
     * @param clazz class
     * @param <T>   extends BaseException
     * @return exception
     */
    private static <T extends BaseException> T instance(Class<T> clazz) {
        T t = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            t = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | NoSuchMethodException | InvocationTargetException e) {
            log.error("IExceptionHandler => Instance error. init baseException to default.");
            return (T) new BaseException();
        }
        return t;
    }

    private IExceptionHandler() {

    }
}
