package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Description: 添加权限
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPermissionDTO {
    @NotBlank
    @ApiModelProperty("权限路径")
    private String path;
    @NotBlank
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("父级节点id")
    private Integer patentId;
    @ApiModelProperty("描述")
    private String desc;
}
