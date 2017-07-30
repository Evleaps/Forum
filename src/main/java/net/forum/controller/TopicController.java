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
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        System.out.println ( thisURL (request) );
        System.out.println (themeService.getAllThemes ().size () );
        model.addAttribute ("themeForm", themeService.findOne (thisURL (request)));//для названия в заголовке
        model.addAttribute ("topicForm", new Topic ());
        return "topic";
    }
   //БЛОК СОЗДАНИЯ НОВОГО ТОПИКА
    private int id;
    @RequestMapping(value = "/createTopic", method = RequestMethod.GET)
    public String pageCreateTopic(Model model, HttpServletRequest request) {
        model.addAttribute ("topicForm", new Topic ());
        id = postURL (request);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public String addTopic(@ModelAttribute("topicForm") Topic topicForm, Model model) {
        if (topicForm.getId () == null) {
            topicForm.setUsername (SecurityContextHolder.getContext ().getAuthentication ().getName ());
            topicForm.setThemeId (id);
            topicForm.setLastPostDate (new Date ());
            topicService.save (topicForm);
        }
        return "redirect:/topic/" + id;
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteTopic/{id}", method = RequestMethod.GET)
    public String deleteTopic(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        topicService.delete (id);
        return "redirect:/topic/" + postURL(request);
    }

    //БЛОК РЕДАКТИРОВАНИЯ
    private int url;
    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.GET)
    public String updateTopic(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        model.addAttribute ("topicForm", topicService.findOne (id));
        List<Topic> allInstanceTopic =  topicService.getAllTopic ();
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        url = postURL (request);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.POST)
    public String updateTopic(@PathVariable("id") int id,
                              @ModelAttribute("topicForm") Topic topicForm) {
        topicForm.setLastPostDate (topicService.findOne (id).getLastPostDate ());
        topicForm.setUsername (topicService.findOne (id).getUsername ());
        topicForm.setThemeId (topicService.findOne (id).getThemeId ());
        topicService.save (topicForm);

        return "redirect:/topic/" + url;
    }
    //

    private int postURL(HttpServletRequest request) {
        String url = request.getHeader("referer"); //URL предыдущая страница
        return Integer.parseInt (url.split ("/topic/")[1]);
    }

    private int thisURL(HttpServletRequest request) {
        String url = request.getRequestURI ();//URL текущая страница
        return Integer.parseInt (url.split ("/topic/")[1]);
    }
}