package com.service;

import com.model.Restaurant;
import com.model.Vote;
import com.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoteService {
    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public Vote save(int restaurantId, int userId) {
        return voteRepository.save(restaurantId, userId);
    }

    public List<Vote> getAllByDate(LocalDate localDate) {
        return voteRepository.getAllByDate(localDate);
    }

    public List<Vote> getAllToday() {
        return voteRepository.getAllByDate(LocalDate.now());
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
        //System.out.println("************Votes for winner: " + max);
        return winner;
    }
}
