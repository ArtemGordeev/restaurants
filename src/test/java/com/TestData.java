package com;

import com.model.Dish;
import com.model.Menu;
import com.model.Restaurant;
import com.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static com.model.AbstractBaseEntity.START_SEQ;

public class TestData {

    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsComparator(Dish.class, "menu");
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingFieldsComparator(Menu.class, "dishes", "restaurant");
    public static TestMatcher<Menu> MENU_MATCHER_INCLUDE_DISHES = TestMatcher.usingFieldsComparator(Menu.class, "dishes", "restaurant");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "menus", "votes");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER_INCLUDE_MENU = TestMatcher.usingFieldsComparator(Restaurant.class,  "votes");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class, "user");
    //public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsComparator(Vote.class, "restaurant", "user");

    public static final int RESTAURANT_ID = START_SEQ + 3;
    public static final int RESTAURANT2_ID = START_SEQ + 4;
    public static final int MENU_ID = START_SEQ + 5;
    public static final int MENU2_ID = START_SEQ + 6;
    public static final int DISH1_ID = START_SEQ + 7;
    public static final int DISH2_ID = START_SEQ + 8;
    public static final int DISH3_ID = START_SEQ + 9;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Pizza", 150);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Tea", 50);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Burger", 200);
    public static final Menu MENU = new Menu(MENU_ID, "Monday", LocalDate.now());
    public static final Menu MENU2 = new Menu(MENU2_ID, "Monday", LocalDate.now());
    public static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID, "KFC");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "Burger King");

    public static final List<Dish> DISHES = List.of(DISH1, DISH2);
    public static final List<Menu> MENUS = List.of(MENU);
    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT, RESTAURANT2);

    static {
        MENU.setDishes(List.of(DISH1, DISH2));
        RESTAURANT.setMenus(List.of(MENU));
        MENU.setDishes(List.of(DISH3));
        RESTAURANT2.setMenus(List.of(MENU2));
    }

}
