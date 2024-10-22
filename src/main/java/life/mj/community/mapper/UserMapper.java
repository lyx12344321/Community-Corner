package life.mj.community.mapper;

import life.mj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {


    // 插入用户信息
    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified, authorization) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{authorization})")
    int insertUser(User user);

    // 根据token查询用户信息
    @Select("select * from users where token = #{token} limit 1")
    User selectUserByToken(@Param("token") String token);

    @Select("select * from users where id = #{id}")
    User findById(@Param("id") int creator);

    @Select("select * from users where account_id = #{accountId} and authorization = #{authorization} limit 1")
    User findGitHubUserByAccountId(@Param("accountId") Long accountId, @Param("authorization") String authorization);
}