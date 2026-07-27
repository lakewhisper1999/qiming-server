package top.kaiven.qiming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kaiven.qiming.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
