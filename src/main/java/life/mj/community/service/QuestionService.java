package life.mj.community.service;

import life.mj.community.dto.QuestionUserDTO;
import life.mj.community.mapper.QuestionMapper;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.Question;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionUserDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionUserDTO> questionUserDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionUserDTO questionUserDTO = new QuestionUserDTO(question, user);
            questionUserDTOList.add(questionUserDTO);
        }
        return questionUserDTOList;
    }
}
