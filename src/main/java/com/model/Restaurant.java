package com.model;

import com.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseEntity implements HasId {

    @Column(name = "title")
    @NotBlank
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Menu> menus;

    public Restaurant() {
        title = "";
    }

    public Restaurant(Integer id, String title) {
        super(id);
        this.title = title;
    }

    public Restaurant(String title) {
        super(null);
        this.title = title;
    }

    public Restaurant(String title, List<Menu> menus) {
        this(null, title, menus);
    }

    public Restaurant(Integer id, String title, List<Menu> menus) {
        super(id);
        this.title = title;
        this.menus = menus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }
}
