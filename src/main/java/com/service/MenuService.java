package com.service;

import com.model.Menu;
import com.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {
    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.getAll(restaurantId);
    }

    public void delete(int id) {
        checkNotFoundWithId(menuRepository.delete(id), id);
    }

    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.get(id), id);
    }

    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Menu save = menuRepository.save(menu, restaurantId);
        return checkNotFoundWithId(save, save.id());
    }

    public Menu getTodayMenu(int restaurantId) {
        return menuRepository.getTodayMenu(restaurantId);
    }
}
