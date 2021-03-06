package com.web.restaurant;


import com.model.Menu;
import com.service.MenuService;
import com.util.exception.NotFoundException;
import com.web.AbstractControllerTest;
import com.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;

import static com.TestData.*;
import static com.TestUtil.readFromJson;
import static com.TestUtil.userHttpBasic;
import static com.UserTestData.ADMIN;
import static com.UserTestData.USER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/restaurants/100003/menus/";
    private static final String REST_ADMIN_URL = "/rest/admin/restaurants/100003/menus/";

    @Autowired
    private MenuService menuService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU));
    }

    @Test
    void deleteUnauth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + MENU_ID))
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
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "100100")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Menu updated = new Menu(MENU_ID, "Wednesday", LocalDate.of(2020, Month.APRIL, 1));
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + DISH1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuService.get(MENU_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenu = new Menu(null, "Wednesday", LocalDate.of(2020, Month.APRIL, 8));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu))
                .with(userHttpBasic(ADMIN))
        );

        Menu created = readFromJson(action, Menu.class);
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuService.get(newId), newMenu);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }

    @Test
    void getTodayMenuWithDishes() throws Exception{
        perform(MockMvcRequestBuilders.get(REST_URL+"today")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER_INCLUDE_DISHES.contentJson(MENU));
    }
}