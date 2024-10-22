package life.mj.community.service;

import life.mj.community.dto.AccessTokenDTO;
import life.mj.community.dto.GitHubUser;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.User;
import life.mj.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    // 注入GitHubProvider
    @Autowired
    private GitHubProvider gitHubProvider;

    // 注入GitHub客户端ID
    @Value("${github.client.id}")
    private String ClientId;

    // 注入GitHub客户端密钥
    @Value("${github.client.secret}")
    private String ClientSecret;
    // 注入GitHub重定向URI
    @Value("${github.redirect.uri}")
    private String RedirectUri;


    public String getAccessToken (String code) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(ClientSecret);

        return gitHubProvider.getAccessToken(accessTokenDTO);
    }

    public GitHubUser getUserInfo(String accessToken) {
        return gitHubProvider.getUser(accessToken);
    }
    public User createOrUpdate(GitHubUser gitHubUser) {
        User user = userMapper.findGitHubUserByAccountId(gitHubUser.getId(), "github");

        if (user == null) {
            user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAuthorization("github");
            System.out.println("user表插入了：" + userMapper.insertUser(user) + "条数据");
        }
        return user;
    }
}
