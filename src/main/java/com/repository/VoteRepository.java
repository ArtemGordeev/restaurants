package com.repository;

import com.model.Vote;
import com.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(int restaurantId, int userId);

    List<Vote> getAll();

    int countPerToday(int restaurantId);

    List<Vote> getAllByDate(LocalDate localDate);
}
