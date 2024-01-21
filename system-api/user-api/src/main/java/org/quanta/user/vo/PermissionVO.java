package org.quanta.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 接口权限响应对象
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVO {

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
     * 路径
     */
    @ApiModelProperty("路径")
    private String path;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String desc;

    /**
     * 是否持有该权限
     */
    @ApiModelProperty("是否持有该权限")
    private Boolean hasPermission;

    /**
     * 子权限列表
     */
    @ApiModelProperty("子权限列表")
    private List<PermissionVO> subPermission;
}
