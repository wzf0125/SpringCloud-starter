package org.quanta.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.User;
import org.quanta.user.entity.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/7
 */
@Data
@Builder
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsVO implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户基本信息")
    private User user;
    @ApiModelProperty("用户拓展信息")

    private UserInfo userInfo;
    @ApiModelProperty("用户角色信息")

    private List<Role> userRoleList;

    @ApiModelProperty("用户权限")
    private List<Permission> userPermissionList;
}
