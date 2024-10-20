package life.mj.community.mapper;

import life.mj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {


    // 插入用户信息
    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified) values (#{account_id}, #{name}, #{token}, #{gmt_create}, #{gmt_modified})")
    int insertUser(User user);

    // 根据token查询用户信息
    @Select("select * from users where token = #{token}")
    User selectUserByToken(@Param("token") String token);


}