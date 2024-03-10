package icu.buzz.redis.service;


import icu.buzz.redis.entities.Person;
import icu.buzz.redis.entities.User;

public interface HelloService {
    String echo(String msg);

    User getUserByName(String name);

    User addUser(String name, String password);

    Person addPerson();

}
