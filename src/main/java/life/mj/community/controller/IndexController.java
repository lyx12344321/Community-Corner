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

        // 遍历请求中的所有Cookie
        for (Cookie cookie : request.getCookies()) {
            // 如果Cookie的名称为token
            if (cookie.getName().equals("token")) {
                // 获取Cookie的值
                String token = cookie.getValue();
                // 根据token查询用户
                User user = userMapper.selectUserByToken(token);
                // 如果用户存在
                if (user != null)
                    // 将用户存入Session中
                    request.getSession().setAttribute("user", user);
                break;
            }
        }

        // 返回index页面
        return "index";
    }


}