package org.quanta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.user.dto.AddPermissionDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.vo.PermissionVO;

import java.util.List;

/**
 * @author wzf
 * @description 针对表【permission】的数据库操作Service
 * @createDate 2023-12-25 18:16:18
 */
public interface PermissionService extends IService<Permission> {

    List<PermissionVO> getPermissionTree();

    void savePermission(AddPermissionDTO dto);

    /**
     * 深度优先搜索遍历子树标记节点
     *
     * @param root 当前节点
     * @param mark 需要标记的列表
     */
    void markNode(PermissionVO root, List<Integer> mark);

    void removePermissionById(Integer id);
}
