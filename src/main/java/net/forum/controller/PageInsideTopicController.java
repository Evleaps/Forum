package net.forum.controller;

import net.forum.model.Message;
import net.forum.model.Theme;
import net.forum.model.Topic;
import net.forum.model.User;
import net.forum.service.MessageService;
import net.forum.service.ThemeService;
import net.forum.service.TopicService;
import net.forum.service.UserService;
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
 Controller for {@link Message}
 */

@Controller
public class PageInsideTopicController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ThemeService themeService;

    private int  id;
    private int  idURL;
    private Date date;

    @RequestMapping(value = "chat/{id}", method = RequestMethod.GET)
    public String welcome(Model model, HttpServletRequest request) {
        List<Message> allInstanceMessages = messageService.getAllMessages ();
        List<Topic> allInstanceTopic = topicService.getAllTopic ();
        Collections.reverse (allInstanceMessages);//что-бы новые сообщения были вверху страницы
        model.addAttribute ("allInstanceMessages", allInstanceMessages);//jsp увидит поля всех инстансов Message
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        model.addAttribute ("messageForm", new Message ());//отправляем в конструктор
        //вытащим из URL id что-бы найти название топика в котором мы находимся
        String url = request.getRequestURI ();//URL текущая страница
        idURL = Integer.parseInt (url.split ("/chat/")[1]);//нужен для возвращения на ту же страницу POST
        id = idURL - 1; //А это нужно, что-бы в JSP мы знали, в каком именно топике находимся и какие отображать сообщения
        model.addAttribute ("idTopic", id);
        return "chat";
    }

    /*Тут мы принимаем наш атрибут, который ищется по имени messageForm и хранит в себе инстанс Message,
       * проверка на null (Long obj)
        * добавляем имя юзера
        * добавляем дату и сохраняем в бд*/
    @RequestMapping(value = "chat", method = RequestMethod.POST)
    public String addMessage(@ModelAttribute("messageForm") Message messageForm, Model model){
        if(messageForm.getId() == null){
            messageForm.setUsername (SecurityContextHolder.getContext ().getAuthentication ().getName ());
            date = new Date ();
            messageForm.setDate (date);
            //обновим даты, ибо появилось новое сообщение
            updateDataPost ();
            messageForm.setTopicId (id);
            messageService.save (messageForm);
        }
        return "redirect:/chat/" + idURL;
    }

    private void updateDataPost() {
        Topic topic = new Topic ();
        Theme theme = new Theme ();

        topic.setId (topicService.getAllTopic ().get (id).getId ());
        topic.setThemeId (topicService.getAllTopic ().get (id).getThemeId ());
        topic.setUsername (topicService.getAllTopic ().get (id).getUsername ());
        topic.setDescription (topicService.getAllTopic ().get (id).getDescription ());
        topic.setLastPostDate (date);
        topic.setTopicName (topicService.getAllTopic ().get (id).getTopicName ());
        topicService.save (topic);

        int idTheme = topicService.getAllTopic ().get (id).getThemeId ();
        theme.setId (themeService.getAllThemes ().get (idTheme).getId ());
        theme.setDescription (themeService.getAllThemes ().get (idTheme).getDescription ());
        theme.setLastPostDate (date);
        theme.setThemeName (themeService.getAllThemes ().get (idTheme).getThemeName ());
        themeService.save (theme);
    }
}
