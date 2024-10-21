package life.mj.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.mj.community.dto.IndexQuestionsPageDTO;
import life.mj.community.dto.QuestionDTO;
import life.mj.community.mapper.QuestionMapper;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.Question;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public IndexQuestionsPageDTO list(Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.list();
        PageInfo<Question> pageInfo = new PageInfo<>(questions);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO(question, user);
            questionDTOList.add(questionDTO);
        }
        IndexQuestionsPageDTO indexQuestionsPageDTO = new IndexQuestionsPageDTO();
        indexQuestionsPageDTO.setQuestions(questionDTOList);
        indexQuestionsPageDTO.setQuestionPageInfo(pageInfo);
        return indexQuestionsPageDTO;
    }
}
