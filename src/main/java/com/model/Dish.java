package com.model;

import com.HasId;
import com.View;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity implements HasId {
    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "price")
    @Min(0)
    private int price; //in cents

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Menu menu;

    public Dish() {
    }

    public Dish(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public Dish(Integer id, String description, int price) {
        super(id);
        this.description = description;
        this.price = price;
    }

    public Dish(Integer id, String description, int price, @NotNull Menu menu) {
        super(id);
        this.description = description;
        this.price = price;
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
