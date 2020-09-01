package com.company.project.core;


import com.company.project.utils.ObjectMapperSingleton;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应结果封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude
public class Result<T> {
    private int code;
    private String message;
    private T data;

    @Override
    public String toString() {
        return ObjectMapperSingleton.obj2string(this);
    }


}
