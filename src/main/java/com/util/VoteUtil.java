package com.util;

import com.model.Vote;
import com.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    public static List<VoteTo> getVoteTo(List<Vote> votes){
        return votes.stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getTime(), vote.getRestaurant().getTitle(), vote.getUser()))
                .collect(Collectors.toList());
    }
}
