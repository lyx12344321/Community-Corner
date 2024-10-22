package life.mj.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.dto.QuestionsPaginationInfoDTO;
import life.mj.community.model.User;
import life.mj.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // 默认跳转到问题页面
    @GetMapping({"/profile/", "/profile"})
    public String defaultProfile() {
        return "redirect:/profile/questions";
    }

    @GetMapping("/profile/{action}")
    public String questionProfile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "6") Integer size,
                          HttpServletRequest httpServletRequest,
                          Model model) {

        QuestionsPaginationInfoDTO PageDTO = profileService.getPage(action, page, size, model, (User) httpServletRequest.getSession().getAttribute("user"));
        model.addAttribute("pageDTO", PageDTO);
        return "profile";
    }



}
