package com.repository;

import com.model.Restaurant;
import com.to.RestaurantTo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository{
    private CrudRestaurantRepository restaurantRepository;

    private CrudMenuRepository crudMenuRepository;

    private VoteRepository voteRepository;

    public RestaurantRepositoryImpl(CrudRestaurantRepository restaurantRepository,
                                    CrudMenuRepository crudMenuRepository,
                                    VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.crudMenuRepository = crudMenuRepository;
        this.voteRepository = voteRepository;
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
    @Transactional
    public List<RestaurantTo> getAll() {
        List<Restaurant> all = restaurantRepository.findAll();
        return all.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getTitle(), voteRepository.countPerToday(restaurant.id())))
                .collect(Collectors.toList());
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

    @Override
    public RestaurantTo winner(){
        if( LocalTime.now().compareTo(LocalTime.of(11, 0)) >= 0){
            List<RestaurantTo> all = getAll();
            Optional<RestaurantTo> max = all.stream().max(Comparator.comparingInt(RestaurantTo::getVotes));
            return max.orElse(null);
        }
        return null;
    }



}
