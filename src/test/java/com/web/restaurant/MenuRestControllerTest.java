package com.web.restaurant;


import com.TestData;
import com.model.Dish;
import com.model.Menu;
import com.repository.DishRepository;
import com.repository.MenuRepository;
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

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/restaurants/100002/menus/";

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void get() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_ID)
                /*.with(userHttpBasic(USER))*/)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU));
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
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ID)
                /*.with(userHttpBasic(ADMIN))*/)
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuRepository.get(MENU_ID));
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
        Menu updated = new Menu(MENU_ID, "Wednesday", LocalDateTime.of(2020, Month.APRIL, 1, 0, 0));
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                /*.with(userHttpBasic(ADMIN))*/)
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuRepository.get(MENU_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        Menu newMenu = new Menu(null, "Wednesday", LocalDateTime.of(2020, Month.APRIL, 8, 0, 0));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu))
                //.with(userHttpBasic(ADMIN))
        );

        Menu created = readFromJson(action, Menu.class);
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.get(newId), newMenu);
    }

    @Test
    void getAll() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.get(REST_URL)
                /*.with(userHttpBasic(USER))*/)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }
}