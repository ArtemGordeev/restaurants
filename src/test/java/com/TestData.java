package com;

import com.model.Dish;
import com.model.Menu;
import com.model.Restaurant;
import com.to.RestaurantTo;
import com.to.VoteTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.model.AbstractBaseEntity.START_SEQ;

public class TestData {

    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsComparator(Dish.class, "menu");
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingFieldsComparator(Menu.class, "dishes", "restaurant");
    public static TestMatcher<Menu> MENU_MATCHER_INCLUDE_DISHES = TestMatcher.usingFieldsComparator(Menu.class, "dishes", "restaurant");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "menus", "votes");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER_INCLUDE_MENU = TestMatcher.usingFieldsComparator(Restaurant.class,  "votes");
    public static TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingFieldsComparator(RestaurantTo.class);
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class, "dateTime", "user");

    public static final int RESTAURANT_ID = START_SEQ + 2;
    public static final int MENU_ID = START_SEQ + 3;
    public static final int DISH1_ID = START_SEQ + 4;
    public static final int DISH2_ID = START_SEQ + 5;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Pizza", 150);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Tea", 50);
    public static final Menu MENU = new Menu(MENU_ID, "Monday", LocalDate.now());
    public static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID, "KFC");
    public static final RestaurantTo RESTAURANT_TO = new RestaurantTo(RESTAURANT_ID, "KFC", 0);

    public static final List<Dish> DISHES = List.of(DISH1, DISH2);
    public static final List<Menu> MENUS = List.of(MENU);
    public static final List<RestaurantTo> RESTAURANTS = List.of(RESTAURANT_TO);

    static {
        MENU.setDishes(List.of(DISH1, DISH2));
        RESTAURANT.setMenus(List.of(MENU));
    }

}
