package icu.buzz.redis.entities;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {
    ADMIN(1, Set.of(Privilege.READ, Privilege.WRITE, Privilege.UPDATE, Privilege.DELETE), "admin"),
    USER(2, Set.of(Privilege.READ, Privilege.WRITE), "user");

    @EnumValue
    private final int id;
    private final Set<Privilege> set;
    private final String desc;

    Role(int id, Set<Privilege> set, String desc) {
        this.id = id;
        this.set = set;
        this.desc = desc;
    }
}
