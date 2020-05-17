package com.to;

import com.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTo extends BaseTo{
    private LocalDate date;
    private LocalTime time;
    private String restaurant;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate date, LocalTime time, String restaurant) {
        super(id);
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getRestaurant() {
        return restaurant;
    }



    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", restaurant='" + restaurant + '\'' +
                '}';
    }
}
