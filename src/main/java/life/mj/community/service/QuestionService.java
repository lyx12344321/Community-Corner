package life.mj.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.mj.community.dto.QuestionsPaginationInfoDTO;
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

    public QuestionsPaginationInfoDTO list(Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.list();
        PageInfo<Question> pageInfo = new PageInfo<>(questions);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            if (user == null) {
                user = new User("default");
            }
            QuestionDTO questionDTO = new QuestionDTO(question, user);
            questionDTOList.add(questionDTO);
        }
        QuestionsPaginationInfoDTO questionsPaginationInfoDTO = new QuestionsPaginationInfoDTO(questionDTOList, pageInfo);
        questionsPaginationInfoDTO.setQuestions(questionDTOList);
        questionsPaginationInfoDTO.setQuestionPageInfo(pageInfo);
        return questionsPaginationInfoDTO;
    }
}
