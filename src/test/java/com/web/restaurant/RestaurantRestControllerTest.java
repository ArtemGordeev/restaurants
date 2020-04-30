package com.web.restaurant;


import com.model.Menu;
import com.model.Restaurant;
import com.repository.MenuRepository;
import com.repository.RestaurantRepository;
import com.util.exception.NotFoundException;
import com.web.AbstractControllerTest;
import com.web.SecurityUtil;
import com.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static com.TestData.*;
import static com.TestUtil.readFromJson;
import static com.UserTestData.ADMIN_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/restaurants/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void get() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                /*.with(userHttpBasic(USER))*/)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT));
    }

//    @Test
//    void getUnauth() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
//                .andExpect(status().isUnauthorized());
//    }

    @Test
    void getNotFound() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.get(REST_URL + "100100")
                /*.with(userHttpBasic(USER))*/)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID)
                /*.with(userHttpBasic(ADMIN))*/)
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantRepository.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.delete(REST_URL + "100100")
                /*.with(userHttpBasic(ADMIN))*/)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        Restaurant updated = new Restaurant(RESTAURANT_ID, "Burger King");
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                /*.with(userHttpBasic(ADMIN))*/)
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(RESTAURANT_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        Restaurant newRestaurant = new Restaurant(null, "Burger King");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                //.with(userHttpBasic(ADMIN))
        );

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(newId), newRestaurant);
    }

    @Test
    void getAll() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.get(REST_URL)
                /*.with(userHttpBasic(USER))*/)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTS));
    }
}