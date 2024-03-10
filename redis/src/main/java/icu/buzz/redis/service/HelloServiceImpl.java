package icu.buzz.redis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.buzz.redis.entities.Person;
import icu.buzz.redis.entities.Role;
import icu.buzz.redis.entities.User;
import icu.buzz.redis.mapper.PersonMapper;
import icu.buzz.redis.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    public HelloServiceImpl(UserMapper userMapper, PersonMapper personMapper) {
        this.userMapper = userMapper;
        this.personMapper = personMapper;
    }

    @Override
    public String echo(String msg) {
        return msg;
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, name));
    }

    @Override
    public User addUser(String name, String password) {
        User user = new User();
        user.setUsername(name).setPassword(password).setRole(Role.USER).setEmail(null);
//        System.out.println(user.getId());
        userMapper.insert(user);
        return user;
    }

    @Override
    public Person addPerson() {
        Person person = new Person();
        personMapper.insert(person);
        return person;
    }
}
