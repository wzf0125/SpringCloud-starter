package org.quanta.core.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/31
 */
@Setter
@Builder
public class PageQueryDO {
    @Getter
    private Long currentPage;
    @Getter
    private Long pageSize;
    private Long start; // 分页起始位置

    public Long getStart() {
        if (start == null || start == 0) {
            start = pageSize * (currentPage - 1);
        }
        return start;
    }
}
