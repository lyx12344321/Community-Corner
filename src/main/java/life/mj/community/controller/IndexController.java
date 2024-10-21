package life.mj.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.dto.IndexQuestionsPageDTO;
import life.mj.community.dto.QuestionDTO;
import life.mj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    // 注入UserMapper
    @Autowired
    private QuestionService questionService;

    // 处理根路径的GET请求
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "6") Integer size) {

        IndexQuestionsPageDTO pageDTO = questionService.list(page, size);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("url_pram_page", page);
        model.addAttribute("url_pram_size", size);
        return "index";
    }




}