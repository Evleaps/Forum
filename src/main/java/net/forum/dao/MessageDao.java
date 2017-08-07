
package net.forum.dao;

import net.forum.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message, Long> {
}

