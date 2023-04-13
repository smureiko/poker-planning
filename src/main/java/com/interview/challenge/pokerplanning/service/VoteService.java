package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Vote;
import java.util.List;
import java.util.UUID;

public interface VoteService {

    List<Vote> findAllBySessionId(UUID sessionId);

    Vote create(Vote vote, UUID id);
}
