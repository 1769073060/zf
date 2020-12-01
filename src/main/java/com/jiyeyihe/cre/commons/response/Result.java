package com.jiyeyihe.cre.commons.response;

import com.jiyeyihe.cre.consts.MsgConsts;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    /**
     * 返回代码
     */
    private int code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
