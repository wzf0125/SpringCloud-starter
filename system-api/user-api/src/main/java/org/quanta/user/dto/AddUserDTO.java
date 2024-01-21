package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Description: 添加用户
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDTO {
    @NotBlank
    @ApiModelProperty("账号")
    private String account;

    @NotBlank
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank
    @ApiModelProperty("密码")
    private String password;

    @NotBlank
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("拓展信息")
    private UserExtDTO ext;
}
