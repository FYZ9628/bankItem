package com.example.opct_notice.notice.enums;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:26
 */
public enum  NoticeAppIdEnum {
    OPCT_NOTICE("opctReceiveNotice", "运营中心通知服务", "OPCT"),
    BLCT_NOTICE("blctReceiveNotice", "票据中心通知服务", "BLCT"),
    SVCT_NOTICE("svctReceiveNotice", "存款中心通知服务", "SVCT"),
    ;

    NoticeAppIdEnum(String servCode, String servName, String appId) {
        this.servCode = servCode;
        this.servName = servName;
        this.appId = appId;
    }

    /**
     * 服务名称
     */
    private String servName;

    /**
     * 通知服务码
     */
    private String servCode;

    /**
     * 应用ID
     */
    private String appId;

    public String getServName() {
        return servName;
    }

    public void setServName(String servName) {
        this.servName = servName;
    }

    public String getServCode() {
        return servCode;
    }

    public void setServCode(String servCode) {
        this.servCode = servCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public static String queryServCode(String appId) {
        for (NoticeAppIdEnum noticeAppIdEnum : values()) {
            if (noticeAppIdEnum.getAppId().equals(appId)) {
                return noticeAppIdEnum.getServCode();
            }
        }
        return null;
    }
}
