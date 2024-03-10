package icu.buzz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.buzz.security.entities.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * simple mapper, extends BaseMapper will free the xml configuration
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
