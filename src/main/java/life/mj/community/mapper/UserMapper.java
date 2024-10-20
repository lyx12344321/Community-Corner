package life.mj.community.mapper;

import life.mj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {


    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified) values (#{account_id}, #{name}, #{token}, #{gmt_create}, #{gmt_modified})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insertUser(User user);

    @Select("show databases ")
    List<String> t1();

    @Select("select current_user")
    List<String> t2();

    @Select("select database()")
    List<String> t3();

    @Select("show tables")
    List<String> t4();




}
