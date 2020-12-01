package com.jiyeyihe.cre.webapp.entity;

import lombok.Data;

/**
 * @author ：rzk
 * @date ：Created in 2020/10/27 2:25
 * @description：返回结果信息
 * @modified By：
 * @version: 1.0$
 */
@Data
public class Result {
    /** 返回的和状态码 */
    private Integer code;
    /** 提示信息 */
    private String msg;
    /** 返回的数据 */
    private String data;

}
