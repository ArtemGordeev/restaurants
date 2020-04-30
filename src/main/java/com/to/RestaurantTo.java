package com.to;

public class RestaurantTo extends BaseTo {

    private String title;

    private int votes;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String title, int votes) {
        super(id);
        this.title = title;
        this.votes = votes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                ", id=" + id +
                "title='" + title + '\'' +
                ", votes=" + votes +
                '}';
    }
}
