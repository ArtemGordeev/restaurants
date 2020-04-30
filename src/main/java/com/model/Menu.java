package com.model;

import com.HasId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity implements HasId {
    @Column(name = "title")
    String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    List<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    Restaurant restaurant;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    public Menu() {
        dateTime = LocalDateTime.now();
    }

    public Menu(Integer id) {
        super(id);
    }

    public Menu(String title) {
        super(null);
        this.title = title;
    }

    public Menu(String title, @NotNull LocalDateTime dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }

    public Menu(Integer id, String title, @NotNull LocalDateTime dateTime) {
        super(id);
        this.title = title;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "title='" + title + '\'' +
                '}';
    }
}
