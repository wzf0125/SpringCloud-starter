package org.quanta.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.user.entity.Permission;

import java.util.List;

/**
 * @author wzf
 * @description 针对表【permission】的数据库操作Mapper
 * @createDate 2023-12-25 18:16:18
 * @Entity org.quanta.user.entity.Permission
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    // 分页获取权限分组
    List<String> getPermissionGroup(Long start, Long size);
    Long countPermissionGroup();
}




