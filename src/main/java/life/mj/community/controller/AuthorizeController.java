package life.mj.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.mj.community.dto.AccessTokenDTO;
import life.mj.community.dto.GitHubUser;
import life.mj.community.mapper.UserMapper;
import life.mj.community.provider.GitHubProvider;
import life.mj.community.model.User;
import life.mj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthorizeController {


    // 注入UserMapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    // 注入GitHub客户端ID
    @Value("${github.client.id}")
    private String ClientId;

    // 注入GitHub客户端密钥
    @Value("${github.client.secret}")
    private String ClientSecret;

    // 注入GitHub重定向URI
    @Value("${github.redirect.uri}")
    private String RedirectUri;


    // 处理GitHub回调
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        String accessToken = userService.getAccessToken(code);
        System.out.println(accessToken);
        GitHubUser gitHubUser = userService.getUserInfo(accessToken);
        System.out.println(gitHubUser);
        if (gitHubUser.getId() == null) {
            return "redirect:" + "/error/authorization_failed";
        }
        User user = userService.createOrUpdate(gitHubUser);
        response.addCookie(new Cookie("token", user.getToken()));
        return "redirect:" + "/";
    }
}