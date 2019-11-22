package com.sickle.servicesecurityoauth2.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @program: springcloudDemo
 * @description: OAuth2 自定义异常类
 * @author: sickle
 * @create: 2019-11-21 17:02
 **/
public class MyOauthException extends OAuth2Exception {
    public int code;

    public enum Type {
        EmailNotFound(1),
        BadClientCredentials(2),
        UserDisabled(3);

        private int code;

        Type(int code) {
            this.code = code;
        }

        public int code() {
            return this.code;
        }
    }


    public MyOauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyOauthException(String msg) {
        super(msg);
    }

    public MyOauthException(Type type, String msg) {
        super(msg);
        this.code = type.code;
    }
}
