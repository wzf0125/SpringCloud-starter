package org.quanta.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.RolePermission;

import java.util.List;

/**
 * @author wzf
 * @description 针对表【role_permission】的数据库操作Mapper
 * @createDate 2023-12-25 18:17:16
 * @Entity org.quanta.user.entity.RolePermission
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Permission> getPermissionByRoleIdList(List<Integer> roleIdList);
}
