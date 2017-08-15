package net.forum.dao;

import net.forum.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 */
public interface ThemeDao extends JpaRepository<Theme, Long> {

    @Query("select t from Theme t order by t.lastPostDate desc")
    Page<Theme> findAll(Pageable pageable);

}
