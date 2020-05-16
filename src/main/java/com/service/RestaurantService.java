package com.service;

import com.model.Restaurant;
import com.repository.RestaurantRepository;
import com.to.RestaurantTo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant save = restaurantRepository.save(restaurant);
        return checkNotFoundWithId(save, save.id());
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public List<RestaurantTo> getAll() {
        return restaurantRepository.getAll();
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public RestaurantTo winner(){
        return restaurantRepository.winner();
    }

    public List<Restaurant> getAllWithTodayMenu() {
        return restaurantRepository.getAllWithTodayMenu();
    }
}
