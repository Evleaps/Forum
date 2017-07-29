package net.forum.service;

import net.forum.dao.TopicDao;
import net.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 Created by Ромчи on 25.07.2017.
 */

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public void save(Topic topic) {
        topicDao.saveAndFlush (topic);
    }

    @Override
    public void delete(long id) {
        topicDao.delete (id);
    }


    @Override
    public List<Topic> getAllTopic() {
        return topicDao.findAll ();
    }
}
