package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class EditPermissionDTO {
    @NotNull
    @ApiModelProperty("权限id")
    private Integer id;
    @NotBlank
    @ApiModelProperty("权限路径")
    private String path;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("描述")
    private String desc;
}
