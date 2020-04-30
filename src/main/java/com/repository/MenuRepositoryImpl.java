package com.repository;

import com.model.Menu;
import com.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

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
    public void delete(int id) {
        checkNotFoundWithId(crudMenuRepository.delete(id) != 0, id);
    }

    @Override
    public Menu get(int id) {
        return checkNotFoundWithId(crudMenuRepository.findById(id).orElse(null), id);
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMenuRepository.save(menu);
    }

    @Override
    public Menu getTodayMenu(int restaurantId) {
        return crudMenuRepository.getTodayMenu(restaurantId);
    }

}
