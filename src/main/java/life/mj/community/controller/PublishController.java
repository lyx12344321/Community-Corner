package life.mj.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.mapper.QuestionMapper;
import life.mj.community.model.Question;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "/publish";
    }

    @PostMapping("/publish")
    public String publishQuestion(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "text", required = false) String text,
            HttpServletRequest request,
            Model module
    ) {

        System.out.println("into publishQuestion");

        module.addAttribute("title", title);
        module.addAttribute("description", description);
        module.addAttribute("tag", tag);
        module.addAttribute("text", text);


        // 校验非空
        if (text == null || text.isEmpty() || title == null || title.isEmpty() || description == null || description.isEmpty() || tag == null || tag.isEmpty()) {
            module.addAttribute("error", "请填写标题、内容或标签");
            return "/publish";
        }
        // 获取当前登录用户
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        if (user == null) {
            module.addAttribute("error", "用户未登录");
            return "redirect:/";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setText(text);

        question.setCreator(user.getId());

        System.out.println(question);

        int creatQuestionOutput = questionMapper.creatQuestion(question);

        if (creatQuestionOutput != 0) {
            System.out.println("成功添加 " + creatQuestionOutput + " 条记录");
        }

        module.addAttribute("success", "添加成功");

        return "/publish";
    }
}
