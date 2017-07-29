package net.forum.model;

import javax.persistence.*;
import java.util.Date;

/**
  Created by Ромчи on 25.07.2017.
 */
@Entity
@Table(name = "topic")
public class Topic implements Comparable<Topic> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name = "username")//знаем создателя топика
    private String username;

    @Column(name = "topicName")//знаем ися топика
    private String topicName;

    @Column(name = "description")//описание
    private String description;

    @Column(name = "lastPostDate")//знаем время послед сообщения
    private Date lastPostDate;

    @Column(name = "themeId")//знаем к какой теме привязан топик
    private int themeId;

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Override
    public int compareTo(Topic that) {
        return that.lastPostDate.compareTo (this.getLastPostDate ());
    }
}
