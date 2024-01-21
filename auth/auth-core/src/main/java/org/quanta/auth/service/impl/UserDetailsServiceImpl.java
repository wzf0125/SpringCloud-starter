package org.quanta.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.quanta.auth.entity.SystemUserDetails;
import org.quanta.core.utils.ResponseUtils;
import org.quanta.user.feign.IUserClient;
import org.quanta.user.vo.UserDetailsVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/12
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 微服务调用 查询用户信息
        UserDetailsVO userDetailsVO = ResponseUtils.getData(userClient.getUserByAccount(account));
        if (userDetailsVO == null || userDetailsVO.getUser() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return SystemUserDetails.builder()
                .user(userDetailsVO)
                .build();
    }
}
