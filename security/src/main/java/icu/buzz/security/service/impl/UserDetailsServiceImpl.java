package icu.buzz.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.buzz.security.constant.ExceptionConstant;
import icu.buzz.security.entities.JwtUser;
import icu.buzz.security.entities.Role;
import icu.buzz.security.entities.User;
import icu.buzz.security.exception.MultiUserFoundException;
import icu.buzz.security.exception.UsernameNotFoundException;
import icu.buzz.security.mapper.UserMapper;
import icu.buzz.security.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RedisUtil redisUtil;
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(RedisUtil redisUtil, UserMapper userMapper) {
        this.redisUtil = redisUtil;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = (User) redisUtil.get(username);
        if (user == null) {
            // query by mybatis-plus
            List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
            if (users.isEmpty()) {
                throw new UsernameNotFoundException(Map.of(ExceptionConstant.USERNAME_NOT_FOUND, username));
            }
            if (users.size() > 1) {
                // multiple users found, that should be server internal error
                throw new MultiUserFoundException(Map.of(ExceptionConstant.MULTIPLE_USER_FOUND, username));
            }
            user = users.get(0);
            redisUtil.set(user.getUsername(), user);
        }
        return new JwtUser(user);
    }

    public boolean userPresent(String username) {
        Object jwtUser = redisUtil.get(username);
        if (jwtUser != null) {
            return true;
        }
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        return !users.isEmpty();
    }

    public User saveUser(String username, String password) {
        User user = new User();
        user
                .setUsername(username)
                .setPassword(password)
                .setRole(Role.USER)
                .setEnabled(true);
        userMapper.insert(user);
        return user;
    }
}
