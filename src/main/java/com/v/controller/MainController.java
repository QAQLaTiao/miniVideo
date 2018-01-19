package com.v.controller;

import com.v.model.Video;
import com.v.util.ConfigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QAQ
 */
@Controller
@RequestMapping("/")
public class MainController {


    @RequestMapping("/main")
    public String main() {
        System.out.println("============main==========");
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, String username, String password) {
        HttpSession session = request.getSession();
        System.out.println("============login==========");
        if ("admin123".equals(username)) {
            if ("admin123".equals(password)) {
                session.setAttribute("username", username);
                return "redirect:/aaa/index";
            }
        }
        return "redirect:/main";
    }

    @RequestMapping("/video")
    public String video() {
        System.out.println("============video==========");
        return "video";
    }

    @RequestMapping("/aaa/index")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(defaultValue = "1") Integer currentPage) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        Integer pageSize = 5;
        Integer startPage = (currentPage - 1) * pageSize;
        List<Video> videoList = ConfigUtil.videoList;
        List<Video> list = new ArrayList<>();

        for (int i = startPage; i < startPage + pageSize; i++) {
            if (i < videoList.size()) {
                list.add(videoList.get(i));
            }
        }
        model.addAttribute("videoList", list);
        model.addAttribute("currentPage", currentPage);
        System.out.println("============index==========");
        return "index";
    }

    @ResponseBody
    @RequestMapping("/play")
    public Video play(String videoName) {
        String s = ConfigUtil.videoMap.get(videoName);
        System.out.println("============play=========" + s);
        Video v = new Video();
        v.setName(videoName);
        v.setUrl(s);
        return v;
    }
}
