package net.forum.validator;

import net.forum.model.User;
import net.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
/**Если поле password не заполнили, выводим сообщение, что оно обязательно к заполнению*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
/**Если поле FirstName не заполнили, выводим сообщение, что оно обязательно к заполнению*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
/**Если поле LastName не заполнили, выводим сообщение, что оно обязательно к заполнению*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");

/**Длина логина от 3 до 30*/
        if (user.getUsername ().length() < 3 || user.getUsername ().length() > 30) {
            errors.rejectValue("username", "Size.userForm.username");
        }
/**Если такой пипл уже есть в БД*/
        if (userService.findByUsername(user.getUsername ()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
/**Логин не должен содержать пунктуации*/
        if (searchSumboil (user.getUsername (),"\\p{Punct}")) {
            errors.rejectValue("username", "NotPunctuation.userForm.form");
        }

/**поле Last name не должен содержать пунктуации*/
        if (searchSumboil (user.getLastName (),"\\p{Punct}")) {
            errors.rejectValue("lastName", "NotPunctuation.userForm.form");
        }

/**Поле First name не должен содержать пунктуации*/
        if (searchSumboil (user.getFirstName (),"\\p{Punct}")) {
            errors.rejectValue("firstName", "NotPunctuation.userForm.form");
        }

/**Если поле password имеет короткий пароль, выводим сообщение из проперти*/
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.userForm.password");
        }
/**Пароль должен содержать минимум 1 число*/
        if (!searchSumboil (user.getPassword(),"\\d+")) {
            errors.rejectValue("password", "RequiredMinOneNumber.userForm.password");
        }

 /**Пароль должен содержать как минимум один знак пунктуации**/
        if (!searchSumboil (user.getPassword (),"\\p{Punct}+")) {
            errors.rejectValue("password", "Punctuation.userForm.form");
        }

/**Пароль должен содержать как минимум один знак верхнего и нижнего регистра**/
        if (!searchSumboil (user.getPassword(),"\\p{Upper}+\\p{Lower}+")) {
            errors.rejectValue("password", "Letter.userForm.password");
        }

/**Если поле password не совпадает с повторите пароль, выводим сообщение из проперти*/
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            System.out.println ("PASSSSSSSSSSSWORD="+user.getPassword () );
            System.out.println ("PASSSSSSSSSSSWORD="+user.getConfirmPassword () );
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }

    private boolean searchSumboil(String str, String regex) {
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (str);

        if (matcher.find ()) return true;
        else return false;
    }
}
