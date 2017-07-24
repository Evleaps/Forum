package net.forum.service;

import net.forum.dao.RoleDao;
import net.forum.dao.UserDao;
import net.forum.model.Role;
import net.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * 1. Мы берем пароль пользователя и преобразуем в более сложный
 * пароль через bCrypt и сохраняем значение
 * 2. Присваиваем роли пользователю, в данном случае только 1(юзер, но не админ)
 * 3. Установили эти роли
 * 4. Сохранили юзера
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        //1.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //2.
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        //3.
        user.setRoles(roles);
        //4.
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll ();//возвращаем всех пользователей
    }


}
