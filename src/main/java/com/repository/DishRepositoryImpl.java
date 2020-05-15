package com.repository;

import com.model.Dish;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DishRepositoryImpl implements DishRepository {

    CrudDishRepository crudDishRepository;

    CrudMenuRepository crudMenuRepository;

    public DishRepositoryImpl(CrudDishRepository crudDishRepository, CrudMenuRepository crudMenuRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.findAllByMenuId(menuId);
    }

    @Override
    public boolean delete(int id) {
        return crudDishRepository.delete(id) != 0;
    }

    @Override
    public Dish get(int dishId) {
        return crudDishRepository.findById(dishId).orElse(null);
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        dish.setMenu(crudMenuRepository.getOne(menuId));
        return crudDishRepository.save(dish);
    }
}
