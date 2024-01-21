package org.quanta.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.user.entity.UserInfo;
import org.quanta.user.mapper.UserInfoMapper;
import org.quanta.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author wzf
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2023-12-25 18:17:20
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




