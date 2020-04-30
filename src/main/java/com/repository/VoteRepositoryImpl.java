package com.repository;

import com.model.Restaurant;
import com.model.User;
import com.model.Vote;
import com.to.VoteTo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    @Transactional
    public List<VoteTo> getAll(int userId) {
        List<Vote> all = crudVoteRepository.findAllByUserId(userId);
        return all.stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurant().getTitle(), vote.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Vote save(int restaurantId, int userId) {
        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantId);
        Vote vote = crudVoteRepository.findByUserIdAndDateTimeAfter(userId, LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        if (vote != null) {
            if (vote.afterEleven()) {
                return null;
            } else {
                vote.setDateTime(LocalDateTime.now());
                vote.setRestaurant(restaurant);
                return crudVoteRepository.save(vote);
            }
        }
        User user = crudUserRepository.getOne(userId);
        vote = new Vote(LocalDateTime.now(), restaurant, user);
        return crudVoteRepository.save(vote);
    }

    public int countPerToday(int restaurantId){
        return crudVoteRepository.countPerToday(restaurantId);
    }
}
