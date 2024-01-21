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
 * @TableName user
 */
@Data
@Builder
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty("账号")
    private String account;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty("密码")
    private String password;

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