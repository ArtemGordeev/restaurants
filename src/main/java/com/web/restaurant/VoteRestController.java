package com.web.restaurant;

import com.repository.VoteRepository;
import com.to.VoteTo;
import com.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest";

    private final static Logger log = LoggerFactory.getLogger(VoteRestController.class);

    private VoteRepository voteRepository;

    public VoteRestController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/users/{userId}/votes")
    public List<VoteTo> getVotes() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        return voteRepository.getAll(userId);
    }

    @PostMapping("/restaurants/vote/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void voteRestaurant(@PathVariable("restaurantId") int restaurantId) {
        log.info("vote for restaurant");
        int userId = SecurityUtil.authUserId();
        voteRepository.save(restaurantId, userId);
    }
}
