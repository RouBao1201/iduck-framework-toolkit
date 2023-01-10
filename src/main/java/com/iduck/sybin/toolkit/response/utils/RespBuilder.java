package com.iduck.sybin.toolkit.response.utils;

import com.iduck.sybin.toolkit.response.enums.RespEnum;
import com.iduck.sybin.toolkit.response.model.RespResult;

import java.util.Date;

/**
 * 响应体构造器
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public class RespBuilder {

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> success(T data) {
        return build(RespEnum.SUCCESS.getCode(), RespEnum.SUCCESS.getMessage(), data, null);
    }

    /**
     * 成功响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> success(String message, T data) {
        return build(RespEnum.SUCCESS.getCode(), message, data, null);
    }


    /**
     * 失败响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> fail(T data) {
        return build(RespEnum.FAIL.getCode(), RespEnum.FAIL.getMessage(), data, null);
    }

    /**
     * 失败响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> fail(String message, T data) {
        return build(RespEnum.FAIL.getCode(), message, data, null);
    }

    /**
     * 异常响应
     *
     * @param data 响应数据
     * @param <T>  响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> error(T data) {
        return build(RespEnum.ERROR.getCode(), RespEnum.ERROR.getMessage(), data, null);
    }

    /**
     * 异常响应
     *
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> error(String message, T data) {
        return build(RespEnum.ERROR.getCode(), message, data, null);
    }

    /**
     * 异常响应
     *
     * @param message       响应信息
     * @param data          响应数据
     * @param throwErrorMsg 抛出的异常信息
     * @param <T>           响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> error(String message, T data, String throwErrorMsg) {
        return build(RespEnum.ERROR.getCode(), message, data, throwErrorMsg);
    }

    /**
     * 基础响应
     *
     * @param code    响应编码
     * @param message 响应信息
     * @param data    响应数据
     * @param <T>     响应数据枚举类型
     * @return BaseResp
     */
    public static <T> RespResult<T> build(String code, String message, T data, String throwErrorMsg) {
        RespResult<T> resp = new RespResult<>();
        resp.setData(data);
        resp.setRespTime(new Date());
        resp.setCode(code);
        resp.setMessage(message);
        resp.setThrowErrorMsg(throwErrorMsg);
        return resp;
    }

    private RespBuilder() {

    }
}
