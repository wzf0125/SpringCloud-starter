package org.quanta.auth.entity;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quanta.user.entity.Permission;
import org.quanta.user.vo.UserDetailsVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDetails implements UserDetails {
    UserDetailsVO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ListUtil.empty();
    }

    @Override
    public String getPassword() {
        return user.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUser().getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
