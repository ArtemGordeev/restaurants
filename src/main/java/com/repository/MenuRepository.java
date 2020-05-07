package com.repository;

import com.model.Menu;

import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu, int restaurantId);

    void delete(int id);

    Menu get(int id);

    List<Menu> getAll(int restaurantId);

    Menu getTodayMenu(int restaurantId);
}
