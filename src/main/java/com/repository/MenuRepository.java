package com.repository;

import com.model.Menu;
import com.model.Restaurant;

import java.util.List;

public interface MenuRepository {
    // null if not found, when updated
    Menu save(Menu menu, int restaurantId);

    void delete(int id);

    Menu get(int id);

    List<Menu> getAll(int restaurantId);

    Menu getTodayMenu(int restaurantId);
}
