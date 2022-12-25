package com.iduck.sybin.toolkit.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础响应体
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public class BaseResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Date respTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String throwErrorMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public String getThrowErrorMsg() {
        return throwErrorMsg;
    }

    public void setThrowErrorMsg(String throwErrorMsg) {
        this.throwErrorMsg = throwErrorMsg;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", respTime=" + respTime +
                ", throwErrorMsg='" + throwErrorMsg + '\'' +
                '}';
    }
}
