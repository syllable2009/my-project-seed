package com.company.project.core;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiaxiaopeng
 * 2020-04-21 20:10
 **/
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    /**
     * 错误码
     */
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    REQ_PARAM_ERROR(400, "request.param.error"),
    NOT_FOUND(404, "resource.not.found"),
    SYSTEM_ERROR(500, "server.error"),
    UNAUTHORIZED(403, "");
    private Integer code;
    private String message;

}
