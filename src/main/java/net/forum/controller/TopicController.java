package net.forum.controller;

import javafx.application.Application;
import net.forum.model.Theme;
import net.forum.service.ThemeService;
import net.forum.service.TopicService;
import net.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller for POJO {@link net.forum.model.Theme}
 * Это контроллер работает с формой(JSP) создания новой темы, добавляет новый топик и переадресовывает
 * пользователя на страницу содержащую список топиков.
 */

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ThemeService themeService;

    @RequestMapping(value = "topic/{id}", method = RequestMethod.GET)
    public String topicPage(Model model) {
        List<Topic> allInstanceTopic =  topicService.getAllTopic ();
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        model.addAttribute ("topicForm", new Topic ());
        model.addAttribute ("themeForm", new Theme ());
        return "topic";
    }

    @RequestMapping(value = "/createTopic", method = RequestMethod.GET)
    public String pageCreateTheme(Model model) {
        model.addAttribute ("topicForm", new Topic ());
        return "createTopic";
    }

    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public String addTheme(@ModelAttribute("topicForm") Topic topicForm, Model model, HttpServletRequest request) {
        if (topicForm.getId () == null) {
            topicForm.setUsername (SecurityContextHolder.getContext ().getAuthentication ().getName ());
            //вытащим из URL id что-бы найти название темы в которой лежит топик
            String url = request.getHeader("referer");
            Long id = Long.parseLong (url.split ("/topic/")[1]);
            topicForm.setThemeId (id);
            topicService.save (topicForm);
        }
        return "redirect:/topic";
    }

}
