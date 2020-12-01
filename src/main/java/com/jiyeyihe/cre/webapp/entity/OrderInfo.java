package com.jiyeyihe.cre.webapp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * fileName:OrderInfo
 * description:
 * author:rzk
 * createTime:2020/10/27 10:24
 * versioni:1.0.0
 */
@Data
public class OrderInfo implements Serializable {
    private String appid;// 小程序ID
    private String mch_id;// 商户号
    private String nonce_str;// 随机字符串
    private String sign_type;//签名类型
    private String sign;// 签名
    private String body;// 商品描述
    private String detail;//商品详情
    private String out_trade_no;// 商户订单号
    private int total_fee;// 总标价金额 ,单位为分
    private String spbill_create_ip;// 终端IP
    private String notify_url;// 通知地址
    private String trade_type;// 交易类型
    private String openid;//用户标识
    private String order_no;//商户订单号
    private String time_start;//交易起始时间
    private String time_expire;//交易结束时间


}
