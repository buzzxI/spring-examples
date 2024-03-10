package icu.buzz.redis.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`persons`")
public class Person {
    @TableId(value = "uid", type = IdType.ASSIGN_UUID)
    String uid;
}
