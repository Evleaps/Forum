package net.forum.service;

import net.forum.model.Topic;

import java.util.List;

/**
 * Created by Ромчи on 25.07.2017.
 */
public interface TopicService {
    void save(Topic topic);

    void delete(long id);

    List<Topic> getAllTopic();
}
