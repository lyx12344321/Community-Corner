package life.mj.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    // 注入UserMapper
    @Autowired
    private UserMapper userMapper;

    // 处理根路径的GET请求
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @GetMapping("/index")
    public String ToIndex(HttpServletRequest request) {
        return "index";
    }


}