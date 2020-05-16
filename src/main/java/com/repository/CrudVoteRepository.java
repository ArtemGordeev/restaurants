package com.repository;

import com.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findAllByUserId(int userId);

    Vote findByUserIdAndDateIsLike(int userId, LocalDate date);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=:restaurantId and v.date = current_date")
    int countPerToday(@Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v left join fetch v.restaurant WHERE v.date=:localDate")
    List<Vote> getAllByDate(@Param("localDate") LocalDate localDate);

    @Query("SELECT v FROM Vote v left join fetch v.restaurant")
    List<Vote> getAll();
}
