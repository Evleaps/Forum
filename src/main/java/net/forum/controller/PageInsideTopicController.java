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
    private Date date;

    @RequestMapping(value = "chat/{id}", method = RequestMethod.GET)
    public String welcome(@PathVariable("id") int id, Model model) {
        List<Message> allInstanceMessages = messageService.getAllMessages ();
        Collections.reverse (allInstanceMessages);//что-бы новые сообщения были вверху страницы
        model.addAttribute ("allInstanceMessages", allInstanceMessages);//jsp увидит поля всех инстансов Message
        model.addAttribute ("topicForm", topicService.findOne (id));
        model.addAttribute ("messageForm", new Message ());//отправляем в конструктор
        this.id = id;
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
        return "redirect:/chat/" + id;
    }

    private void updateDataPost() {
        Topic topic = new Topic ();
        Theme theme = new Theme ();

        topic.setId (topicService.findOne (id).getId ());
        topic.setThemeId (topicService.findOne (id).getThemeId ());
        topic.setUsername (topicService.findOne (id).getUsername ());
        topic.setDescription (topicService.findOne (id).getDescription ());
        topic.setLastPostDate (date);
        topic.setTopicName (topicService.findOne (id).getTopicName ());
        topicService.save (topic);

        theme.setId (themeService.findOne (topicService.findOne (id).getThemeId ()).getId ());
        theme.setDescription (themeService.findOne (id).getDescription ());
        theme.setLastPostDate (date);
        theme.setThemeName (themeService.findOne (id).getThemeName ());
        themeService.save (theme);
    }
}
