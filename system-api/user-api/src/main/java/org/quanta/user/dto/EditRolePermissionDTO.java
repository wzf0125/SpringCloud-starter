package org.quanta.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description: 添加角色权限
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRolePermissionDTO {
    @NotNull
    private Integer roleId;
    @NotEmpty
    private List<@NotNull Integer> permissionIdList;
}
