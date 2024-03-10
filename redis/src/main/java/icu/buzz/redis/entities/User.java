package icu.buzz.redis.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
// make pojo fluent, all getters will return this
@Accessors(chain = true)
// indicates User entity is mapped to user table
@TableName("`users`")
public class User {
    // set current value auto increment
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
