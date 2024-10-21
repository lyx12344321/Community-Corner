package life.mj.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.dto.QuestionUserDTO;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.User;
import life.mj.community.service.QuestionService;
import org.apache.ibatis.annotations.Mapper;
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
                        Model model) {

        List<QuestionUserDTO> questions = questionService.list();
        model.addAttribute("questions", questions);
//        System.out.println(questions);
        return "index";
    }




}