package net.forum.controller;

import net.forum.model.User;
import net.forum.service.SecurityService;
import net.forum.service.UserService;
import net.forum.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for {@link User}
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private SecurityService securityService;

    /* При получении запроса "/registration" переводим на соответствующую страницу*/
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute ("userForm", new User ());
        return "registration";
    }

    /* Получаем данные, делегируем проверку сервисам и переводим на страницу чата*/
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername (), userForm.getConfirmPassword());

        return "redirect:/forum/0";//перенаправляем не на jsp, а на адресс "/"
    }
}
