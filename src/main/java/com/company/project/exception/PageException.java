package com.company.project.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 页面异常
 * </p>
 *
 * @package: com.xkcoding.exception.handler.exception
 * @description: 页面异常
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:18 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageException extends RuntimeException {

}
