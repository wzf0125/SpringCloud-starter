package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 添加用户拓展信息
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExtDTO {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别")
    private String gender;
}
