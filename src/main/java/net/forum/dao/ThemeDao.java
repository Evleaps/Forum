package net.forum.dao;

import net.forum.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Ромчи on 25.07.2017.
 */
public interface ThemeDao extends JpaRepository<Theme, Long> {

    @Query("select u from Theme order by u.lastPastDate desc")
    Page<Theme> findAll(Pageable pageable);

}
