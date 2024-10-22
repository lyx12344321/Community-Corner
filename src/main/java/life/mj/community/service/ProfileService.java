package life.mj.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.mj.community.dto.QuestionDTO;
import life.mj.community.dto.QuestionsPaginationInfoDTO;
import life.mj.community.mapper.ProfileMapper;
import life.mj.community.model.Question;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileMapper profileMapper;

    public QuestionsPaginationInfoDTO getPage(String action, Integer page, Integer size, Model model, User user) {

        if (user == null) {
            return null;
        }

        List<Question> questions;
        PageInfo<Question> pageInfo;
        List<QuestionDTO> questionDTOList;
        switch (action) {
            case "questions":
                model.addAttribute("section", "questions");
                model.addAttribute("sectionName", "我的提问");
                PageHelper.startPage(page, size);
                questions = profileMapper.selectMyQuestionByUserId(user.getId());
                pageInfo = new PageInfo<>(questions);
                break;

            case "replies":
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "最新回复");
                PageHelper.startPage(page, size);
                questions = profileMapper.selectRecentReply(user.getId());
                pageInfo = new PageInfo<>(questions);
                break;

            default:
                questions = new ArrayList<>();
                pageInfo = new PageInfo<>(questions);
        }

        questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            questionDTOList.add(new QuestionDTO(question, user));
        }
        return new QuestionsPaginationInfoDTO(questionDTOList, pageInfo);

    }


}
