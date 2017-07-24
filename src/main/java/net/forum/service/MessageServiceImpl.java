
package net.forum.service;

import net.forum.dao.MessageDao;
import net.forum.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public void save(Message message) {
        messageDao.save (message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDao.findAll();//получить все записи
    }
}

