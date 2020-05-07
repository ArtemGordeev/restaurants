package com.repository;

import com.model.Vote;
import com.to.VoteTo;

import java.util.List;

public interface VoteRepository {
    Vote save(int restaurantId, int userId);

    List<VoteTo> getAll(int userId);

    int countPerToday(int restaurantId);
}
