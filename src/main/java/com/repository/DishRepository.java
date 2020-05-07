package com.repository;

import com.model.Dish;

import java.util.List;

public interface DishRepository {
    Dish save(Dish dish, int menuId);

    void delete(int id);

    Dish get(int id);

    List<Dish> getAll(int menuId);
}
