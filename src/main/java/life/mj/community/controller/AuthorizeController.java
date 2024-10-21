package life.mj.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.mj.community.dto.AccessTokenDTO;
import life.mj.community.dto.GitHubUser;
import life.mj.community.mapper.UserMapper;
import life.mj.community.provider.GitHubProvider;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthorizeController {

    // 注入GitHubProvider
    @Autowired
    private GitHubProvider gitHubProvider;

    // 注入UserMapper
    @Autowired
    private UserMapper userMapper;

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
        // System.out.println("into callback");
        // 创建AccessTokenDTO对象
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(ClientSecret);

        // 获取GitHub访问令牌
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        // System.out.println(accessToken);
        // 获取GitHub用户信息
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        // System.out.println(gitHubUser);

        // 如果GitHub用户ID不为空
        if (gitHubUser.getId() != null) {

            // 创建User对象
            User user = new User();
            // 生成随机token
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

             System.out.println(userMapper.insertUser(user));

            // 添加token到Cookie
            response.addCookie(new Cookie("token", token));

            System.out.println("登陆成功");
            return "redirect:" + "/";
        }
        System.out.println("登陆失败");
        return "redirect:" + "/";
    }
}