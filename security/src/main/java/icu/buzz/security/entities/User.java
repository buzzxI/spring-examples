package icu.buzz.security.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
// enable chain style (lombok)
@Accessors(chain = true)
// associate entity with table user
@TableName("`users`")
public class User  {
    // primary key, auto generate by mybatis-plus (uuid)
    @TableId(value = "uid", type = IdType.ASSIGN_UUID)
    private String uid;
    private String username;
    private String password;
    private Boolean enabled;
    private Role role;
}
