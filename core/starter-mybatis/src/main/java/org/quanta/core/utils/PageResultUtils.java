package org.quanta.core.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.quanta.core.beans.PageQueryResult;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/31
 */
public class PageResultUtils {


    /**
     * 转换分页查询对象
     */
    public static <T> PageQueryResult<T> buildPageQueryResult(IPage<T> pageData) {
        long pages = (pageData.getTotal() / pageData.getSize()) + ((pageData.getTotal() % pageData.getSize()) == 0 ? 0 : 1);
        return PageQueryResult.<T>builder()
                .data(pageData.getRecords())
                .currentPage(pageData.getCurrent())
                .pageSize(pageData.getSize())
                .total(pageData.getTotal())
                .pages(pageData.getPages())
                .build();
    }

    /**
     * 转换分页查询对象
     */
    public static <T> PageQueryResult<T> buildPageQueryResult(List<T> data, Long currentPage, Long pageSize, Long total) {
        long pages = (total / pageSize) + ((total % pageSize) == 0 ? 0 : 1);
        return PageQueryResult.<T>builder()
                .data(data)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .total(total)
                .pages(pages)
                .build();
    }
}
