package com.web.restaurant;

import com.repository.VoteRepository;
import com.web.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VoteController {

    private VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("users/{userId}/votes")
    public String getVotes(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("votes", voteRepository.getAll(userId));
        return "votes";
    }

    @GetMapping("restaurants/vote/{restaurantId}")
    public String voteRestaurant(@PathVariable("restaurantId") int restaurantId) {
        int userId = SecurityUtil.authUserId();
        voteRepository.save(restaurantId, userId);
        return "redirect:/restaurants";
    }
}
