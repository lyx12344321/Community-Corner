package life.mj.community.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessTokenDTO {
    // 客户端ID
    private String  client_id;
    // 客户端密钥
    private String  client_secret;
    // 授权码
    private String code;
    // 重定向URI
    private String redirect_uri;

}