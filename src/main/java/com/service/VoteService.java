package com.service;

import com.model.Restaurant;
import com.model.User;
import com.model.Vote;
import com.repository.CrudRestaurantRepository;
import com.repository.CrudUserRepository;
import com.repository.CrudVoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoteService {
    private CrudVoteRepository crudVoteRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    private CrudUserRepository crudUserRepository;

    public VoteService(CrudVoteRepository crudVoteRepository,
                       CrudRestaurantRepository crudRestaurantRepository,
                       CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    public List<Vote> getAll() {
        return crudVoteRepository.getAll();
    }

    @Transactional
    public Vote save(int restaurantId, int userId) {
        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantId);
        Vote vote = crudVoteRepository.findByUserIdAndDateIsLike(userId, LocalDate.now());
        if (vote != null) {
            if (vote.afterEleven()) {
                return null;
            } else {
                vote.setDate(LocalDate.now());
                vote.setTime(LocalTime.now());
                vote.setRestaurant(restaurant);
                return crudVoteRepository.save(vote);
            }
        }
        User user = crudUserRepository.getOne(userId);
        vote = new Vote(LocalDate.now(), LocalTime.now(), restaurant, user);
        return crudVoteRepository.save(vote);
    }

    public List<Vote> getAllByDate(LocalDate localDate) {
        return crudVoteRepository.getAllByDate(localDate);
    }

    public List<Vote> getAllToday() {
        return crudVoteRepository.getAllByDate(LocalDate.now());
    }

    public Restaurant getWinner() {
        List<Vote> all = getAllToday();
        HashMap<Restaurant, Integer> map = new HashMap<>();
        for (Vote vote : all) {
            Restaurant restaurant = vote.getRestaurant();
            if(map.containsKey(restaurant)){
                map.put(restaurant, map.get(restaurant)+1);
            }
            else {
                map.put(restaurant, 1);
            }
        }
        Restaurant winner = null;
        Integer max = 0;
        for (Map.Entry<Restaurant, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                winner = entry.getKey();
            }
        }
        return winner;
    }

    public int countPerToday(int restaurantId){
        return crudVoteRepository.countPerToday(restaurantId);
    }
}
