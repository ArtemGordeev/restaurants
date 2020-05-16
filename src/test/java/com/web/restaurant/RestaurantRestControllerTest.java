package com.web.restaurant;


import com.model.Restaurant;
import com.service.RestaurantService;
import com.util.exception.NotFoundException;
import com.web.AbstractControllerTest;
import com.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.TestData.*;
import static com.TestUtil.readFromJson;
import static com.TestUtil.userHttpBasic;
import static com.UserTestData.ADMIN;
import static com.UserTestData.USER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/restaurants/";
    private static final String REST_ADMIN_URL = "/rest/admin/restaurants/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "100100")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "100100")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_ID, "Burger King");
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + RESTAURANT_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "Burger King");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(userHttpBasic(ADMIN))
        );

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    void getAllWithTodayMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER_INCLUDE_MENU.contentJson(RESTAURANTS));
    }
}