package net.forum.dao;

import net.forum.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ромчи on 25.07.2017.
 */
public interface TopicDao extends JpaRepository<Topic, Long> {
}
