package life.mj.community.dto;

import com.github.pagehelper.PageInfo;
import life.mj.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionsPaginationInfoDTO {

    List<QuestionDTO> questions;
    PageInfo<Question> questionPageInfo;

    public QuestionsPaginationInfoDTO(List<QuestionDTO> questionDTOList, PageInfo<Question> pageInfo) {
        this.questions = questionDTOList;
        this.questionPageInfo = pageInfo;
    }

}
