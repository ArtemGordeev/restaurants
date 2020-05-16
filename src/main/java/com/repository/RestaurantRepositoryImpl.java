package com.repository;

import com.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository{
    private CrudRestaurantRepository restaurantRepository;

    private CrudMenuRepository crudMenuRepository;

    public RestaurantRepositoryImpl(CrudRestaurantRepository restaurantRepository, CrudMenuRepository crudMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    @Transactional
    public List<Restaurant> getAllWithTodayMenu() {
        List<Restaurant> all = restaurantRepository.findAll();
        for (Restaurant restaurant : all) {
            restaurant.setMenus(List.of(crudMenuRepository.getTodayMenuWithDishes(restaurant.id())));
        }
        return all;
    }


    @Override
    public boolean delete(int id) {
        return restaurantRepository.delete(id) != 0;
    }
}
