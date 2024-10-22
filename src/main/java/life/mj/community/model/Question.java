package life.mj.community.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Question {
    // 文章ID
    private int id;
    // 文章标题
    private String title;
    String text;
    // 文章描述
    private String description;
    // 创建时间
    private Long gmtCreate;
    // 修改时间
    private Long gmtModified;
    // 创建者ID
    private int creator;
    // 评论数量
    private int commentCount;
    // 浏览数量
    private int viewCount;
    // 点赞数量
    private int likeCount;
    // 标签
    private String tag;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", creator=" + creator +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", tag='" + tag + '\'' +
                '}';
    }
}
