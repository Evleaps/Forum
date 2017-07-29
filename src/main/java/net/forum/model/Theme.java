package net.forum.model;



import javax.persistence.*;
import java.util.Date;

/**
  Created by Ромчи on 25.07.2017.
 */
@Entity
@Table(name = "theme")
public class Theme implements Comparable<Theme>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name = "themeName")
    private String themeName;

    @Column(name = "description")//орписание
    private String description;

    @Column(name = "lastPostDate")
    private Date lastPostDate;

    public Theme() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    @Override
    public int compareTo(Theme that) {
        return that.lastPostDate.compareTo (this.getLastPostDate ());
    }
}
