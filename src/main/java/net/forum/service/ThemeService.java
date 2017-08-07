package net.forum.service;


import net.forum.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 Created by Ромчи on 25.07.2017.
 */


public interface ThemeService {
    void save(Theme theme);

    void delete(long id);

    Theme findOne(long id);

  //  Page findAll(PageRequest pageRequest);
    Page findAll(Pageable pageable);

    List<Theme> getAllThemes();
}
