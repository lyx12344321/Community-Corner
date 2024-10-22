package life.mj.community.mapper;

import life.mj.community.dto.QuestionDTO;
import life.mj.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag, text) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}, #{text})")
    int creatQuestion(Question question);

    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);
}
