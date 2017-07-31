package net.forum.controller;

import net.forum.model.Theme;
import net.forum.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * Controller for POJO {@link net.forum.model.Theme}
 *
 * Это контроллер работает с формой(JSP) создания новой темы, добавляет новую тему и переадресовывает
 * пользователя на главную страницу.
 */

@Controller
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    /*страница форума, */
    @RequestMapping(value = {"/","/forum"}, method = RequestMethod.GET)
    public String forum(Model model) {
        String userRole = SecurityContextHolder.getContext ().getAuthentication ().getAuthorities ().toString ();
        List<Theme> allInstanceTheme = themeService.getAllThemes ();
        Collections.sort (allInstanceTheme);
        model.addAttribute ("userRole", userRole);
        model.addAttribute ("allInstenceTheme", allInstanceTheme);
        model.addAttribute ("themeForm", new Theme ());
        return "forum";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.GET)
    public String pageCreateTheme(Model model) {
        model.addAttribute ("themeForm", new Theme ());
        return "createUpdateTheme";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.POST)
    public String addTheme(@ModelAttribute("themeForm") Theme themeForm, Model model) {
        if (themeForm.getId () == null) {
            themeForm.setLastPostDate (new Date ());
            themeService.save (themeForm);
        }
        return "redirect:/forum";
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteTheme/{id}", method = RequestMethod.GET)
    public String deleteTheme(@PathVariable("id") int id, Model model) {
        String userRole = SecurityContextHolder.getContext ().getAuthentication ().getAuthorities ().toString ();
        if (userRole.equals ("[ROLE_ADMIN]")) {
            themeService.delete (id);
        }
        return "redirect:/forum";
    }

    //БЛОК РЕДАКТИРОВАНИЯ
    @RequestMapping(value = "/updateTheme/{id}", method = RequestMethod.GET)
    public String updateTheme(@PathVariable("id") int id, Model model) {
        model.addAttribute ("themeForm", themeService.findOne (id));
        List<Theme> allInstanceTheme =  themeService.getAllThemes ();
        model.addAttribute ("allInstanceTheme", allInstanceTheme);
        return "createUpdateTheme";
    }

    @RequestMapping(value = "/updateTheme/{id}", method = RequestMethod.POST)
    public String updateTheme(@PathVariable("id") int id,
                              @ModelAttribute("themeForm") Theme themeForm) {
        themeForm.setLastPostDate (themeService.findOne (id).getLastPostDate ());
        String userRole = SecurityContextHolder.getContext ().getAuthentication ().getAuthorities ().toString ();
        if (userRole.equals ("[ROLE_ADMIN]")) {
            themeService.save (themeForm);
        }

        return "redirect:/forum";
    }
}
