package com.web.restaurant;


import com.repository.VoteRepository;
import com.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.TestData.*;
import static com.TestUtil.userHttpBasic;
import static com.UserTestData.*;
import static com.util.VoteUtil.getVoteTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void voteRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/rest/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTo(voteRepository.getAll())));
    }

    @Test
    void voteAgain() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        if(LocalTime.now().compareTo(LocalTime.of(11, 0)) > 0) {
            perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isUnprocessableEntity());
        }
        else {
            perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isNoContent());
            perform(MockMvcRequestBuilders.get("/rest/votes")
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTo(voteRepository.getAll())));
        }
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/vote/" + RESTAURANT_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllByDate() throws Exception{
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/rest/votes")
                .param("date", LocalDate.now().toString())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTo(voteRepository.getAll())));
    }

    @Test
    public void getWinner() throws Exception{
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.post("/rest/vote/" + RESTAURANT2_ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/rest/votes/today/winner")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT));
    }
}