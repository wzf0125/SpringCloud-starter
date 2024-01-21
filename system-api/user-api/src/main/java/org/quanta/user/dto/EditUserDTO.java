package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {
    @NotNull
    @ApiModelProperty("用户id")
    private Integer id;

    @NotBlank
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank
    @ApiModelProperty("密码")
    private String password;

    @NotBlank
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户拓展信息")
    private UserExtDTO ext;
}
