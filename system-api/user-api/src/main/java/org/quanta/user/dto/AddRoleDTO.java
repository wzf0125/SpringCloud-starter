package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Description: 添加角色
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleDTO {
    /**
     * 父级角色id
     */
    @ApiModelProperty("父级角色id")
    private Integer parentId;

    /**
     * 角色名称
     */
    @NotBlank
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("描述")
    private String desc;
}
