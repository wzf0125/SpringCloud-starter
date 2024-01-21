package org.quanta.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName role_permission
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="role_permission")
public class RolePermission implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty("角色id")
    private Integer roleId;

    /**
     * 权限id
     */
    @TableField(value = "permission_id")
    @ApiModelProperty("权限id")
    private Integer permissionId;

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