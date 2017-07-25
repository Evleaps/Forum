package net.forum.validator;

import net.forum.model.User;
import net.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link User} class,
 * implements {@link Validator} interface.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
/**Если поле Login не заполнили, выводим сообщение, что оно обязательно к заполнению*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if (user.getLogin ().length() < 3 || user.getLogin ().length() > 30) {
            errors.rejectValue("login", "Size.userForm.login");
        }
/**Если такой пипл уже есть в БД*/
        if (userService.findByUsername(user.getLogin ()) != null) {
            errors.rejectValue("login", "Duplicate.userForm.login");
        }
/**Если поле password не заполнили, выводим сообщение, что оно обязательно к заполнению*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        /**Если поле password имеет короткий пароль, выводим сообщение из проперти*/
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.userForm.password");
        }
/**Пароль должен содержать минимум 1 число*/
        if (!user.getPassword().matches ("\\d")) {
            errors.rejectValue("password", "RequiredMinOneNumber.userForm.password");
        }


 /**Пароль не должен содержать пунктуации*/
        if (user.getPassword().toString ().matches ("\\p{Punct}")) {
            errors.rejectValue("password", "Punctuation.userForm.password");
        }

/**Если поле password не совпадает с повторите пароль, выводим сообщение из проперти*/
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
