package icu.buzz.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.buzz.redis.entities.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
}
