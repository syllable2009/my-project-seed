package com.company.project.core;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.company.project.exception.BusinessException;
import com.company.project.i18n.MessageSources;
import com.company.project.utils.ObjectMapperSingleton;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2019-03-04
 */
@Slf4j
@Data
@Component
public class ResultUtils {

    @Resource
    private MessageSources messageSources;

    private static MessageSources messageSourcesMap;

    @PostConstruct
    public void init() {
        messageSourcesMap = messageSources;
    }

    /**
     * 构建成功返回的结构体
     *
     * @return 成功返回结果
     */
    public static Result success() {
        return Result.of(ResultCodeEnum.SUCCESS.getCode(),
                messageSourcesMap.get(ResultCodeEnum.SUCCESS.getMessage()), null);
    }

    /**
     * 构建成功返回的结构体
     *
     * @param result 结果信息
     * @return 成功返回结果
     */
    public static <T> Result<T> success(T result) {
        return Result.of(ResultCodeEnum.SUCCESS.getCode(),
                messageSourcesMap.get(ResultCodeEnum.SUCCESS.getMessage()), result);
    }

    /**
     * 构建成功返回的结构体
     *
     * @param msg 返回的消息
     * @return 成功返回结果
     */
    public static Result msgSuccess(String msg) {
        return Result.of(ResultCodeEnum.SUCCESS.getCode(), msg, null);
    }

    /**
     * 构建成功返回的结构体
     *
     * @param msg 返回的消息
     * @param result 结果信息
     * @return 成功返回结果
     */
    public static <T> Result<T> success(String msg, T result) {
        return Result.of(ResultCodeEnum.SUCCESS.getCode(), msg, result);
    }

    /**
     * 构建失败返回的结构体
     *
     * @param msg 失败信息
     * @return 失败返回结果
     */
    public static <T> Result<T> failure(String msg) {
        return Result.of(ResultCodeEnum.FAIL.getCode(), msg, null);
    }

    /**
     * 构建失败返回的结构体
     *
     * @param msg 失败信息
     * @param result 结果信息
     * @return 失败返回结果
     */
    public static <T> Result<T> failure(String msg, T result) {
        return Result.of(ResultCodeEnum.FAIL.getCode(), msg, result);
    }

    /**
     * 构建失败返回的结构体
     *
     * @param code 失败code
     * @param msg 失败信息
     * @return 失败返回结果
     */
    private static <T> Result fail(Integer code, String msg) {
        return Result.of(code, msg, null);
    }

    /**
     * 构建失败返回的结构体
     *
     * @param code 失败code
     * @param msg 失败信息
     * @param result 结果信息
     * @return 失败返回结果
     */
    public static <T> Result failure(Integer code, String msg, T result) {
        return Result.of(code, msg, result);
    }

    /**
     * 任何未被捕获的异常才使用本方法
     *
     * @return 系统错误返回结果
     */
    public static <T> Result sysError() {
        return Result.of(ResultCodeEnum.FAIL.getCode(),
                messageSourcesMap.get(ResultCodeEnum.FAIL.getMessage()), null);
    }

    /**
     * 返回可分页的范性数据
     *
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param total 总数
     * @param result 数据
     * @return 结果
     */
    public static <T> PageableResult<T> pager(Integer pageNum, Integer pageSize, Long total,
            List<T> result) {
        return PageableResult.of(pageNum, pageSize, total, result, null);
    }

    /**
     * 返回可分页的范性数据
     *
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param total 总数
     * @param result 数据
     * @param sum 全量
     * @return 结果
     */
    public static <T> PageableResult<T> pagerWithSum(Integer pageNum, Integer pageSize, Long total,
            List<T> result, Long sum) {
        return PageableResult.of(pageNum, pageSize, total, result, sum);
    }

    /**
     * 输出带状态码的json返回值
     *
     * @param statusCode 返回状态码
     * @param msg 错误提示
     * @param response response对象
     */
    public static void responseError(int statusCode, String msg, HttpServletResponse response)
            throws IOException {
        response(statusCode, ResultUtils.failure(msg), response);
    }

    /**
     * 输出带状态码和错误码的json返回值
     *
     * @param statusCode 返回状态码
     * @param errorCode 错误码
     * @param msg 错误提示
     * @param response response对象
     */
    public static void responseError(int statusCode, int errorCode, String msg,
            HttpServletResponse response) throws IOException {
        response(statusCode, ResultUtils.fail(errorCode, msg), response);
    }

    /**
     * 输出带状态码的json返回值
     *
     * @param msg 成功
     * @param response response对象
     */
    public static void responseSuccess(String msg, HttpServletResponse response)
            throws IOException {
        response(HttpServletResponse.SC_OK, ResultUtils.success(msg), response);
    }

    /**
     * 输出带状态码的json
     *
     * @param statusCode 状态码
     * @param Result 封装的结果
     */
    private static void response(int statusCode, Result Result, HttpServletResponse response)
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = response.getWriter();
        out.write(ObjectMapperSingleton.obj2string(Result));
        out.flush();
        out.close();
    }

    public static <T> Result<T> toResult(Supplier<T> supplier) {
        try {
            return ResultUtils.success(supplier.get());
        } catch (BusinessException e) {
            log.info("business exception:{},{}", e.getCode(), e.getMessage());
            return ResultUtils.fail(e.getCode(), e.getMessage());
        } catch (DataAccessException e) {
            log.error("database error", e);
            return ResultUtils.fail(ResultCodeEnum.FAIL.getCode(), "system error.");
        } catch (Exception e) {
            log.error("unknown exception", e);
            return ResultUtils.fail(ResultCodeEnum.SYSTEM_ERROR.getCode(), "system error.");
        }
    }

}
