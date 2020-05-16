package com.repository;

import com.model.Restaurant;
import com.model.User;
import com.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository{

    private CrudVoteRepository crudVoteRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    private CrudUserRepository crudUserRepository;

    public VoteRepositoryImpl(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public List<Vote> getAll() {
         return crudVoteRepository.getAll();
    }

    public List<Vote> getAllByDate(LocalDate localDate) {
        return crudVoteRepository.getAllByDate(localDate);
    }

    @Override
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

    public int countPerToday(int restaurantId){
        return crudVoteRepository.countPerToday(restaurantId);
    }
}
