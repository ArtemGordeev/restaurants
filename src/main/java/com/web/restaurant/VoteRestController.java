package com.web.restaurant;

import com.model.Restaurant;
import com.service.VoteService;
import com.to.VoteTo;
import com.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.util.ValidationUtil.checkVote;
import static com.util.VoteUtil.getVoteTo;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest";

    private final static Logger log = LoggerFactory.getLogger(VoteRestController.class);

    private VoteService voteService;

    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/vote/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void voteRestaurant(@PathVariable("restaurantId") int restaurantId) {
        log.info("vote for restaurant");
        int userId = SecurityUtil.authUserId();
        checkVote(voteService.save(restaurantId, userId));
    }

    @GetMapping("/votes")
    public List<VoteTo> getAllByDate(@RequestParam @Nullable LocalDate date) {
        log.info("getAll");
        if(date == null){
            return getVoteTo(voteService.getAll());
        }
        return getVoteTo(voteService.getAllByDate(date));
    }

    @GetMapping("/votes/today")
    public List<VoteTo> getAllToday() {
        log.info("getAllToday");
        return getVoteTo(voteService.getAllToday());
    }

    @GetMapping("/votes/today/winner")
    public Restaurant getWinner(){
        log.info("getWinner");
        return voteService.getWinner();
    }
}
