package net.forum.controller;

import net.forum.service.TopicService;
import net.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

/**
 * Controller for{@Link }
 */

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String topicPage(Model model) {
        List<Topic> allInstanceTopic =  topicService.getAllTopic ();
        model.addAttribute ("allInstanceTopic", allInstanceTopic);
        model.addAttribute ("topicForm", new Topic ());
        return "topic";
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String addTopic(@ModelAttribute("topicForm") Topic topicForm, Model model) {
        if (topicForm.getId () == null) {
            topicForm.setLogin (SecurityContextHolder.getContext ().getAuthentication ().getName ());
            topicService.save (topicForm);
        }
        return "redirect:/topic";
    }
}
