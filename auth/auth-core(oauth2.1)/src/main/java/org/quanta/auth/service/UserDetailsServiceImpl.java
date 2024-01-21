package org.quanta.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.core.utils.ResponseUtils;
import org.quanta.user.entity.Permission;
import org.quanta.user.feign.IUserClient;
import org.quanta.user.vo.UserDetailsVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/5
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class UserDetailsServiceImpl implements UserDetailsService {
    // 用户服务
    private final IUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 微服务调用 查询用户信息
        UserDetailsVO userDetailsVO = ResponseUtils.getData(userClient.getUserByAccount(account));
        if (userDetailsVO == null || userDetailsVO.getUser() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 构建权限列表
        List<SimpleGrantedAuthority> grantedAuthorityList = userDetailsVO
                .getUserPermissionList()
                .stream()
                .map(Permission::getPath)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 返回用户数据
        return new User(account, userDetailsVO.getUser().getPassword(), grantedAuthorityList);
    }
}
