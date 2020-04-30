package com.repository;

import com.model.Menu;
import com.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Native;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository  extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    List<Menu> findAllByRestaurantId(int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId and m.dateTime > current_date"  )
    Menu getTodayMenu(@Param("restaurantId") int restaurantId);
}
