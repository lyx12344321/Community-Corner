package life.mj.community.model;

import lombok.Getter;
import lombok.Setter;

// 使用Lombok注解，自动生成getter和setter方法
@Setter
@Getter
public class User {

    // 用户ID
    private Integer id;
    // 账户ID
    private String accountId;
    // 用户名
    private String Name;
    // 令牌
    private String token;
    // 创建时间
    private Long gmtCreate;
    // 修改时间
    private Long gmtModified;
    // 头像
    private String avatarUrl;


}