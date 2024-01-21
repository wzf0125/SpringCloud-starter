package org.quanta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.user.dto.AddRoleDTO;
import org.quanta.user.entity.Role;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;

import java.util.List;

/**
 * @author wzf
 * @description 针对表【role】的数据库操作Service
 * @createDate 2023-12-25 18:17:12
 */
public interface RoleService extends IService<Role> {
    /**
     * 保存角色
     */
    void saveRole(AddRoleDTO dto);

    /**
     * 获取角色列表
     */
    List<RoleVO> getRoleTree();


    /**
     * 获取角色权限列表
     */
    List<PermissionVO> getRolePermissionById(Integer roleId);

    void removeRoleById(Integer id);
}
