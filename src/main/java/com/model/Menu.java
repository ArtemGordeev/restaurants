package com.model;

import com.HasId;
import com.View;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menus_unique_restaurant_id_date_idx")})
public class Menu extends AbstractBaseEntity implements HasId {
    @Column(name = "title")
    @NotBlank
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Menu() {
        date = LocalDateTime.now().toLocalDate();
    }

    public Menu(Integer id) {
        super(id);
    }

    public Menu(String title) {
        super(null);
        this.title = title;
    }

    public Menu(String title, @NotNull LocalDate date) {
        this.title = title;
        this.date = date;
    }

    public Menu(Integer id, String title, @NotNull LocalDate date) {
        super(id);
        this.title = title;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
//                ", dishes=" + dishes +
//                ", restaurant=" + restaurant +
                ", date=" + date +
                '}';
    }
}
