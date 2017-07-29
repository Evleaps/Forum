package net.forum.controller;

import net.forum.model.Theme;
import net.forum.service.ThemeService;
import net.forum.service.TopicService;
import net.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
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

    //ГЛАВНАЯ
    @RequestMapping(value = "topic/{id}", method = RequestMethod.GET)
    public String topicPage(Model model, HttpServletRequest request) {
        List<Topic> allInstanceTopic =  topicService.getAllTopic ();
        Collections.sort (allInstanceTopic);
        List<Theme> allInstanceTheme = themeService.getAllThemes ();
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        model.addAttribute ("allInstanceTheme", allInstanceTheme);
        model.addAttribute ("topicForm", new Topic ());
        //вытащим из URL id что-бы найти название темы в которой лежит топик
        model.addAttribute ("idTheme", postURL (request));
        return "topic";
    }
   //БЛОК СОЗДАНИЯ НОВОГО ТОПИКА
    private int id;
    @RequestMapping(value = "/createTopic", method = RequestMethod.GET)
    public String pageCreateTopic(Model model, HttpServletRequest request) {
        model.addAttribute ("topicForm", new Topic ());
        id = thisURL (request);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public String addTopic(@ModelAttribute("topicForm") Topic topicForm, Model model) {
        if (topicForm.getId () == null) {
            topicForm.setUsername (SecurityContextHolder.getContext ().getAuthentication ().getName ());
            topicForm.setThemeId (this.id - 1);
            topicForm.setLastPostDate (new Date ());
            topicService.save (topicForm);
        }
        return "redirect:/topic/" + id;
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteTopic/{id}", method = RequestMethod.GET)
    public String deleteTopic(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        topicService.delete (id);
        return "redirect:/topic/" + thisURL(request);
    }

    //БЛОК РЕДАКТИРОВАНИЯ
    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.GET)
    public String updateTopic(@PathVariable("id") int id, Model model) {
        model.addAttribute ("topicForm", topicService.getAllTopic ().get (id-1));
        List<Topic> allInstanceTopic =  topicService.getAllTopic ();
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.POST)
    public String updateTopic(@PathVariable("id") int id,
                              @ModelAttribute("topicForm") Topic topicForm, HttpServletRequest request) {
        topicForm.setLastPostDate (topicService.getAllTopic ().get (id-1).getLastPostDate ());
        topicForm.setUsername (topicService.getAllTopic ().get (id-1).getUsername ());
        topicForm.setThemeId (topicService.getAllTopic ().get (id-1).getThemeId ());
        topicService.save (topicForm);

        return "redirect:/topic/" + thisURL(request);
    }
    //

    private int thisURL(HttpServletRequest request) {
        String url = request.getHeader("referer"); //URL предыдущая страница
        return Integer.parseInt (url.split ("/topic/")[1]);
    }

    private int postURL(HttpServletRequest request) {
        //вытащим из URL id что-бы найти название темы в которой лежит топик
        String url = request.getRequestURI ();//URL текущая страница
        return Integer.parseInt (url.split ("/topic/")[1]) - 1;
    }
}