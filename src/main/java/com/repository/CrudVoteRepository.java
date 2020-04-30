package com.repository;

import com.model.Restaurant;
import com.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findAllByUserId(int userId);

    Vote findByUserIdAndDateTimeAfter(int userId, LocalDateTime dateTime);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=:restaurantId and v.dateTime > current_date"  )
    int countPerToday(@Param("restaurantId") int restaurantId);
}
