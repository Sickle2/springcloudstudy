package com.sickle.servicesecurityoauth2.domain;

/**
 * Author: lianhai
 * Date: 2019-02-19 18:15
 */
public enum ResponseMeta {

    SUCCESS(0, "操作成功"),
    FAILURE(-1, "请求失败"),

    LOGOUT_SUCCESS(2001000, "退出成功"),

    EMAIL_NOT_FOUND(2003001, "账号不存在"),
    PASSWORD_ERROR(2003002, "密码错误"),
    USER_DISABLED(2003003, "该用户被禁用");

    private Integer code;
    private String message;

    ResponseMeta(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}