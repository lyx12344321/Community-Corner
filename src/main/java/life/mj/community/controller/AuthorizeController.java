package life.mj.community.controller;

import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String ClientId;

    @Value("${github.client.secret}")
    private String ClientSecret;

    @Value("${github.redirect.uri}")
    private String RedirectUri;



    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request) {
        System.out.println("into callback");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(ClientSecret);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println(gitHubUser);

        if (gitHubUser.getId() != null) {

            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());

            System.out.println(userMapper.insertUser(user));

            request.getSession().setAttribute("gitHubUser", gitHubUser);

            System.out.println("登陆成功");
            return "redirect:"+"/";
        }
        System.out.println("登陆失败");
        return "redirect:"+"/";
    }
}
