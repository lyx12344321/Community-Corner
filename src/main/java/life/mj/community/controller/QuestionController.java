package life.mj.community.controller;

import life.mj.community.dto.QuestionDTO;
import life.mj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/question", "/question/"})
    public String toErrorPage(Model model) {
        model.addAttribute("error_msg", "该问题不存在或请求路径错误");
        return "/error/not_found";
    }

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") int id,
                           Model model){

        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("question", questionDTO);
        return "/question";
    }
}
