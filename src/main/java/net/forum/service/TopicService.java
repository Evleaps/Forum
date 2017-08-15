package net.forum.service;

import net.forum.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Ромчи on 25.07.2017.
 */
public interface TopicService {
    void save(Topic topic);

    void delete(long id);

    Topic findOne(long id);

    Page<Topic> findAll(Pageable pageable, long themeId);

    List<Topic> getAllTopic();
}
