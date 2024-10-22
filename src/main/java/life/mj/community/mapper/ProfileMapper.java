package life.mj.community.mapper;

import life.mj.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProfileMapper {

    //查询我的问题
    @Select("select * from question where creator = #{userId} order by gmt_create")
    List<Question> selectMyQuestionByUserId(@Param("userId") int userId);


    @Select("select * from question where creator = #{userId} && has_recent_reply = true order by gmt_create")
    List<Question> selectRecentReply(@Param("userId") int userId);

    @Update("update question set has_recent_reply = false where creator = #{userId} && id = #{questionId}")
    void removeRecentRepl(@Param("userId") int userId, @Param("questionId") int questionId);
}
