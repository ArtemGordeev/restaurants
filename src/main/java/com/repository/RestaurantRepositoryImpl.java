package com.repository;

import com.model.Restaurant;
import com.to.RestaurantTo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository{
    CrudRestaurantRepository restaurantRepository;

    VoteRepository voteRepository;

    public RestaurantRepositoryImpl(CrudRestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    @Override
    //@Transactional
    public List<RestaurantTo> getAll() {
        List<Restaurant> all = restaurantRepository.findAll();
        return all.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getTitle(), voteRepository.countPerToday(restaurant.id())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Override
    public RestaurantTo winner(){
        if( LocalDateTime.now().toLocalTime().compareTo(LocalTime.of(11, 0)) >= 0){
            List<RestaurantTo> all = getAll();
            Optional<RestaurantTo> max = all.stream().max(Comparator.comparingInt(RestaurantTo::getVotes));
            return max.orElse(null);
        }
        return null;
    }

}
