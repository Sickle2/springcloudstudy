package com.sickle.servicesecurityoauth2.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: lianhai
 * Date: 2019-02-19 18:12
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Response {

    private Meta meta;
    private Object data;

    public Response success() {
        this.meta = new Meta(ResponseMeta.SUCCESS);
        return this;
    }

    public Response success(Object data) {
        this.meta = new Meta(ResponseMeta.SUCCESS);
        this.data = data;
        return this;
    }

    public Response failure(ResponseMeta meta) {
        this.meta = new Meta(meta);
        return this;
    }

    public Response failure(ResponseMeta meta, Object data) {
        this.meta = new Meta(meta);
        this.data = data;
        return this;
    }

    @Getter
    @Setter
    public class Meta {
        private Integer code;
        private String message;

        Meta(ResponseMeta meta) {
            this.code = meta.code();
            this.message = meta.message();
        }
    }
}
