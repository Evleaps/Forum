package net.forum.dao;

import net.forum.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Так-как все топики хранятся в 1 таблице, мы выводим только те записи, которые соответствуют id темы в которой
 * создан топик.
 */
public interface TopicDao extends JpaRepository<Topic, Long> {

    @Query("select u from Topic u where u.themeId = :themeId order by u.lastPostDate desc")
    Page<Topic> findAll(Pageable pageable, @Param ("themeId") int themeId);
}
