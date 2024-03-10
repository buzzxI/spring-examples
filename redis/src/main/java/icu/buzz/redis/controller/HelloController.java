package icu.buzz.redis.controller;

import icu.buzz.redis.entities.Person;
import icu.buzz.redis.entities.User;
import icu.buzz.redis.service.HelloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostMapping("/echo")
    public String hello(@RequestBody String msg) {
        return helloService.echo(msg);
    }

    @PostMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return helloService.getUserByName(username);
    }

    @PostMapping("/add/{username}/{password}")
    public User addUser(@PathVariable String username, @PathVariable String password) {
        return helloService.addUser(username, password);
    }

    @PostMapping("/addPerson")
    public Person addPerson() {
        return helloService.addPerson();
    }
}
