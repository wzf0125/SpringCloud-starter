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
 * @TableName user_info
 */
@Data
@Builder
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_info")
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    @ApiModelProperty("角色id")
    private Integer uid;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 性别
     */
    @TableField(value = "gender")
    @ApiModelProperty("性别")
    private String gender;

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