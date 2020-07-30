package com.company.project.exception;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.project.core.BusinessErrorInterface;
import com.company.project.core.Result;
import com.company.project.core.ResultCodeEnum;
import com.company.project.core.ResultUtils;
import com.company.project.i18n.MessageSources;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Resource
    private MessageSources messageSources;
    private static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("GlobalExceptionAdvice->有未被捕获的异常信息!", e);
        return ResultUtils.sysError();
    }

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        BusinessErrorInterface error = ((BusinessException) e).getBusinessErrorInterface();
        log.error("GlobalExceptionAdvice->handleBusinessException", e);
        if (Objects.nonNull(error)) {
            return ResultUtils.failure(messageSources.get(error.getMessage()));
        }
        return ResultUtils.failure(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e) {
        return ResultUtils.failure(ResultCodeEnum.UNAUTHORIZED.getMessage());
    }

    /**
     * 统一 页面 异常处理
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【DemoPageException】:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message", exception.getMessage());
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }
}
