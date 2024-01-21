package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description: 编辑角色
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRoleDTO {
    @NotNull
    @ApiModelProperty("id")
    private Integer id;

    @NotBlank
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("描述")
    private String desc;
}
