package org.quanta.user.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.beans.PageQueryDO;
import org.quanta.core.beans.PageQueryResult;
import org.quanta.core.constant.LockConstant;
import org.quanta.core.constant.cache.TokenCache;
import org.quanta.core.constant.cache.UserCache;
import org.quanta.core.utils.CacheUtils;
import org.quanta.core.utils.RedisUtils;
import org.quanta.user.dto.AddUserDTO;
import org.quanta.user.dto.EditUserDTO;
import org.quanta.user.entity.User;
import org.quanta.user.entity.UserInfo;
import org.quanta.user.entity.UserRole;
import org.quanta.user.mapper.UserMapper;
import org.quanta.user.service.UserInfoService;
import org.quanta.user.service.UserPermissionService;
import org.quanta.user.service.UserRoleService;
import org.quanta.user.service.UserService;
import org.quanta.user.vo.UserDetailsVO;
import org.quanta.user.vo.UserVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wzf
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-12-25 18:17:18
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final RedissonClient redissonClient;
    private final UserInfoService userInfoService;
    private final UserRoleService userRoleService;
    private final UserPermissionService userPermissionService;
    private final RedisUtils redisUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(AddUserDTO dto) {
        // 对邮箱加锁
        RLock mailLock = redissonClient.getFairLock(String.format(LockConstant.LOCK_USER_EMAIL, dto.getEmail()));
        RLock accountLock = redissonClient.getFairLock(String.format(LockConstant.LOCK_USER_ACCOUNT, dto.getEmail()));
        mailLock.lock(10L, TimeUnit.MINUTES);
        accountLock.lock(10L, TimeUnit.MINUTES);
        try {
            // 校验邮箱是否存在
            User user = lambdaQuery()
                    .eq(User::getEmail, dto.getEmail())
                    .or(condition -> condition.eq(User::getAccount, dto.getAccount()))
                    .in(User::getIsDeleted, ListUtil.list(false, 0, 1))
                    .one();
            if (user != null) {
                if (user.getIsDeleted() == 0) {
                    if (user.getEmail().equals(dto.getEmail())) {
                        throw new ServiceException("邮箱已被使用");
                    } else {
                        throw new ServiceException("账号已被使用");
                    }
                }
                // 已逻辑删除 执行完全删除用户
                removeById(user.getId(), true);
            }

            // 保存用户信息
            user = User.builder()
                    .account(dto.getAccount())
                    .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    .build();
            save(user);

            // 保存额外信息
            UserInfo ext = UserInfo.builder()
                    .uid(user.getId())
                    .avatar(dto.getExt() == null ? "" : dto.getExt().getAvatar())
                    .gender(dto.getExt() == null ? "" : dto.getExt().getGender())
                    .build();
            userInfoService.save(ext);
        } finally {
            mailLock.unlock();
            accountLock.unlock();
        }
    }

    @Override
    @Transactional
    public void editUser(EditUserDTO dto) {
        // 编辑学生信息
        User user = getById(dto.getId());
        if (user == null) throw new ServiceException("用户不存在");

        // 对邮箱加锁
        RLock lock = redissonClient.getFairLock(String.format(LockConstant.LOCK_USER_EMAIL, dto.getEmail()));
        lock.lock(10L, TimeUnit.MINUTES);
        try {
            // 查询邮箱是否被绑定
            User user1 = lambdaQuery()
                    .eq(User::getEmail, dto.getEmail())
                    .ne(User::getId, user.getId()) // 记得排除自己..
                    .one();

            // 邮箱已被使用
            if (user1 != null) {
                if (user1.getIsDeleted() == 0) {
                    throw new ServiceException("邮箱已被使用");
                }
                // 已被逻辑删除 对该用户执行强制删除
                removeById(user1, true);
            }

            // 填充编辑值
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());

            // 判断是否需要修改密码 修改密码则创建Token黑名单阻拦之前Token的使用
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                // 设置新密码
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                // 保存当前时间
                redisUtils.set(String.format(TokenCache.SYSTEM_USER_TOKEN_LOCK, user.getId()), DateUtil.now());
            }

            // 更新用户信息
            updateById(user);

            // 查询拓展信息id
            UserInfo extInfo = userInfoService.lambdaQuery().eq(UserInfo::getUid, dto.getId()).one();
            extInfo.setAvatar(dto.getExt() == null ? "" : dto.getExt().getAvatar());
            extInfo.setGender(dto.getExt() == null ? "" : dto.getExt().getGender());
            userInfoService.updateById(extInfo);
        } finally {
            lock.unlock();
            // 清除账号缓存
            CacheUtils.evict(UserCache.SYSTEM_USER_USER_BY_ACCOUNT, user.getAccount());
        }


    }

    /**
     * 删除用户
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user = getById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        // 删除角色
        removeById(id);
        // 删除角色信息
        userInfoService.lambdaUpdate()
                .eq(UserInfo::getUid, id)
                .remove();
        // 删除用户角色关联
        userRoleService.lambdaUpdate()
                .eq(UserRole::getUid, id)
                .remove();
        // 清除账号缓存
        CacheUtils.evict(UserCache.SYSTEM_USER_USER_BY_EMAIL, user.getEmail());
        // 清除账号缓存
        CacheUtils.evict(UserCache.SYSTEM_USER_USER_BY_ACCOUNT, user.getAccount());
    }

    /**
     * 分页查询用户列表
     */
    @Override
    public PageQueryResult<UserVO> getUserByPage(String keyword, PageQueryDO query) {
        IPage<User> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        if (keyword != null) {
            // 近似查找用户名和邮箱
            page(page, new LambdaQueryWrapper<User>()
                    .like(User::getUsername, keyword)
                    .or(condition -> condition.like(User::getEmail, keyword))
                    .select(ListUtil.toList(User::getId, User::getUsername, User::getEmail)));
        } else {
            page(page);
        }

        List<User> userList = page.getRecords();
        List<UserVO> res = userList.stream().map(u -> UserVO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .build()).collect(Collectors.toList());
        return PageQueryResult.<UserVO>builder()
                .data(res)
                .currentPage(query.getCurrentPage())
                .pageSize(query.getPageSize())
                .pages(page.getPages())
                .total(page.getTotal())
                .build();
    }

    /**
     * 通过邮箱查找用户信息
     */
    @Override
    public UserDetailsVO getUserByEmail(String email) {
        UserDetailsVO info = getBaseMapper().findUserByEmail(email);
        if (info == null) return null;
        // 设置角色和权限信息
        info.setUserRoleList(userRoleService.getUserRoleListByUid(info.getId()));
        info.setUserPermissionList(userPermissionService.getUserPermissionList(info.getId()));
        return info;
    }

    @Override
    public UserDetailsVO getUserByAccount(String account) {
        UserDetailsVO info = getBaseMapper().findUserByAccount(account);
        if (info == null) return null;
        // 设置角色和权限信息
        info.setUserRoleList(userRoleService.getUserRoleListByUid(info.getId()));
        info.setUserPermissionList(userPermissionService.getUserPermissionList(info.getId()));
        return info;
    }

}
