package org.quanta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.user.dto.EditUserRoleDTO;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.UserRole;
import org.quanta.user.vo.RoleVO;

import java.util.List;

/**
* @author wzf
* @description 针对表【user_role】的数据库操作Service
* @createDate 2023-12-25 18:17:25
*/
public interface UserRoleService extends IService<UserRole> {

    List<RoleVO> getUserRoleByUid(Integer id);

    void editUserRole(EditUserRoleDTO dto);

    List<Role> getUserRoleListByUid(Integer id);
}
