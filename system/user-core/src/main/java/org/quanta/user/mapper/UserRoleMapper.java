package org.quanta.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.UserRole;

import java.util.List;

/**
* @author wzf
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2023-12-25 18:17:25
* @Entity org.quanta.user.entity.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<Role> getUserRoleListByUid(Integer id);
}




