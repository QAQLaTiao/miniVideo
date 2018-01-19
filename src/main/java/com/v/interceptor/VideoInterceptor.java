package com.v.interceptor;

import com.v.model.Video;
import com.v.util.ConfigUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author QAQ
 */
public class VideoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HttpSession session = httpServletRequest.getSession();
        o = session.getAttribute("username");
        String username = "";
        if (o != null) {
            username = o.toString();
        }
        if (StringUtils.isEmpty(username)) {
            httpServletResponse.sendRedirect("/main");
        } else {

            if (ConfigUtil.videoList.size() == 0) {
                try {
                    String pathname = httpServletRequest.getSession().getServletContext().getRealPath("video.txt");
                    File filename = new File(pathname);
                    InputStreamReader reader = new InputStreamReader(
                            new FileInputStream(filename));
                    BufferedReader br = new BufferedReader(reader);
                    String line = "";
                    line = br.readLine();
                    int i = 0;
                    while (line != null) {
                        i++;
                        System.out.println(i);
                        line = br.readLine();
                        String[] split = line.split(",,");
                        Video v = new Video();
                        v.setName(split[0]);
                        v.setUrl(split[1]);
                        ConfigUtil.videoMap.put(split[0], split[1]);
                        ConfigUtil.videoList.add(v);
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}