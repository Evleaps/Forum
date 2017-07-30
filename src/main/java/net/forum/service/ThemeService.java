package net.forum.service;


import net.forum.model.Theme;

import java.util.List;

/**
 Created by Ромчи on 25.07.2017.
 */


public interface ThemeService {
    void save(Theme theme);

    void delete(long id);

    Theme findOne(long id);

    List<Theme> getAllThemes();
}
