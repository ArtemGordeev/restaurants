package com.service;

import com.model.Restaurant;
import com.repository.CrudMenuRepository;
import com.repository.CrudRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private CrudRestaurantRepository restaurantRepository;

    private CrudMenuRepository menuRepository;

    public RestaurantService(CrudRestaurantRepository restaurantRepository, CrudMenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant save = restaurantRepository.save(restaurant);
        return checkNotFoundWithId(save, save.id());
    }

    public Restaurant get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        return checkNotFoundWithId(restaurant, id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public void delete(int id) {
        boolean deleted = restaurantRepository.delete(id) != 0;
        checkNotFoundWithId(deleted, id);
    }

    @Transactional
    public List<Restaurant> getAllWithTodayMenu() {
        List<Restaurant> all = restaurantRepository.findAll();
        for (Restaurant restaurant : all) {
            restaurant.setMenus(List.of(menuRepository.getTodayMenuWithDishes(restaurant.id())));
        }
        return all;
    }
}
