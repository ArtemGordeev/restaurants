package com.repository;

import com.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    List<Restaurant> getAllWithTodayMenu();
}
