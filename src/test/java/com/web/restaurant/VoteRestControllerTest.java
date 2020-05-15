package com.web.restaurant;


import com.repository.VoteRepository;
import com.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.TestData.RESTAURANT_ID;
import static com.TestData.VOTE_TO_MATCHER;
import static com.TestUtil.userHttpBasic;
import static com.UserTestData.ADMIN;
import static com.UserTestData.ADMIN_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;

    @Test
    void voteRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/restaurants/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/rest/users/"+ ADMIN_ID + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteRepository.getAll(ADMIN_ID)));
    }

    @Test
    void voteAgain() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/restaurants/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        if(LocalTime.now().compareTo(LocalTime.of(11, 0)) > 0) {
            perform(MockMvcRequestBuilders.post("/rest/restaurants/vote/" + RESTAURANT_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isUnprocessableEntity());
        }
        else {
            perform(MockMvcRequestBuilders.post("/rest/restaurants/vote/" + RESTAURANT_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isNoContent());
            perform(MockMvcRequestBuilders.get("/rest/users/"+ ADMIN_ID + "/votes")
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(VOTE_TO_MATCHER.contentJson(voteRepository.getAll(ADMIN_ID)));
        }
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/restaurants/vote/" + RESTAURANT_ID))
                .andExpect(status().isUnauthorized());
    }
}