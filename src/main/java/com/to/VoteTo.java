package com.to;

import com.model.User;

import java.time.LocalDateTime;

public class VoteTo extends BaseTo{
    private LocalDateTime dateTime;
    private String restaurant;
    private User user;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDateTime dateTime, String restaurant, User user) {
        super(id);
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", restaurant='" + restaurant + '\'' +
                ", user=" + user +
                '}';
    }
}
