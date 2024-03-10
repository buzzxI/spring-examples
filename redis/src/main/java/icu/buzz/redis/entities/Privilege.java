package icu.buzz.redis.entities;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Privilege {
    READ(1, "read"),
    WRITE(2, "write"),
    DELETE(3, "delete"),
    UPDATE(4, "update");

    @EnumValue
    private final int id;
    private final String desc;

    Privilege(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
