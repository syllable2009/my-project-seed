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
    SUCCESS(0, "success"), FAIL(100, "fail"), REQ_PARAM_ERROR(400, "request.param.error");

    private Integer code;
    private String message;

}
