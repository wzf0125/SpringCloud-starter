package org.quanta.core.beans;

import cn.hutool.core.collection.ListUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 分页查询结果
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryResult<T> {
    List<T> data;
    Long total;
    Long pages;
    Long currentPage;
    Long pageSize;

    public static <T> PageQueryResult<T> empty(Long currentPage, Long pageSize) {
        return PageQueryResult.<T>builder()
                .data(ListUtil.empty())
                .total(0L)
                .pages(0L)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}
