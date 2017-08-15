package net.forum.dao;

import net.forum.model.Theme;
import net.forum.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
