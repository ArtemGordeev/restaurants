package com.service;

import com.model.Menu;
import com.repository.CrudMenuRepository;
import com.repository.CrudRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {
    CrudMenuRepository crudMenuRepository;

    CrudRestaurantRepository crudRestaurantRepository;

    public MenuService(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.findAllByRestaurantId(restaurantId);
    }

    public void delete(int id) {
        boolean deleted = crudMenuRepository.delete(id) != 0;
        checkNotFoundWithId(deleted, id);
    }

    public Menu get(int id) {
        Menu menu = crudMenuRepository.findById(id).orElse(null);
        return checkNotFoundWithId(menu, id);
    }

    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        Menu save = crudMenuRepository.save(menu);
        return checkNotFoundWithId(save, save.id());
    }

    public Menu getTodayWithDishes(int restaurantId) {
        return crudMenuRepository.getTodayMenuWithDishes(restaurantId);
    }
}
