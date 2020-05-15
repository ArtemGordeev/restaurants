package com.service;

import com.model.Dish;
import com.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAll(int menuId) {
        return dishRepository.getAll(menuId);
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id), id);
    }

    public Dish get(int dishId) {
        return checkNotFoundWithId(dishRepository.get(dishId), dishId);
    }

    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        Dish save = dishRepository.save(dish, menuId);
        return checkNotFoundWithId(save, save.id());
    }

}
