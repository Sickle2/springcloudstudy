package com.sickle.servicesecurityoauth2.exception;

import com.sickle.servicesecurityoauth2.domain.Response;
import com.sickle.servicesecurityoauth2.domain.ResponseMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;


/**
 * Author: lianhai
 * Date: 2019-05-08 15:52
 */
@Slf4j
@Component
public class MyWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity<Response> translate(Exception e) {
        Response response = new Response();
        ResponseEntity<Response> entity = new ResponseEntity<>(response, HttpStatus.OK);
        MyOauthException exception = (MyOauthException) e;

        log.debug("LoginError:{}", exception.code);

        if (exception.code == MyOauthException.Type.BadClientCredentials.code()) {
            response.failure(ResponseMeta.PASSWORD_ERROR);
            return entity;
        } else if (exception.code == MyOauthException.Type.UserDisabled.code()) {
            response.failure(ResponseMeta.USER_DISABLED);
            return entity;
        }
        response.failure(ResponseMeta.FAILURE);
        return entity;
    }
}