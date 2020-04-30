package com.web.restaurant;


import com.model.Restaurant;
import com.repository.RestaurantRepository;
import com.repository.VoteRepository;
import com.util.exception.NotFoundException;
import com.web.AbstractControllerTest;
import com.web.SecurityUtil;
import com.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.TestData.*;
import static com.TestUtil.readFromJson;
import static com.UserTestData.ADMIN_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;

    @Test
    void voteRestaurant() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        perform(MockMvcRequestBuilders.post("/rest/restaurants/vote/" + RESTAURANT_ID)
                /*.with(userHttpBasic(USER))*/)
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/rest/users/"+ ADMIN_ID + "/votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteRepository.getAll(ADMIN_ID)));
    }

//    @Test
//    void getUnauth() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
//                .andExpect(status().isUnauthorized());
//    }
}