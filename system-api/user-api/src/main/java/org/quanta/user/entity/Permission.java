package org.quanta.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName permission
 */
@Data
@Builder
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "permission")
public class Permission implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 父级节点id
     */
    @TableField(value = "`parent_id`")
    @ApiModelProperty("父级节点id")
    private Integer parentId;

    /**
     * 权限名称
     */
    @TableField(value = "name")
    @ApiModelProperty("权限名称")
    private String name;

    /**
     * 权限路径
     */
    @TableField(value = "path")
    @ApiModelProperty("权限路径")
    private String path;


    /**
     * 描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty("描述")
    private String desc;

    /**
     * 逻辑删除
     */
    @JsonIgnore
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}