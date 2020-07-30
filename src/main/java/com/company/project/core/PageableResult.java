package com.company.project.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResult<T> {

    @ApiModelProperty("页码")
    private Integer pageNo = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 20;

    @ApiModelProperty("数据总条数")
    private Long total;

    @ApiModelProperty("数据列表")
    private List<T> content;

    @ApiModelProperty("全部合计数量")
    private Long sum;
}
