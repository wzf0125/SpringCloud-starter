package org.quanta.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quanta.user.entity.User;
import org.quanta.user.vo.UserDetailsVO;

/**
* @author wzf
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-12-25 18:17:18
* @Entity org.quanta.user.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    UserDetailsVO findUserByEmail(String email);
    UserDetailsVO findUserByAccount(String email);
}




