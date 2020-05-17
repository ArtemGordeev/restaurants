package com.service;

import com.model.Dish;
import com.repository.CrudDishRepository;
import com.repository.CrudMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    CrudDishRepository crudDishRepository;

    CrudMenuRepository crudMenuRepository;

    public DishService(CrudDishRepository crudDishRepository, CrudMenuRepository crudMenuRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    public List<Dish> getAll(int menuId) {
        return crudDishRepository.findAllByMenuId(menuId);
    }

    public void delete(int id) {
        boolean deleted = crudDishRepository.delete(id) != 0;
        checkNotFoundWithId(deleted, id);
    }

    public Dish get(int dishId) {
        Dish dish = crudDishRepository.findById(dishId).orElse(null);
        return checkNotFoundWithId(dish, dishId);
    }

    @Transactional
    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setMenu(crudMenuRepository.getOne(menuId));
        Dish save = crudDishRepository.save(dish);
        return checkNotFoundWithId(save, save.id());
    }

}
