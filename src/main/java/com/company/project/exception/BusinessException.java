package com.company.project.exception;

import com.company.project.core.BusinessErrorInterface;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    private BusinessErrorInterface businessErrorInterface;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessErrorInterface businessErrorInterface) {
        super(businessErrorInterface.getMessage());
        this.businessErrorInterface = businessErrorInterface;
    }

    public BusinessException(String message, BusinessErrorInterface businessErrorInterface) {
        super(message);
        this.businessErrorInterface = businessErrorInterface;
    }

    public void setBusinessErrorInterface(BusinessErrorInterface businessErrorInterface) {
        this.businessErrorInterface = businessErrorInterface;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BusinessErrorInterface getBusinessErrorInterface() {
        return businessErrorInterface;
    }
}
