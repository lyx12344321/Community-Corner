package life.mj.community.dto;

import com.github.pagehelper.PageInfo;
import life.mj.community.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class IndexQuestionsPageDTO {

    List<QuestionDTO> questions;
    PageInfo<Question> questionPageInfo;

}
