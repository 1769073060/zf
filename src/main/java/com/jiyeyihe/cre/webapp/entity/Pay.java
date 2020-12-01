package com.jiyeyihe.cre.webapp.entity;


import lombok.Data;

@Data
public class Pay {
    public String openid;
    private Long id;
    private Long customerViewingRecordId;
    private Long userId;
    private Integer type;
    private String ordersNo;
    private String frontIdCardUrl;
    private String backIdCardUrl;
    private String contractUrl;
    private String idCard;
    private String contactName;
    private String phoneNumber;
}
