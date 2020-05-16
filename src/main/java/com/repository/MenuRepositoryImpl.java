package com.repository;

import com.model.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MenuRepositoryImpl implements MenuRepository {
    CrudMenuRepository crudMenuRepository;

    CrudRestaurantRepository crudRestaurantRepository;

    public MenuRepositoryImpl(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public boolean delete(int id) {
        return crudMenuRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return crudMenuRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMenuRepository.save(menu);
    }

    @Override
    public Menu getTodayMenuWithDishes(int restaurantId) {
        return crudMenuRepository.getTodayMenuWithDishes(restaurantId);
    }

}
