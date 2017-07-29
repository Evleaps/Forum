package net.forum.dao;

import net.forum.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ромчи on 25.07.2017.
 */
public interface ThemeDao extends JpaRepository<Theme, Long> {
}
