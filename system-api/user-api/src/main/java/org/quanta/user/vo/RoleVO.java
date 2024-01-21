package org.quanta.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO {
    /**
     * 权限id
     */
    @ApiModelProperty("节点id")
    private Integer id;

    /**
     * 父节点id
     */
    @ApiModelProperty("父节点id")
    private Integer parentId;

    /**
     * 父节点名称
     */
    @ApiModelProperty("父节点名称")
    private String parentName;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String desc;

    /**
     * 是否有该角色
     */
    @ApiModelProperty("是否为该角色")
    private Boolean hasRole;

    /**
     * 子权限列表
     */
    @ApiModelProperty("子权限列表")
    private List<RoleVO> subRoleList;
}
