package pro.yeyuan.merlinwisewiki.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pro.yeyuan.merlinwisewiki.domain.User;
import pro.yeyuan.merlinwisewiki.domain.UserExample;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}