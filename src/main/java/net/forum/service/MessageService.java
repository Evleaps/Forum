package net.forum.service;

import net.forum.model.Message;

import java.util.List;

public interface MessageService {

     void save(Message message);

     List<Message> getAllMessages();
}
