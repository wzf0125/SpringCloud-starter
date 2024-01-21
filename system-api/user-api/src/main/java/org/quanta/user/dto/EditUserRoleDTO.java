package org.quanta.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description: 编辑用户角色
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRoleDTO {
    @NotNull
    private Integer userId;

    @NotEmpty
    private List<@NotNull Integer> roleIdList;
}
