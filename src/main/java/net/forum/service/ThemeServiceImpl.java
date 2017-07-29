package net.forum.service;

import net.forum.dao.ThemeDao;
import net.forum.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  Created by Ромчи on 25.07.2017.
 */

@Service
public class ThemeServiceImpl implements ThemeService{

    @Autowired
    private ThemeDao themeDao;

    @Override
    public void save(Theme theme) {
        themeDao.saveAndFlush (theme);
    }

    @Override
    public void delete(long id) {
        themeDao.delete (id);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeDao.findAll ();
    }
}
