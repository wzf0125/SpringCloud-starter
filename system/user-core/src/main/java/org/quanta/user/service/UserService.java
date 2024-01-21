package org.quanta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.core.beans.PageQueryDO;
import org.quanta.core.beans.PageQueryResult;
import org.quanta.user.dto.AddUserDTO;
import org.quanta.user.dto.EditUserDTO;
import org.quanta.user.entity.User;
import org.quanta.user.vo.UserDetailsVO;
import org.quanta.user.vo.UserVO;

/**
 * @author wzf
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-12-25 18:17:18
 */
public interface UserService extends IService<User> {

    /**
     * 创建用户
     */
    void createUser(AddUserDTO dto);

    /**
     * 编辑用户
     */
    void editUser(EditUserDTO dto);

    /**
     * 删除用户
     */
    void deleteUser(Integer id);

    /**
     * 分页查询用户
     */
    PageQueryResult<UserVO> getUserByPage(String keyword, PageQueryDO build);

    /**
     * 通过邮箱获取用户信息
     */
    UserDetailsVO getUserByEmail(String email);

    UserDetailsVO getUserByAccount(String account);
}
