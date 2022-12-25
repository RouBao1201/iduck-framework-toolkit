package com.iduck.sybin.toolkit.response.model;

/**
 * 响应结果统一返回体
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public class RespResult<T> extends BaseResp {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespResult{" +
                "data=" + data +
                '}';
    }
}
