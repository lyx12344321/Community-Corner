package life.mj.community.dto;

import life.mj.community.model.Question;
import life.mj.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {

    // 文章ID
    private int id;
    // 文章标题
    private String title;
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
    private String text;
    // 用户
    private User user;

    public QuestionDTO(Question question, User user) {
        // 逐个赋值
        this.id = question.getId();
        this.title = question.getTitle();
        this.description = question.getDescription();
        this.gmtCreate = question.getGmtCreate();
        this.gmtModified = question.getGmtModified();
        this.creator = question.getCreator();
        this.commentCount = question.getCommentCount();
        this.viewCount = question.getViewCount();
        this.likeCount = question.getLikeCount();
        this.tag = question.getTag();
        this.text = question.getText();
        this.user = user;
    }

    @Override
    public String toString() {
        return "QuestionUserDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", creator=" + creator +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", tag='" + tag + '\'' +
                ", user=" + user +
                '}';
    }
}
