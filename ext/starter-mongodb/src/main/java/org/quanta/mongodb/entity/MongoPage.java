package org.quanta.mongodb.entity;

import lombok.Data;

/**
 * Description: mongodb分页查询对象
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/28
 */
@Data
public class MongoPage {
    /**
     * 数据总量
     */
    private Integer total;

    /**
     * 页面数量
     */
    private Integer size;

    /**
     * 分页数据
     */
    private Object data;
}