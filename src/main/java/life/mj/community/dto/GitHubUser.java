package life.mj.community.dto;

import lombok.Getter;
import lombok.Setter;

// 使用Lombok注解，自动生成getter和setter方法
@Setter
@Getter
public class GitHubUser {
    // 用户名
    private String name;
    // 用户ID
    private Long id;

    // 重写toString方法，返回用户名和ID
    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}