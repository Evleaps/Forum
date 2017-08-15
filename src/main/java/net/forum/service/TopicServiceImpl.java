package net.forum.service;

import net.forum.dao.TopicDao;
import net.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
    public Topic findOne(long id) {
        return topicDao.findOne (id);
    }

    @Override
    public Page<Topic> findAll(Pageable pageable, long themeId) {
        return topicDao.findAll (pageable, Math.toIntExact (themeId));
    }

    @Override
    public List<Topic> getAllTopic() {
        return topicDao.findAll ();
    }
}