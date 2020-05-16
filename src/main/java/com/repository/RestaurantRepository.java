package com.repository;

import com.model.Restaurant;
import com.to.RestaurantTo;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<RestaurantTo> getAll();

    RestaurantTo winner();

    List<Restaurant> getAllWithTodayMenu();
}
